import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Router, ActivatedRoute } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { ButtonModule } from 'primeng/button';
import { InputNumberModule } from 'primeng/inputnumber';
import { DividerModule } from 'primeng/divider';
import { ResenaService, ResenaDTO } from '../../../../core/services/resena.service';


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


export interface ZonaDisponible {
  id: number;
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
  zonas: ZonaDisponible[]; // <-- Aquí sí existe la propiedad zonas
}

@Component({
  selector: 'app-detalle-concierto',
  standalone: true,
  imports: [
    CommonModule, RouterModule, FormsModule,
    ButtonModule, InputNumberModule, DividerModule,
  ],
  templateUrl: './detalle-concierto.component.html',
  styleUrls: ['./detalle-concierto.component.css']
})
export class DetalleConciertoComponent implements OnInit {

  // Inicializamos el objeto vacío con la estructura correcta para que el HTML no lance errores de "undefined"
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

  asientosPorZona: { [zonaId: number]: Lugar[][] } = {};

  asientosSeleccionados: { [zonaId: number]: Set<number> } = {};

  cargandoAsientos: { [zonaId: number]: boolean } = {};

  cantidades: { [zonaId: number]: number } = {};
  loading: boolean = true; // Control de estado de carga



  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private conciertoService: ConciertoService,
    private compraService: CompraService,
    private authService: AuthService,
    private resenaService: ResenaService,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    // Capturamos el parámetro id definido en el enrutador (ej: /concierto/:id)
    const idConcierto = Number(this.route.snapshot.paramMap.get('id'));

    if (idConcierto) {
      this.cargarDetalleConcierto(idConcierto);
    } else {
      // Si no viene un ID válido, redirigimos a la página principal
      this.router.navigate(['/principal/paginaprincipal']);
    }

    this.cargarDetalleConcierto(idConcierto);

  }

  resenas: ResenaDTO[] = [];
  cargandoResenas: boolean = false;

  nuevaResena = {
    comentario: '',
    calificacion: 0
  };

  enviandoResena: boolean = false;

  cargarDetalleConcierto(id: number): void {
    this.loading = true;

    // 1. Consumimos el getById que retorna un ConciertoDTO
    this.conciertoService.getById(id).subscribe({
      next: (dto: ConciertoDTO) => {

        // 2. Disparamos las llamadas secundarias usando los métodos exactos de tu Service
        forkJoin({
          relacionesConcierto: this.conciertoService.getZonasByConcierto(id),
          // Asegúrate de mapear este método en tu servicio para que apunte a la tabla 'zona/all'
          zonasMaestras: this.conciertoService.getZonasGlobales()
        }).subscribe({
          next: ({ relacionesConcierto, zonasMaestras }) => {

            // 3. Cruzamos la información usando las variables locales de la suscripción
            const zonasMapeadas: ZonaDisponible[] = relacionesConcierto.map((zc: any) => {
              const zonaInfo = zonasMaestras.find((zm: any) => zm.idZona === zc.idZona);

              return {
                idZona: zc.idZona,

                idZonaConcierto: zc.idZonaConcierto ?? zc.id ?? zc.idPrecio,

                nombre: zonaInfo?.nombreZona || 'Zona',
                precio: zc.precio || 0,
                cantidadDisponible: zc.cantidadDisponible ?? 0,
                descripcion: `Entrada válida para la zona ${zonaInfo ? zonaInfo.nombreZona : ''}.`,
                tieneAsientos: zonaInfo ? zonaInfo.tieneAsiento : false
              };
            });

            // 4. Construimos el objeto final usando la interfaz ConciertoVisualizacion
            // Esto separa las propiedades del DTO estricto de lo que requiere la vista
            this.concierto = {
              id: dto.idConcierto || id,
              nombre: dto.nombreConcierto,
              artista: 'Artista por confirmar', // Más adelante puedes cruzarlo con getArtistasByConcierto(id)
              imagen: dto.imagenConcierto || 'https://images.unsplash.com/photo-1540039155733-5bb30b53aa14?w=1200&q=80',
              fecha: dto.fechaConcierto ? new Date(dto.fechaConcierto).toLocaleDateString('es-CO', { day: 'numeric', month: 'short', year: 'numeric' }) : 'Por confirmar',
              hora: dto.fechaConcierto ? new Date(dto.fechaConcierto).toLocaleTimeString('es-CO', { hour: '2-digit', minute: '2-digit' }) : 'Por confirmar',
              ciudad: 'Bogotá',
              sede: dto.nombreSede || 'Sede Principal',
              genero: 'General',
              edadMinima: dto.edadMinima || 0,
              descripcion: dto.descripcionConcierto || 'No hay descripción disponible.',
              zonas: zonasMapeadas // Asignación limpia y segura gracias a la nueva interfaz
            };

            // Inicializamos los inputs numéricos del carrito
            this.concierto.zonas.forEach((z: ZonaDisponible) => this.cantidades[z.id] = 0);
            this.loading = false;
            this.cargarResenas();
            this.cdr.detectChanges();
          },
          error: (err) => {
            console.error('Error procesando las localidades asociadas:', err);
            this.loading = false;
          }
        });

      },
      error: (err) => {
        console.error('Error al invocar getById en el Service:', err);
        this.router.navigate(['/principal/paginaprincipal']);
      }
    });
  }

  getCantidad(idZona: number): number {
    return this.cantidades[idZona] || 0;
  }

  incrementar(zona: ZonaDisponible): void {
    const actual = this.getCantidad(zona.id);
    if (actual < zona.cantidadDisponible && actual < 10) {
      this.cantidades[zona.id] = actual + 1;
    }
  }

  decrementar(zonaId: number): void {
    const actual = this.getCantidad(zonaId);
    if (actual > 0) this.cantidades[zonaId] = actual - 1;
  }

  get totalBoletas(): number {
    return this.itemsSeleccionados.reduce((acc, i) => acc + i.cantidad, 0);
  }

  get totalPrecio(): number {
    return this.itemsSeleccionados.reduce((acc, i) => acc + i.zona.precio * i.cantidad, 0);
  }

  formatPrecio(v: number): string {
    return new Intl.NumberFormat('es-CO', {
      style: 'currency', currency: 'COP', maximumFractionDigits: 0
    }).format(valor);
  }

  irAResumen(): void {
    this.router.navigate(['/compra/resumen'], {
      state: {
        concierto: this.concierto,
        items: this.itemsSeleccionados,
        total: this.totalPrecio
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

    this.concierto.zonas.forEach(z => {
      const cantidad = this.getCantidad(z.idZona);

      if (cantidad > 0 && !z.tieneAsientos) {
        items.push({
          zona: z,
          cantidad
        });
      }
    });

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

  cargarResenas(): void {
    if (!this.concierto.nombre) return;

    this.cargandoResenas = true;

    this.resenaService.getByConcierto(this.concierto.nombre).subscribe({
      next: (data) => {
        this.resenas = data ?? [];   // ← si data es null, usar []
        this.cargandoResenas = false;
      },
      error: () => {
        this.resenas = [];
        this.cargandoResenas = false;
      }
    });
  }

  enviarResena(): void {

    if (!this.nuevaResena.comentario.trim()) {
      alert('Escribe un comentario');
      return;
    }

    if (this.nuevaResena.calificacion < 1 || this.nuevaResena.calificacion > 5) {
      alert('La calificación debe estar entre 1 y 5');
      return;
    }

    const username = this.authService.getUsernameString();

    if (!username) {
      alert('Debes iniciar sesión para dejar una reseña');
      return;
    }

    this.authService.getCorreoByUsername(username).subscribe({

      next: (correo: string) => {

        const dto: ResenaDTO = {
          comentario: this.nuevaResena.comentario,
          calificacion: this.nuevaResena.calificacion,
          correoUsuario: correo,
          idConcierto: this.concierto.id
        };

        this.enviandoResena = true;

        this.resenaService.crear(dto).subscribe({

          next: () => {
            alert('¡Reseña publicada!');
            this.nuevaResena = { comentario: '', calificacion: 0 };
            this.enviandoResena = false;
            this.cargarResenas(); // recargar lista
          },

          error: (err) => {
            this.enviandoResena = false;
            alert(err.error || 'Error al publicar la reseña');
          }
        });
      },

      error: () => {
        alert('No se pudo obtener el correo del usuario');
      }
    });
  }

  estrellas(n: number): number[] {
    return Array(n).fill(0);
  }

  setCalificacion(n: number): void {
    this.nuevaResena.calificacion = n;
    this.cdr.detectChanges();
  }
}
