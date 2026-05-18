import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { ButtonModule } from 'primeng/button';
import { InputTextModule } from 'primeng/inputtext';
import { SelectModule } from 'primeng/select';
import { TableModule } from 'primeng/table';
import { DatePickerModule } from 'primeng/datepicker';
import { TextareaModule } from 'primeng/textarea';
import { InputNumberModule } from 'primeng/inputnumber';
import { ConciertoService} from '../../../core/services/concierto.service';
import { environment } from '../../../../environments/environment';

export interface Zona {
  idZona: number;
  nombreZona: string;
  tieneAsiento: boolean;
}

export interface Sede {
  nombreSede: string;
  ciudad: string;
  direccionSede?: string;
  tieneAccesibilidad?: boolean;
  imagenSede?: string;
}

export interface ZonaConcierto {
  id: number;
  zonaId: number;
  nombreZona: string;
  precio: number;
  cantidadDisponible: number;
  descripcion: string;
}

export interface Concierto {
  id: number;
  nombre: string;
  artista: string;
  nombreSede: string;
  fecha: Date | null;
  edadMinima: number;
  descripcion: string;
  estado: string;
  zonas: ZonaConcierto[];
  expandida?: boolean;
}

export interface ConciertoDTO {
  idConcierto: number;
  nombreConcierto: string;
  descripcionConcierto: string;
  imagenConcierto: string;
  edadMinima: number;
  recomendacion: string;
  fechaConcierto: string; // ISO String (yyyy-MM-dd)
  estadoConcierto: string;
  idTour: number;
  nombreSede: string;
}

export interface ZonaConciertoBackend {
  idConciertoZona: number;
  idZona: number;
  precio: number;
  cantidadDisponible: number; // o "cantidadDisponible" según Java
}

@Component({
  selector: 'app-admin-concierto',
  standalone: true,
  imports: [
    CommonModule, FormsModule,
    ButtonModule, InputTextModule, SelectModule,
    TableModule, DatePickerModule, TextareaModule, InputNumberModule,
  ],
  templateUrl: './concierto.component.html',
  styleUrls: ['./concierto.component.css']
})
export class AdminConciertoComponent implements OnInit {

  //Estado concierto
  mostrarFormulario = false;
  modoEdicion = false;
  conciertos: Concierto[] = [];
  formulario: Concierto = this.conciertoVacio();

  //Estado zona
  conciertoConFormularioZona: number | null = null;
  modoEdicionZona = false;
  formularioZona: ZonaConcierto = this.zonaVacia();

  //Datos del backend
  sedes: Sede[] = [];
  todasLasZonas: Zona[] = [];
  zonasDeSede: Zona[] = [];
  cargandoSedes = false;
  cargandoZonas = false;
  errorCarga = '';

  errorMsg: string = '';
  successMsg: string = '';
  loading: boolean = false;

  estados = [
    { label: 'Programado', value: 'Programado' },
    { label: 'Cancelado',  value: 'Cancelado'  },
    { label: 'Finalizado', value: 'Finalizado' },
  ];

  constructor(private http: HttpClient, private conciertoService: ConciertoService, private cdr: ChangeDetectorRef) {}

  ngOnInit(): void {
    this.cargarSedes();
    this.cargarZonas();
    this.cargarConciertos();
  }

  //Carga desde backend

  cargarSedes(): void {
    this.cargandoSedes = true;
    this.http.get<Sede[]>(`${environment.apiUrl}/sede/all`).subscribe({
      next: (sedes) => {
        this.sedes = sedes;
        this.cargandoSedes = false;
        this.cdr.detectChanges();
      },
      error: (err) => {
        console.error('Error al cargar sedes:', err);
        this.errorCarga = 'No se pudieron cargar las sedes.';
        this.cargandoSedes = false;
      }
    });
  }

  cargarZonas(): void {
    this.cargandoZonas = true;
    this.http.get<Zona[]>(`${environment.apiUrl}/zona/all`).subscribe({
      next: (zonas) => {
        this.todasLasZonas = zonas;
        this.cargandoZonas = false;
        this.cdr.detectChanges();
      },
      error: (err) => {
        console.error('Error al cargar zonas:', err);
        this.cargandoZonas = false;
      }
    });
  }

  cargarConciertos(): void {
    this.conciertoService.getAll().subscribe({
      next: (dtos: ConciertoDTO[]) => {
        // Transformamos lo que viene de Java al formato del Front
        this.conciertos = dtos.map(dto => this.mapearAFront(dto));
        this.cdr.detectChanges();
      },
      error: () => {
        this.errorMsg = 'Error al cargar los conciertos del servidor.';
      }
    });
  }

  //Opciones para el selector
  get opcionesSedes() {
    return this.sedes.map(s => ({ label: s.nombreSede, value: s.nombreSede }));
  }

  //Cuando cambia la sede filtra las zonas
  onSedeChange(nombreSede: string): void {
    this.formulario.nombreSede = nombreSede;
    this.zonasDeSede = this.todasLasZonas;
  }

  //Zonas disponibles para el panel de zonas de un concierto
  zonasDisponibles(concierto: Concierto): Zona[] {
    if (!concierto.nombreSede) return [];
    return this.todasLasZonas;
  }

  onZonaChange(zona: Zona): void {
    this.formularioZona.zonaId    = zona.idZona;
    this.formularioZona.nombreZona = zona.nombreZona;
  }

  //CRUD Concierto

  conciertoVacio(): Concierto {
    return { id: 0, nombre: '', artista: '', nombreSede: '', fecha: null, edadMinima: 0, descripcion: '', estado: 'Programado', zonas: [], expandida: false };
  }

  zonaVacia(): ZonaConcierto {
    return { id: 0, zonaId: 0, nombreZona: '', precio: 0, cantidadDisponible: 0, descripcion: '' };
  }

  abrirCrear(): void {
    this.formulario = this.conciertoVacio();
    this.zonasDeSede = [];
    this.modoEdicion = false;
    this.mostrarFormulario = true;
  }

  abrirEditar(c: Concierto): void {
    this.formulario = { ...c, zonas: [...c.zonas] };
    if (c.nombreSede) this.onSedeChange(c.nombreSede);
    this.modoEdicion = true;
    this.mostrarFormulario = true;
  }

  guardar(): void {
    if (!this.formulario.nombre || !this.formulario.artista) return;

    this.errorMsg = '';
    this.successMsg = '';
    this.loading = true;

    // Convertimos nuestro formulario visual al JSON de Java
    const bodyBackend = this.mapearABack(this.formulario);

    if (this.modoEdicion) {
      // Caso EDITAR (PUT)
      this.conciertoService.update(this.formulario.id, bodyBackend).subscribe({
        next: () => {
          this.successMsg = 'Concierto actualizado con éxito';
          this.cargarConciertos(); // Recarga limpia desde la Base de Datos
          this.cancelar();
        },
        error: () => {
          this.errorMsg = 'Error al actualizar el concierto';
          this.loading = false;
        }
      });
    } else {
      // Caso CREAR (POST)
      this.conciertoService.create(bodyBackend).subscribe({
        next: () => {
          this.successMsg = 'Concierto creado con éxito';
          this.cargarConciertos(); // Trae la lista fresca incluyendo el ID autogenerado por Java
          this.cancelar();
        },
        error: () => {
          this.errorMsg = 'Error al registrar el concierto';
          this.loading = false;
        }
      });
    }
  }

  eliminar(id: number): void {
    if (confirm('¿Estás seguro de eliminar este concierto?')) {
      this.conciertoService.delete(id).subscribe({
        next: () => {
          this.successMsg = 'Concierto eliminado permanentemente';
          this.cargarConciertos();
        },
        error: () => {
          this.errorMsg = 'No se pudo eliminar el concierto';
        }
      });
    }
  }

  cancelar(): void {
    this.mostrarFormulario = false;
    this.zonasDeSede = [];
    this.formulario = this.conciertoVacio();
    this.loading = false;
  }

  toggleExpandir(concierto: Concierto): void {
    concierto.expandida = !concierto.expandida;
    if (!concierto.expandida && this.conciertoConFormularioZona === concierto.id) {
      this.cancelarZona();
    }
  }

  //CRUD Zona

  abrirCrearZona(conciertoId: number): void {
    this.formularioZona = this.zonaVacia();
    this.modoEdicionZona = false;
    this.conciertoConFormularioZona = conciertoId;
  }

  abrirEditarZona(conciertoId: number, zona: ZonaConcierto): void {
    this.formularioZona = { ...zona };
    this.modoEdicionZona = true;
    this.conciertoConFormularioZona = conciertoId;
  }

  guardarZona(concierto: Concierto): void {
    if (!this.formularioZona.nombreZona) return;
    if (this.modoEdicionZona) {
      const idx = concierto.zonas.findIndex(z => z.id === this.formularioZona.id);
      if (idx !== -1) concierto.zonas[idx] = { ...this.formularioZona };
    } else {
      const nuevoId = concierto.zonas.length ? Math.max(...concierto.zonas.map(z => z.id)) + 1 : 1;
      concierto.zonas = [...concierto.zonas, { ...this.formularioZona, id: nuevoId }];
    }
    this.cancelarZona();
  }

  eliminarZona(concierto: Concierto, zonaId: number): void {
    concierto.zonas = concierto.zonas.filter(z => z.id !== zonaId);
  }

  cancelarZona(): void {
    this.conciertoConFormularioZona = null;
    this.formularioZona = this.zonaVacia();
  }

  formatFecha(fecha: Date | null): string {
    return fecha ? new Date(fecha).toLocaleDateString('es-CO') : '—';
  }

  formatPrecio(valor: number): string {
    return new Intl.NumberFormat('es-CO', {
      style: 'currency', currency: 'COP', maximumFractionDigits: 0
    }).format(valor);
  }

  private mapearAFront(dto: ConciertoDTO): Concierto {
    return {
      id: dto.idConcierto,
      nombre: dto.nombreConcierto,
      artista: 'Artista Ejemplo', // Vincula el artista si tu DTO lo incluye
      nombreSede: dto.nombreSede,
      fecha: dto.fechaConcierto ? new Date(dto.fechaConcierto) : null,
      edadMinima: dto.edadMinima,
      descripcion: dto.descripcionConcierto,
      estado: dto.estadoConcierto || 'Programado',
      zonas: [], // Si el GET trae zonas, las mapeas aquí de forma similar
      expandida: false
    };
  }

  private mapearABack(concierto: Concierto): ConciertoDTO {
    // Aseguramos que la fecha lleve la hora requerida por el LocalDateTime de Java
    let fechaFormateada = '';
    if (concierto.fecha instanceof Date) {
      // .toISOString() por defecto devuelve algo como "2026-05-19T00:00:00.000Z"
      fechaFormateada = concierto.fecha.toISOString();
    }

    return {
      idConcierto: concierto.id || 0,
      nombreConcierto: concierto.nombre,
      descripcionConcierto: concierto.descripcion,
      imagenConcierto: '',
      edadMinima: concierto.edadMinima,
      recomendacion: '',
      fechaConcierto: fechaFormateada, // <--- Enviamos el ISO completo con la 'T' y la hora
      estadoConcierto: concierto.estado,
      idTour: 1,
      nombreSede: concierto.nombreSede
    };
  }
}
