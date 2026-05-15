import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ButtonModule } from 'primeng/button';
import { InputTextModule } from 'primeng/inputtext';
import { SelectModule } from 'primeng/select';
import { TableModule } from 'primeng/table';
import { DatePickerModule } from 'primeng/datepicker';
import { TextareaModule } from 'primeng/textarea';
import { InputNumberModule } from 'primeng/inputnumber';

export interface ZonaConcierto {
  id: number;
  nombreZona: string;
  precio: number;
  cantidadDisponible: number;
  descripcion: string;
}

export interface Concierto {
  id: number;
  nombre: string;
  artista: string;
  sede: string;
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
export class AdminConciertoComponent {

  // ── Estado concierto ──
  mostrarFormulario = false;
  modoEdicion = false;
  conciertos: Concierto[] = [];
  formulario: Concierto = this.concierttoVacio();

  // ── Estado zona ──
  conciertoConFormularioZona: number | null = null;
  modoEdicionZona = false;
  formularioZona: ZonaConcierto = this.zonaVacia();

  estados = [
    { label: 'Programado', value: 'Programado' },
    { label: 'Cancelado',  value: 'Cancelado'  },
    { label: 'Finalizado', value: 'Finalizado' },
  ];

  concierttoVacio(): Concierto {
    return { id: 0, nombre: '', artista: '', sede: '', fecha: null, edadMinima: 0, descripcion: '', estado: 'Programado', zonas: [], expandida: false };
  }

  zonaVacia(): ZonaConcierto {
    return { id: 0, nombreZona: '', precio: 0, cantidadDisponible: 0, descripcion: '' };
  }

  // ── CRUD Concierto ──
  abrirCrear(): void {
    this.formulario = this.concierttoVacio();
    this.modoEdicion = false;
    this.mostrarFormulario = true;
  }

  abrirEditar(c: Concierto): void {
    this.formulario = { ...c, zonas: [...c.zonas] };
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
    this.formulario = this.concierttoVacio();
  }

  toggleExpandir(concierto: Concierto): void {
    concierto.expandida = !concierto.expandida;
    if (!concierto.expandida && this.conciertoConFormularioZona === concierto.id) {
      this.cancelarZona();
    }
  }

  // ── CRUD Zona ──
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
    return new Intl.NumberFormat('es-CO', { style: 'currency', currency: 'COP', maximumFractionDigits: 0 }).format(valor);
  }
}
