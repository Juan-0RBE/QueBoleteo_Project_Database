import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ButtonModule } from 'primeng/button';
import { InputTextModule } from 'primeng/inputtext';
import { TableModule } from 'primeng/table';
import { OrganizadorService, Organizador } from '../../../core/services/organizador.service';
import { SelectModule } from 'primeng/select';
import { TagModule } from 'primeng/tag';
import { TextareaModule } from 'primeng/textarea';
import { Artista, ArtistaService } from '../../../core/services/artista.service';
import { GrupoService } from '../../../core/services/grupo.service';

@Component({
  selector: 'app-admin-organizador',
  standalone: true,
  imports: [
    CommonModule, FormsModule,
    ButtonModule, InputTextModule, SelectModule,
    TableModule, TagModule, TextareaModule,
  ],
  templateUrl: './organizador.component.html',
  styleUrls: ['./organizador.component.css']
})

export class AdminOrganizadorComponent implements OnInit {

  mostrarFormulario = false;
  modoEdicion = false;
  organizadorEditandoId: string | null = null;

  organizadores: Organizador[] = [];
  formulario: Organizador = this.formularioVacio();

  errorMsg: string = '';
  successMsg: string = '';
  loading: boolean = false;

  constructor(private organizadorService: OrganizadorService) {}

  // Se ejecuta automáticamente al abrir la pantalla
  ngOnInit(): void {
    this.cargarOrganizadores();
  }

  // Llama al backend y llena la tabla
  cargarOrganizadores(): void {
    this.organizadorService.getAll().subscribe({
      next: (data) => this.organizadores = data,
      error: () => this.errorMsg = 'Error al cargar los artistas'
    });
  }

  abrirCrear(): void {
    this.formulario = this.formularioVacio();
    this.modoEdicion = false;
    this.organizadorEditandoId = null;
    this.mostrarFormulario = true;
  }

  abrirEditar(organizador: Organizador): void {
    this.formulario = { ...organizador };
    this.modoEdicion = true;
    this.organizadorEditandoId = organizador.nombreOrganizador ?? null;
    this.mostrarFormulario = true;
  }

  guardar(): void {
    this.errorMsg = '';
    this.successMsg = '';
    this.loading = true;

    if (this.modoEdicion && this.organizadorEditandoId !== null) {
      // Edición — llama al PUT del backend
      this.organizadorService.update(this.organizadorEditandoId, this.formulario).subscribe({
        next: () => {
          this.successMsg = 'Artista actualizado correctamente';
          this.cancelar();
          this.cargarOrganizadores();
        },
        error: () => {
          this.errorMsg = 'Error al actualizar el artista';
          this.loading = false;
        }
      });
    } else {
      // Creación — llama al POST del backend
      this.organizadorService.create(this.formulario).subscribe({
        next: () => {
          this.successMsg = 'Artista creado correctamente';
          this.cancelar();
          this.cargarOrganizadores();
        },
        error: () => {
          this.errorMsg = 'Error al crear el artista';
          this.loading = false;
        }
      });
    }
  }

  eliminar(nombreOrganizador: string): void {
    this.organizadorService.delete(nombreOrganizador).subscribe({
      next: () => {
        this.successMsg = 'Artista eliminado correctamente';
        this.cargarOrganizadores();
      },
      error: () => this.errorMsg = 'Error al eliminar el artista'
    });
  }

  cancelar(): void {
    this.mostrarFormulario = false;
    this.formulario = this.formularioVacio();
    this.modoEdicion = false;
    this.organizadorEditandoId = null;
    this.loading = false;
  }

  private formularioVacio(): Organizador {
    return {
      nombreOrganizador: '',
      correoOrganizador: '',
      logo: '',
    };
  }

}


