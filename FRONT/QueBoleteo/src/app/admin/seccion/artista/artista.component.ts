import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ButtonModule } from 'primeng/button';
import { InputTextModule } from 'primeng/inputtext';
import { SelectModule } from 'primeng/select';
import { TableModule } from 'primeng/table';
import { TagModule } from 'primeng/tag';
import { TextareaModule } from 'primeng/textarea';
import { ArtistaService, Artista, ArtGen } from '../../../core/services/artista.service';
import { GeneroService, Genero } from '../../../core/services/genero.service';
import { Component, OnInit, ChangeDetectorRef } from '@angular/core';

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

  // Panel de géneros
  artistaGeneroAbierto: number | null = null;
  generosAsociados: ArtGen[] = [];
  todosLosGeneros: Genero[] = [];
  generoSeleccionado: Genero | null = null;
  nombreArtistaPanel: string = '';

  errorGenero: string = '';
  successGenero: string = '';

  constructor(private artistaService: ArtistaService,
  private generoService: GeneroService,
              private cdr: ChangeDetectorRef
  ) {}

  // Se ejecuta automáticamente al abrir la pantalla
  ngOnInit(): void {
    this.cargarArtistas();
    this.cargarTodosLosGeneros();
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

  cargarTodosLosGeneros(): void {
    this.generoService.getAll().subscribe({
      next: (data) => this.todosLosGeneros = data,
      error: () => {}
    });
  }

  abrirCrear(): void {
    this.formulario = this.formularioVacio();
    this.modoEdicion = false;
    this.artistaEditandoId = null;
    this.mostrarFormulario = true;
    this.cerrarPanelGeneros();
  }

  abrirEditar(artista: Artista): void {
    this.formulario = { ...artista };
    this.modoEdicion = true;
    this.artistaEditandoId = artista.idArtista ?? null;
    this.mostrarFormulario = true;
    this.cerrarPanelGeneros();
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

  // Panel de géneros

  togglePanelGeneros(artista: Artista): void {
    const id = artista.idArtista!;

    if (this.artistaGeneroAbierto === id) {
      this.cerrarPanelGeneros();
      return;
    }

    this.artistaGeneroAbierto = id;
    this.nombreArtistaPanel = artista.nombreArtista;
    this.generoSeleccionado = null;
    this.errorGenero = '';
    this.successGenero = '';
    this.cargarGenerosAsociados(id);
  }



  cargarGenerosAsociados(idArtista: number): void {
    this.artistaService.getGenerosByArtista(idArtista).subscribe({
      next: (data) => this.generosAsociados = data ?? [],
      error: () => this.generosAsociados = []
    });
  }


  // Devuelve el nombre de un género dado su ID
  nombreGenero(idGenero: number): string {
    return this.todosLosGeneros.find(g => g.idGenero === idGenero)?.nombreGenero ?? `#${idGenero}`;
  }

  asociarGenero(): void {
    if (!this.generoSeleccionado || !this.artistaGeneroAbierto) return;

    this.errorGenero = '';
    this.successGenero = '';

    const dto: ArtGen = {
      idArtista: this.artistaGeneroAbierto,
      idGenero: this.generoSeleccionado.idGenero!
    };

    this.artistaService.asociarGenero(dto).subscribe({
      next: () => {
        this.successGenero = 'Género asociado correctamente';
        this.generoSeleccionado = null;
        this.cargarGenerosAsociados(this.artistaGeneroAbierto!);
      },
      error: (err) => {
        this.errorGenero = err.status === 406
          ? 'Ese género ya está asociado'
          : 'Error al asociar el género';
      }
    });
  }

  desasociarGenero(idGenero: number): void {
    if (!this.artistaGeneroAbierto) return;
    this.errorGenero = '';
    this.successGenero = '';

    this.artistaService.desasociarGenero(this.artistaGeneroAbierto, idGenero).subscribe({
      next: () => {
        this.successGenero = 'Género desasociado correctamente';
        this.cargarGenerosAsociados(this.artistaGeneroAbierto!);
      },
      error: () => this.errorGenero = 'Error al desasociar el género'
    });
  }

  // Géneros disponibles = todos los géneros menos los ya asociados
  get generosDisponibles(): Genero[] {
    const asociadosIds = new Set(this.generosAsociados.map(a => a.idGenero));
    return this.todosLosGeneros.filter(g => !asociadosIds.has(g.idGenero!));
  }

  cerrarPanelGeneros(): void {
    this.artistaGeneroAbierto = null;
    this.generosAsociados = [];
    this.generoSeleccionado = null;
    this.nombreArtistaPanel = '';
    this.errorGenero = '';
    this.successGenero = '';
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
