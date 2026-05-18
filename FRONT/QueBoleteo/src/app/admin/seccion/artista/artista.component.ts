import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ButtonModule } from 'primeng/button';
import { InputTextModule } from 'primeng/inputtext';
import { SelectModule } from 'primeng/select';
import { TableModule } from 'primeng/table';
import { TagModule } from 'primeng/tag';
import { TextareaModule } from 'primeng/textarea';
import { ArtistaService, Artista } from '../../../core/services/artista.service';

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
export class AdminArtistaComponent implements OnInit {

  mostrarFormulario = false;
  modoEdicion = false;
  artistaEditandoId: number | null = null;

  artistas: Artista[] = [];
  formulario: Artista = this.formularioVacio();

  errorMsg: string = '';
  successMsg: string = '';
  loading: boolean = false;

  constructor(private artistaService: ArtistaService, private cdr: ChangeDetectorRef) {}

  // Se ejecuta automáticamente al abrir la pantalla
  ngOnInit(): void {
    this.cargarArtistas();
  }

  // Llama al backend y llena la tabla
  cargarArtistas(): void {
    this.artistaService.getAll().subscribe({
      next: (data) => {
        this.artistas = data;
        this.cdr.detectChanges();
      },
      error: () => this.errorMsg = 'Error al cargar los artistas'
    });
  }

  abrirCrear(): void {
    this.formulario = this.formularioVacio();
    this.modoEdicion = false;
    this.artistaEditandoId = null;
    this.mostrarFormulario = true;
  }

  abrirEditar(artista: Artista): void {
    this.formulario = { ...artista };
    this.modoEdicion = true;
    this.artistaEditandoId = artista.idArtista ?? null;
    this.mostrarFormulario = true;
  }

  guardar(): void {
    this.errorMsg = '';
    this.successMsg = '';
    this.loading = true;

    if (this.modoEdicion && this.artistaEditandoId !== null) {
      // Edición — llama al PUT del backend
      this.artistaService.update(this.artistaEditandoId, this.formulario).subscribe({
        next: () => {
          this.successMsg = 'Artista actualizado correctamente';
          this.cancelar();
          this.cargarArtistas();
        },
        error: () => {
          this.errorMsg = 'Error al actualizar el artista';
          this.loading = false;
        }
      });
    } else {
      // Creación — llama al POST del backend
      this.artistaService.create(this.formulario).subscribe({
        next: () => {
          this.successMsg = 'Artista creado correctamente';
          this.cancelar();
          this.cargarArtistas();
        },
        error: () => {
          this.errorMsg = 'Error al crear el artista';
          this.loading = false;
        }
      });
    }
  }

  eliminar(id: number): void {
    this.artistaService.delete(id).subscribe({
      next: () => {
        this.successMsg = 'Artista eliminado correctamente';
        this.cargarArtistas();
      },
      error: () => this.errorMsg = 'Error al eliminar el artista'
    });
  }

  cancelar(): void {
    this.mostrarFormulario = false;
    this.formulario = this.formularioVacio();
    this.modoEdicion = false;
    this.artistaEditandoId = null;
    this.loading = false;
  }

  private formularioVacio(): Artista {
    return {
      nombreArtista: '',
      descripcionArtista: '',
      imagenArtista: '',
      paisOrigenArtista: '',
      edadArtista: 0,
      lenguajeArtista: ''
    };
  }
}
