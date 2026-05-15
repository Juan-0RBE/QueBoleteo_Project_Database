import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ButtonModule } from 'primeng/button';
import { InputTextModule } from 'primeng/inputtext';
import { TableModule } from 'primeng/table';

export interface Organizador {
  id: number;
  nombre: string;
  correo: string;
  logo: string;
}

@Component({
  selector: 'app-admin-organizador',
  standalone: true,
  imports: [CommonModule, FormsModule, ButtonModule, InputTextModule, TableModule],
  templateUrl: './organizador.component.html',
  styleUrls: ['./organizador.component.css']
})
export class AdminOrganizadorComponent {

  mostrarFormulario = false;
  modoEdicion = false;
  organizadores: Organizador[] = [];
  formulario: Organizador = this.formularioVacio();

  formularioVacio(): Organizador {
    return { id: 0, nombre: '', correo: '', logo: '' };
  }

  abrirCrear(): void { this.formulario = this.formularioVacio(); this.modoEdicion = false; this.mostrarFormulario = true; }
  abrirEditar(o: Organizador): void { this.formulario = { ...o }; this.modoEdicion = true; this.mostrarFormulario = true; }

  guardar(): void {
    if (!this.formulario.nombre) return;
    if (this.modoEdicion) {
      const idx = this.organizadores.findIndex(o => o.id === this.formulario.id);
      if (idx !== -1) this.organizadores[idx] = { ...this.formulario };
    } else {
      const nuevoId = this.organizadores.length ? Math.max(...this.organizadores.map(o => o.id)) + 1 : 1;
      this.organizadores = [...this.organizadores, { ...this.formulario, id: nuevoId }];
    }
    this.cancelar();
  }

  eliminar(id: number): void { this.organizadores = this.organizadores.filter(o => o.id !== id); }
  cancelar(): void { this.mostrarFormulario = false; this.formulario = this.formularioVacio(); }
}
