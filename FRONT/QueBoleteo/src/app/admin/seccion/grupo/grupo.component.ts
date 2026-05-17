import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ButtonModule } from 'primeng/button';
import { InputTextModule } from 'primeng/inputtext';
import { SelectModule } from 'primeng/select';
import { TableModule } from 'primeng/table';
import { TagModule } from 'primeng/tag';
import { TextareaModule } from 'primeng/textarea';
import { GrupoService, Grupo } from '../../../core/services/grupo.service';

@Component({
  selector: 'app-admin-grupo',
  standalone: true,
  imports: [
    CommonModule, FormsModule,
    ButtonModule, InputTextModule, SelectModule,
    TableModule, TagModule, TextareaModule,
  ],
  templateUrl: './grupo.component.html',
  styleUrls: ['./grupo.component.css']
})

export class AdminGrupoComponent implements OnInit {

  mostrarFormulario = false;
  modoEdicion = false;
  grupoEditandoId: number | null = null;

  grupos: Grupo[] = [];
  formulario: Grupo = this.formularioVacio();

  errorMsg: string = '';
  successMsg: string = '';
  loading: boolean = false;

  constructor(private grupoService: GrupoService) {}

  // Se ejecuta automáticamente al abrir la pantalla
  ngOnInit(): void {
    this.cargarGrupos();
  }

  // Llama al backend y llena la tabla
  cargarGrupos(): void {
    this.grupoService.getAll().subscribe({
      next: (data) => this.grupos = data,
      error: () => this.errorMsg = 'Error al cargar los grupos'
    });
  }

  abrirCrear(): void {
    this.formulario = this.formularioVacio();
    this.modoEdicion = false;
    this.grupoEditandoId = null;
    this.mostrarFormulario = true;
  }

  abrirEditar(grupo: Grupo): void {
    this.formulario = { ...grupo};
    this.modoEdicion = true;
    this.grupoEditandoId = grupo.idGrupo ?? null;
    this.mostrarFormulario = true;
  }

  guardar(): void {
    this.errorMsg = '';
    this.successMsg = '';
    this.loading = true;

    if (this.modoEdicion && this.grupoEditandoId !== null) {
      // Edición — llama al PUT del backend
      this.grupoService.update(this.grupoEditandoId, this.formulario).subscribe({
        next: () => {
          this.successMsg = 'Grupo actualizado correctamente';
          this.cancelar();
          this.cargarGrupos();
        },
        error: () => {
          this.errorMsg = 'Error al actualizar el grupo';
          this.loading = false;
        }
      });
    } else {
      // Creación — llama al POST del backend
      this.grupoService.create(this.formulario).subscribe({
        next: () => {
          this.successMsg = 'Grupo creado correctamente';
          this.cancelar();
          this.cargarGrupos();
        },
        error: () => {
          this.errorMsg = 'Error al crear el Grupo';
          this.loading = false;
        }
      });
    }
  }


  eliminar(id: number): void {
    this.grupoService.delete(id).subscribe({
      next: () => {
        this.successMsg = 'Grupo eliminado correctamente';
        this.cargarGrupos();
      },
      error: () => this.errorMsg = 'Error al eliminar el grupo'
    });
  }

  cancelar(): void {
    this.mostrarFormulario = false;
    this.formulario = this.formularioVacio();
    this.modoEdicion = false;
    this.grupoEditandoId = null;
    this.loading = false;
  }

  private formularioVacio(): Grupo {
    return {
      nombreGrupo: '',
      descripcionGrupo: '',
      imagenGrupo: '',
      paisOrigenGrupo: '',
      tiempoDuracion: 0,
      lenguajeGrupo: ''
    };
  }


}
