import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ButtonModule } from 'primeng/button';
import { InputTextModule } from 'primeng/inputtext';
import { SelectModule } from 'primeng/select';
import { TableModule } from 'primeng/table';
import { TagModule } from 'primeng/tag';
import { TextareaModule } from 'primeng/textarea';

export interface Artista {
  id: number;
  nombre: string;
  tipo: string;
  genero: string;
  pais: string;
  descripcion: string;
}

@Component({
  selector: 'app-admin-artista',
  standalone: true,
  imports: [
    CommonModule, FormsModule,
    ButtonModule, InputTextModule, SelectModule,
    TableModule, TagModule, TextareaModule,
  ],
  templateUrl: './artista.component.html',
  styleUrls: ['./artista.component.css']
})
export class AdminArtistaComponent {

  mostrarFormulario = false;
  modoEdicion = false;

  tipos = [
    { label: 'Individual', value: 'Individual' },
    { label: 'Grupo',      value: 'Grupo'      },
  ];

  generos = [
    { label: 'Rock',        value: 'Rock'        },
    { label: 'Pop',         value: 'Pop'         },
    { label: 'Electrónica', value: 'Electrónica' },
    { label: 'Reggaeton',   value: 'Reggaeton'   },
    { label: 'Jazz',        value: 'Jazz'         },
    { label: 'Otro',        value: 'Otro'         },
  ];

  artistas: Artista[] = [];

  formulario: Artista = this.formularioVacio();

  formularioVacio(): Artista {
    return { id: 0, nombre: '', tipo: '', genero: '', pais: '', descripcion: '' };
  }

  abrirCrear(): void {
    this.formulario = this.formularioVacio();
    this.modoEdicion = false;
    this.mostrarFormulario = true;
  }

  abrirEditar(artista: Artista): void {
    this.formulario = { ...artista };
    this.modoEdicion = true;
    this.mostrarFormulario = true;
  }

  guardar(): void {
    if (!this.formulario.nombre || !this.formulario.tipo || !this.formulario.genero) return;

    if (this.modoEdicion) {
      const idx = this.artistas.findIndex(a => a.id === this.formulario.id);
      if (idx !== -1) this.artistas[idx] = { ...this.formulario };
    } else {
      const nuevoId = this.artistas.length ? Math.max(...this.artistas.map(a => a.id)) + 1 : 1;
      this.artistas = [...this.artistas, { ...this.formulario, id: nuevoId }];
    }
    this.cancelar();
  }

  eliminar(id: number): void {
    this.artistas = this.artistas.filter(a => a.id !== id);
  }

  cancelar(): void {
    this.mostrarFormulario = false;
    this.formulario = this.formularioVacio();
  }
}
