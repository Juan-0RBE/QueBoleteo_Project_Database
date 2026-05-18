import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ButtonModule } from 'primeng/button';
import { InputTextModule } from 'primeng/inputtext';
import { SelectModule } from 'primeng/select';
import { TableModule } from 'primeng/table';
import { TagModule } from 'primeng/tag';
import { TextareaModule } from 'primeng/textarea';
import { GeneroService, Genero } from '../../../core/services/genero.service';

@Component({
  selector: 'app-admin-genero',
  standalone: true,
  imports: [
    CommonModule, FormsModule,
    ButtonModule, InputTextModule, SelectModule,
    TableModule, TagModule, TextareaModule,
  ],
  templateUrl: './genero.component.html',
  styleUrls: ['./genero.component.css']
})

export class AdminGeneroComponent {

  mostrarFormulario = false;
  modoEdicion = false;
  generoEditandoId: number | null = null;

  generos: Genero[] = [];
  formulario: Genero = this.formularioVacio();

  errorMsg: string = '';
  successMsg: string = '';
  loading: boolean = false;

  constructor(private generoService: GeneroService) {}

  // Se ejecuta automáticamente al abrir la pantalla
  ngOnInit(): void {
    this.cargarGeneros();
  }

  // Llama al backend y llena la tabla
  cargarGeneros(): void {
    this.generoService.getAll().subscribe({
      next: (data) => this.generos = data,
      error: () => this.errorMsg = 'Error al cargar los artistas'
    });
  }

  abrirCrear(): void {
    this.formulario = this.formularioVacio();
    this.modoEdicion = false;
    this.generoEditandoId = null;
    this.mostrarFormulario = true;
  }

  abrirEditar(genero: Genero): void {
    this.formulario = { ...genero };
    this.modoEdicion = true;
    this.generoEditandoId = genero.idGenero ?? null;
    this.mostrarFormulario = true;
  }

  guardar(): void {
    this.errorMsg = '';
    this.successMsg = '';
    this.loading = true;

    if (this.modoEdicion && this.generoEditandoId !== null) {
      // Edición — llama al PUT del backend
      this.generoService.update(this.generoEditandoId, this.formulario).subscribe({
        next: () => {
          this.successMsg = 'Artista actualizado correctamente';
          this.cancelar();
          this.cargarGeneros();
        },
        error: () => {
          this.errorMsg = 'Error al actualizar el artista';
          this.loading = false;
        }
      });
    } else {
      // Creación — llama al POST del backend
      this.generoService.create(this.formulario).subscribe({
        next: () => {
          this.successMsg = 'Artista creado correctamente';
          this.cancelar();
          this.cargarGeneros();
        },
        error: () => {
          this.errorMsg = 'Error al crear el artista';
          this.loading = false;
        }
      });
    }
  }

  eliminar(id: number): void {
    this.generoService.delete(id).subscribe({
      next: () => {
        this.successMsg = 'Artista eliminado correctamente';
        this.cargarGeneros();
      },
      error: () => this.errorMsg = 'Error al eliminar el artista'
    });
  }

  cancelar(): void {
    this.mostrarFormulario = false;
    this.formulario = this.formularioVacio();
    this.modoEdicion = false;
    this.generoEditandoId = null;
    this.loading = false;
  }

  private formularioVacio(): Genero {
    return {
      nombreGenero: '',
    };
  }
}
