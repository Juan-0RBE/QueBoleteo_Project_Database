import { Component, OnInit } from '@angular/core';
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

  estados = [
    { label: 'Programado', value: 'Programado' },
    { label: 'Cancelado',  value: 'Cancelado'  },
    { label: 'Finalizado', value: 'Finalizado' },
  ];

  constructor(private http: HttpClient) {}

  ngOnInit(): void {
    this.cargarSedes();
    this.cargarZonas();
  }

  //Carga desde backend

  cargarSedes(): void {
    this.cargandoSedes = true;
    this.http.get<Sede[]>(`${environment.apiUrl}/sede/all`).subscribe({
      next: (sedes) => {
        this.sedes = sedes;
        this.cargandoSedes = false;
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
      },
      error: (err) => {
        console.error('Error al cargar zonas:', err);
        this.cargandoZonas = false;
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
    if (this.modoEdicion) {
      const idx = this.conciertos.findIndex(c => c.id === this.formulario.id);
      if (idx !== -1) this.conciertos[idx] = { ...this.formulario };
    } else {
      const nuevoId = this.conciertos.length ? Math.max(...this.conciertos.map(c => c.id)) + 1 : 1;
      this.conciertos = [...this.conciertos, { ...this.formulario, id: nuevoId, zonas: [], expandida: false }];
    }
    this.cancelar();
  }

  eliminar(id: number): void {
    this.conciertos = this.conciertos.filter(c => c.id !== id);
  }

  cancelar(): void {
    this.mostrarFormulario = false;
    this.zonasDeSede = [];
    this.formulario = this.conciertoVacio();
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
}
