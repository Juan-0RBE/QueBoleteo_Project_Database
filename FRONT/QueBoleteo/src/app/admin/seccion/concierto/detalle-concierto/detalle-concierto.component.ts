import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Router, ActivatedRoute } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { ButtonModule } from 'primeng/button';
import { InputNumberModule } from 'primeng/inputnumber';
import { DividerModule } from 'primeng/divider';


import { forkJoin } from 'rxjs';

import {
  ConciertoDTO,
  ConciertoService
} from '../../../../core/services/concierto.service';

import {
  CompraService,
  CompraRequest,
  Lugar
} from '../../../../core/services/compra.service';

import { AuthService } from '../../../../core/services/auth.service';

/* =========================
   MODELOS UI
========================= */

export interface ZonaDisponible {
  idZona: number;
  idZonaConcierto: number;
  nombre: string;
  precio: number;
  cantidadDisponible: number;
  descripcion: string;
  tieneAsientos: boolean;
}

export interface ItemSeleccionado {
  zona: ZonaDisponible;
  cantidad: number;
}

export interface ConciertoVisualizacion {
  id: number;
  nombre: string;
  artista: string;
  imagen: string;
  fecha: string;
  hora: string;
  ciudad: string;
  sede: string;
  genero: string;
  edadMinima: number;
  descripcion: string;
  zonas: ZonaDisponible[];
}

@Component({
  selector: 'app-detalle-concierto',
  standalone: true,
  imports: [
    CommonModule,
    RouterModule,
    FormsModule,
    ButtonModule,
    InputNumberModule,
    DividerModule,
  ],
  templateUrl: './detalle-concierto.component.html',
  styleUrls: ['./detalle-concierto.component.css']
})

export class DetalleConciertoComponent implements OnInit {
  /* =========================
     ESTADO
  ========================= */

  concierto: ConciertoVisualizacion = {
    id: 0,
    nombre: '',
    artista: '',
    imagen: '',
    fecha: '',
    hora: '',
    ciudad: '',
    sede: '',
    genero: '',
    edadMinima: 0,
    descripcion: '',
    zonas: []
  };

/*
  itemsSeleccionados: any[] = [];
*/
// Matriz de filas → cada celda tiene el Lugar real del backend
  asientosPorZona: { [zonaId: number]: Lugar[][] } = {};

// Set de idLugar (number, no string)
  asientosSeleccionados: { [zonaId: number]: Set<number> } = {};

// Para mostrar spinner mientras carga el mapa
  cargandoAsientos: { [zonaId: number]: boolean } = {};

  cantidades: { [zonaId: number]: number } = {};
  loading: boolean = true;

  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private conciertoService: ConciertoService,
    private compraService: CompraService,
    private authService: AuthService,
    private cdr: ChangeDetectorRef
  ) {}

  /* =========================
     INIT
  ========================= */

  ngOnInit(): void {

    const idConcierto = Number(this.route.snapshot.paramMap.get('id'));

    if (!idConcierto) {
      this.router.navigate(['/principal/paginaprincipal']);
      return;
    }

    this.cargarDetalleConcierto(idConcierto);
  }

  /* =========================
     CARGA CONCIERTO
  ========================= */

  cargarDetalleConcierto(id: number): void {

    this.loading = true;

    this.conciertoService.getById(id).subscribe({

      next: (dto: ConciertoDTO) => {

        forkJoin({
          relacionesConcierto: this.conciertoService.getZonasByConcierto(id),
          zonasMaestras: this.conciertoService.getZonasGlobales()
        }).subscribe({

          next: ({ relacionesConcierto, zonasMaestras }) => {
            console.log('RELACIONES CONCIERTO:', relacionesConcierto);
            const zonasMapeadas: ZonaDisponible[] = relacionesConcierto.map((zc: any) => {

              const zonaInfo = zonasMaestras.find(
                (zm: any) => zm.idZona === zc.idZona
              );

              return {
                idZona: zc.idZona,

                // ⚠️ IMPORTANTE: AJUSTA ESTE CAMPO SI TU BACKEND USA OTRO NOMBRE
                idZonaConcierto: zc.idZonaConcierto ?? zc.id ?? zc.idPrecio,

                nombre: zonaInfo?.nombreZona || 'Zona',
                precio: zc.precio || 0,
                cantidadDisponible: zc.cantidadDisponible ?? 0,
                descripcion: `Zona ${zonaInfo?.nombreZona || ''}`,
                tieneAsientos: zonaInfo?.tieneAsiento || false
              };
            });

            this.concierto = {
              id: dto.idConcierto || id,
              nombre: dto.nombreConcierto,
              artista: 'Artista por confirmar',
              imagen: dto.imagenConcierto ||
                'https://images.unsplash.com/photo-1540039155733-5bb30b53aa14?w=1200&q=80',
              fecha: dto.fechaConcierto
                ? new Date(dto.fechaConcierto).toLocaleDateString('es-CO')
                : 'Por confirmar',
              hora: dto.fechaConcierto
                ? new Date(dto.fechaConcierto).toLocaleTimeString('es-CO')
                : 'Por confirmar',
              ciudad: 'Bogotá',
              sede: dto.nombreSede || '',
              genero: 'General',
              edadMinima: dto.edadMinima || 0,
              descripcion: dto.descripcionConcierto || '',
              zonas: zonasMapeadas
            };

            // inicializar cantidades
            this.concierto.zonas.forEach(z => {
              this.cantidades[z.idZona] = 0;
            });

            this.loading = false;
            this.cdr.detectChanges();
          },

          error: (err) => {
            console.error(err);
            this.loading = false;
          }

        });

      },

      error: (err) => {
        console.error(err);
        this.router.navigate(['/principal/paginaprincipal']);
      }

    });
  }

  /* =========================
     CANTIDADES
  ========================= */

  getCantidad(idZona: number): number {
    return this.cantidades[idZona] || 0;
  }

  incrementar(zona: ZonaDisponible): void {

    const actual = this.getCantidad(zona.idZona);

    if (actual < zona.cantidadDisponible && actual < 10) {
      this.cantidades[zona.idZona] = actual + 1;
    }
  }

  decrementar(idZona: number): void {

    const actual = this.getCantidad(idZona);

    if (actual > 0) {
      this.cantidades[idZona] = actual - 1;
    }
  }

  /* =========================
     SELECCIÓN
  ========================= */

/*  get itemsSeleccionados(): ItemSeleccionado[] {

    return this.concierto.zonas
      .filter(z => this.getCantidad(z.idZona) > 0)
      .map(z => ({
        zona: z,
        cantidad: this.getCantidad(z.idZona)
      }));
  }*/

  get totalBoletas(): number {
    return this.itemsSeleccionados
      .reduce((a, b) => a + b.cantidad, 0);
  }

  get totalPrecio(): number {
    return this.itemsSeleccionados
      .reduce((a, b) => a + b.cantidad * b.zona.precio, 0);
  }

  /* =========================
     COMPRA
  ========================= */

/*  comprar(zona: ZonaDisponible): void {

    const cantidad = this.getCantidad(zona.idZona);

    if (cantidad <= 0) {
      alert('Selecciona al menos 1 boleta');
      return;
    }

    const username = this.authService.getUsername();

    if (!username) {
      alert('Debes iniciar sesión');
      return;
    }

    this.authService.getCorreoByUsername(username)
      .subscribe({

        next: (correoUsuario: string) => {

          const compra: CompraRequest = {
            correoUsuario: correoUsuario,
            idZonaConcierto: zona.idZonaConcierto,
            cantidad: cantidad
          };

          console.log('DTO enviado:', compra);

          this.compraService.realizarCompra(compra)
            .subscribe({

              next: (response) => {
                alert(
                  `Compra realizada correctamente.

Venta #${response.idVenta}
Boletos: ${response.codigosBoletos.join(', ')}`
                );
              },

              error: (error) => {
                console.error(error);
                alert(error.error || 'Error realizando compra');
              }

            });

        },

        error: () => {
          alert('No se pudo obtener el correo del usuario');
        }

      });
  }*/

  /* =========================
     UTIL
  ========================= */

  formatPrecio(v: number): string {
    return new Intl.NumberFormat('es-CO', {
      style: 'currency',
      currency: 'COP',
      maximumFractionDigits: 0
    }).format(v);
  }

  irAResumen(): void {

    const correoUsuario = this.authService.getCorreo();

    if (!correoUsuario) {
      alert('Debes iniciar sesión');
      return;
    }

    if (this.itemsSeleccionados.length === 0) {
      alert('Selecciona al menos una boleta');
      return;
    }

    this.router.navigate(['/compra/resumen'], {
      state: {
        concierto: this.concierto,
        items: this.itemsSeleccionados,
        total: this.totalPrecio,
        correo: correoUsuario
      }
    });
  }


  cargarAsientosReales(zona: ZonaDisponible): void {

    if (this.asientosPorZona[zona.idZona]) return; // ya cargados, no volver a pedir

    this.cargandoAsientos[zona.idZona] = true;

    this.compraService.getLugaresDisponibles(zona.idZonaConcierto).subscribe({

      next: (lugares: Lugar[]) => {

        // Agrupar por fila
        const filaMap = new Map<string, Lugar[]>();

        lugares.forEach(l => {
          const fila = l.fila ?? 'A'; // fallback por si viene null
          if (!filaMap.has(fila)) filaMap.set(fila, []);
          filaMap.get(fila)!.push(l);
        });

        // Ordenar filas y asientos dentro de cada fila
        const matriz: Lugar[][] = Array.from(filaMap.entries())
          .sort(([a], [b]) => a.localeCompare(b))
          .map(([, asientos]) =>
            asientos.sort((a, b) => a.numeroAsiento - b.numeroAsiento)
          );

        this.asientosPorZona[zona.idZona] = matriz;
        this.asientosSeleccionados[zona.idZona] = new Set<number>();
        this.cargandoAsientos[zona.idZona] = false;
        this.cdr.detectChanges();
      },

      error: () => {
        this.cargandoAsientos[zona.idZona] = false;
        alert('Error al cargar los asientos de esta zona');
      }
    });
  }


  toggleAsiento(zonaId: number, idLugar: number): void {

    const set = this.asientosSeleccionados[zonaId];
    if (!set) return;

    if (set.has(idLugar)) {
      set.delete(idLugar);
    } else {
      set.add(idLugar);
    }
  }

  esAsientoSeleccionado(zonaId: number, idLugar: number): boolean {
    return this.asientosSeleccionados[zonaId]?.has(idLugar) ?? false;
  }



  /*comprar(zona: ZonaDisponible): void {

    let cantidad = this.getCantidad(zona.idZona);

    // 🟣 SI ES ZONA CON ASIENTOS → tomar del set
    if (zona.tieneAsientos) {
      const seleccion = this.asientosSeleccionados[zona.idZona];
      cantidad = seleccion ? seleccion.size : 0;
    }

    if (cantidad <= 0) {
      alert('Selecciona al menos 1 boleta');
      return;
    }

    const username = this.authService.getUsername();

    if (!username) {
      alert('Debes iniciar sesión');
      return;
    }

    this.authService.getCorreoByUsername(username)
      .subscribe({

        next: (correoUsuario: string) => {

          const compra: CompraRequest = {
            correoUsuario: correoUsuario,
            idZonaConcierto: zona.idZonaConcierto,
            cantidad: cantidad
          };

          console.log('DTO enviado:', compra);

          this.compraService.realizarCompra(compra)
            .subscribe({

              next: (response) => {
                alert(
                  `Compra realizada correctamente.

Venta #${response.idVenta}
Boletos: ${response.codigosBoletos.join(', ')}`
                );
              },

              error: (error) => {
                console.error(error);
                alert(error.error || 'Error realizando compra');
              }

            });

        },

        error: () => {
          alert('No se pudo obtener el correo del usuario');
        }

      });
  }*/

  comprar(zona: ZonaDisponible): void {

    const username = this.authService.getUsernameString(); // ← este nunca retorna null (retorna '' si no hay)

    if (!username) {
      alert('Debes iniciar sesión');
      return;
    }

    // Resto de la validación de cantidad...
    let cantidad: number;
    let idsLugaresElegidos: number[] | undefined;

    if (zona.tieneAsientos) {
      const seleccion = this.asientosSeleccionados[zona.idZona];
      if (!seleccion || seleccion.size === 0) {
        alert('Selecciona al menos un asiento');
        return;
      }
      idsLugaresElegidos = Array.from(seleccion);
      cantidad = idsLugaresElegidos.length;
    } else {
      cantidad = this.getCantidad(zona.idZona);
      if (cantidad <= 0) {
        alert('Selecciona al menos 1 boleta');
        return;
      }
    }

    this.authService.getCorreoByUsername(username).subscribe({

      next: (correoUsuario: string) => {

        const compra: CompraRequest = {
          correoUsuario,
          idZonaConcierto: zona.idZonaConcierto,
          cantidad,
          ...(idsLugaresElegidos ? { idsLugaresElegidos } : {})
        };

        console.log('DTO enviado:', compra);

        this.compraService.realizarCompra(compra).subscribe({
          next: (response) => {
            alert(`Compra realizada correctamente.\n\nVenta #${response.idVenta}\nBoletos: ${response.codigosBoletos.join(', ')}`);
            delete this.asientosPorZona[zona.idZona];
            this.asientosSeleccionados[zona.idZona] = new Set();
          },
          error: (error) => {
            console.error(error);
            alert(error.error || 'Error realizando compra');
          }
        });
      },

      error: () => {
        alert('No se pudo obtener el correo del usuario');
      }
    });
  }

  get itemsSeleccionados(): any[] {

    const items: any[] = [];

    // 🟡 ZONAS GENERALES
    this.concierto.zonas.forEach(z => {
      const cantidad = this.getCantidad(z.idZona);

      if (cantidad > 0 && !z.tieneAsientos) {
        items.push({
          zona: z,
          cantidad
        });
      }
    });

    // 🔵 ASIENTOS
    Object.keys(this.asientosSeleccionados).forEach((zonaIdStr) => {

      const zonaId = Number(zonaIdStr);
      const zona = this.concierto.zonas.find(z => z.idZona === zonaId);

      const seleccion = this.asientosSeleccionados[zonaId];

      if (zona && seleccion && seleccion.size > 0) {
        items.push({
          zona,
          cantidad: seleccion.size,
          asientos: Array.from(seleccion)
        });
      }
    });

    return items;
  }
}
