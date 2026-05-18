import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Router, ActivatedRoute } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { ButtonModule } from 'primeng/button';
import { InputNumberModule } from 'primeng/inputnumber';
import { DividerModule } from 'primeng/divider';
import { ConciertoDTO, ConciertoService } from '../../../../core/services/concierto.service';
import { forkJoin } from 'rxjs';

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

  zonaSeleccionada: ZonaDisponible | null = null;
  cantidades: { [zonaId: number]: number } = {};
  loading: boolean = true; // Control de estado de carga

  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private conciertoService: ConciertoService,
    private cdr: ChangeDetectorRef,
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
  }

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
                id: zc.idZona,
                nombre: zonaInfo ? zonaInfo.nombreZona : 'Localidad',
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

  getCantidad(zonaId: number): number {
    return this.cantidades[zonaId] || 0;
  }

  setCantidad(zonaId: number, valor: number): void {
    this.cantidades[zonaId] = Math.max(0, valor || 0);
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

  get itemsSeleccionados(): ItemSeleccionado[] {
    if (!this.concierto || !this.concierto.zonas) return [];
    return this.concierto.zonas
      .filter((z: ZonaDisponible) => this.getCantidad(z.id) > 0)
      .map((z: ZonaDisponible) => ({ zona: z, cantidad: this.getCantidad(z.id) }));
  }

  get totalBoletas(): number {
    return this.itemsSeleccionados.reduce((acc, i) => acc + i.cantidad, 0);
  }

  get totalPrecio(): number {
    return this.itemsSeleccionados.reduce((acc, i) => acc + i.zona.precio * i.cantidad, 0);
  }

  formatPrecio(valor: number): string {
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
}
