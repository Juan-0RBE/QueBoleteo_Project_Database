import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ButtonModule } from 'primeng/button';
import { InputTextModule } from 'primeng/inputtext';
import { TableModule } from 'primeng/table';
import { DatePickerModule } from 'primeng/datepicker';
import { TextareaModule } from 'primeng/textarea';
import { TourService, Tour } from '../../../core/services/tour.service';
import { Artista } from '../../../core/services/artista.service';

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
  tourEditandoId: number | null = null;

  tours: Tour[] = [];
  formulario: Tour = this.formularioVacio();

  errorMsg: string = '';
  successMsg: string = '';
  loading: boolean = false;

  constructor(private tourService: TourService) {}

  // Se ejecuta automáticamente al abrir la pantalla
  ngOnInit(): void {
    this.cargarTours();
  }

  // Llama al backend y llena la tabla
  cargarTours(): void {
    this.tourService.getAll().subscribe({
      next: (data) => this.tours = data,
      error: () => this.errorMsg = 'Error al cargar los tours'
    });
  }

  abrirCrear(): void {
    this.formulario = this.formularioVacio();
    this.modoEdicion = false;
    this.tourEditandoId = null;
    this.mostrarFormulario = true;
  }

  abrirEditar(tour: Tour): void {
    this.formulario = { ...tour };
    this.modoEdicion = true;
    this.tourEditandoId = tour.idTour ?? null;
    this.mostrarFormulario = true;
  }

  guardar(): void {
    this.errorMsg = '';
    this.successMsg = '';
    this.loading = true;

    if (this.modoEdicion && this.tourEditandoId !== null) {
      // Edición — llama al PUT del backend
      this.tourService.update(this.tourEditandoId, this.formulario).subscribe({
        next: () => {
          this.successMsg = 'Tour actualizado correctamente';
          this.cancelar();
          this.cargarTours();
        },
        error: () => {
          this.errorMsg = 'Error al actualizar el tour';
          this.loading = false;
        }
      });
    } else {
      // Creación — llama al POST del backend
      this.tourService.create(this.formulario).subscribe({
        next: () => {
          this.successMsg = 'Tour creado correctamente';
          this.cancelar();
          this.cargarTours();
        },
        error: () => {
          this.errorMsg = 'Error al crear el tour';
          this.loading = false;
        }
      });
    }
  }

  eliminar(id: number): void {
    this.tourService.delete(id).subscribe({
      next: () => {
        this.successMsg = 'Tour eliminado correctamente';
        this.cargarTours();
      },
      error: () => this.errorMsg = 'Error al eliminar el tour'
    });
  }

  cancelar(): void {
    this.mostrarFormulario = false;
    this.formulario = this.formularioVacio();
    this.modoEdicion = false;
    this.tourEditandoId = null;
    this.loading = false;
  }

  private formularioVacio(): Tour {
    return {
      nombreTour: '',
      descripcionTour: '',
      imagenTour: '',
      fechaInicial: '',
      fechaFinal: ''
    };
  }

  formatFecha(f: Date | null): string { return f ? new Date(f).toLocaleDateString('es-CO') : '—'; }
}
