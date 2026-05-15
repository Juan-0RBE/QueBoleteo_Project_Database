import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ButtonModule } from 'primeng/button';
import { InputTextModule } from 'primeng/inputtext';
import { TableModule } from 'primeng/table';
import { DatePickerModule } from 'primeng/datepicker';
import { TextareaModule } from 'primeng/textarea';

export interface Tour {
  id: number;
  nombre: string;
  artista: string;
  fechaInicial: Date | null;
  fechaFinal: Date | null;
  descripcion: string;
}

@Component({
  selector: 'app-admin-tour',
  standalone: true,
  imports: [CommonModule, FormsModule, ButtonModule, InputTextModule, TableModule, DatePickerModule, TextareaModule],
  templateUrl: './tour.component.html',
  styleUrls: ['./tour.component.css']
})
export class AdminTourComponent {

  mostrarFormulario = false;
  modoEdicion = false;
  tours: Tour[] = [];
  formulario: Tour = this.formularioVacio();

  formularioVacio(): Tour {
    return { id: 0, nombre: '', artista: '', fechaInicial: null, fechaFinal: null, descripcion: '' };
  }

  abrirCrear(): void { this.formulario = this.formularioVacio(); this.modoEdicion = false; this.mostrarFormulario = true; }
  abrirEditar(t: Tour): void { this.formulario = { ...t }; this.modoEdicion = true; this.mostrarFormulario = true; }

  guardar(): void {
    if (!this.formulario.nombre) return;
    if (this.modoEdicion) {
      const idx = this.tours.findIndex(t => t.id === this.formulario.id);
      if (idx !== -1) this.tours[idx] = { ...this.formulario };
    } else {
      const nuevoId = this.tours.length ? Math.max(...this.tours.map(t => t.id)) + 1 : 1;
      this.tours = [...this.tours, { ...this.formulario, id: nuevoId }];
    }
    this.cancelar();
  }

  eliminar(id: number): void { this.tours = this.tours.filter(t => t.id !== id); }
  cancelar(): void { this.mostrarFormulario = false; this.formulario = this.formularioVacio(); }
  formatFecha(f: Date | null): string { return f ? new Date(f).toLocaleDateString('es-CO') : '—'; }
}
