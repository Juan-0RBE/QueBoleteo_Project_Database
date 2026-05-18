import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ButtonModule } from 'primeng/button';
import { InputTextModule } from 'primeng/inputtext';
import { SelectModule } from 'primeng/select';
import { TableModule } from 'primeng/table';
import { TagModule } from 'primeng/tag';
import { TextareaModule } from 'primeng/textarea';
import { GrupoService, Grupo, GruGen, GruArt } from '../../../core/services/grupo.service';
import { GeneroService, Genero } from '../../../core/services/genero.service';
import { ArtistaService, Artista } from '../../../core/services/artista.service';


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

  // Panel de géneros
  grupoGeneroAbierto: number | null = null;
  generosAsociados: GruGen[] = [];
  todosLosGeneros: Genero[] = [];
  generoSeleccionado: Genero | null = null;
  nombreGrupoPanel: string = '';

  errorGenero: string = '';
  successGenero: string = '';

  // Panel de artistas

  // ── Panel de artistas ─────────────────────────────────
  grupoArtistaAbierto: number | null = null;
  artistasAsociados: GruArt[] = [];
  todosLosArtistas: Artista[] = [];
  artistaSeleccionado: Artista | null = null;
  rolNuevo: string = '';
  // Para edición de rol inline
  editandoRol: number | null = null;  // idArtista que está en modo edición
  rolEditando: string = '';
  errorArtista: string = '';
  successArtista: string = '';

  constructor(private grupoService: GrupoService,
              private generoService: GeneroService,
              private artistaService: ArtistaService,
              private cdr: ChangeDetectorRef
  ) {}

  // Se ejecuta automáticamente al abrir la pantalla
  ngOnInit(): void {
    this.cargarGrupos();
    this.cargarTodosLosGeneros();
    this.cargarTodosLosArtistas();
  }

  // Llama al backend y llena la tabla
  cargarGrupos(): void {
    this.grupoService.getAll().subscribe({
      next: (data) => {
        this.grupos = data;
        this.cdr.detectChanges();
      },
      error: () => this.errorMsg = 'Error al cargar los grupos'
    });
  }

  cargarTodosLosGeneros(): void {
    this.generoService.getAll().subscribe({
      next: (data) => {
        this.todosLosGeneros = data;
        this.cdr.detectChanges();
      },
      error: () => {}
    });
  }

  cargarTodosLosArtistas(): void {
    this.artistaService.getAll().subscribe({
      next: (data) => {
        this.todosLosArtistas = data;
        this.cdr.detectChanges();
      },
      error: () => {}
    });
  }

  abrirCrear(): void {
    this.formulario = this.formularioVacio();
    this.modoEdicion = false;
    this.grupoEditandoId = null;
    this.mostrarFormulario = true;
    this.cerrarPanelGeneros();
    this.cerrarPanelArtistas();
  }

  abrirEditar(grupo: Grupo): void {
    this.formulario = { ...grupo};
    this.modoEdicion = true;
    this.grupoEditandoId = grupo.idGrupo ?? null;
    this.mostrarFormulario = true;
    this.cerrarPanelGeneros();
    this.cerrarPanelArtistas();
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


  // Panel de géneros

  togglePanelGeneros(grupo: Grupo): void {
    const id = grupo.idGrupo!;

    if (this.grupoGeneroAbierto === id) {
      this.cerrarPanelGeneros();
      return;
    }


    this.grupoGeneroAbierto = id;
    this.nombreGrupoPanel = grupo.nombreGrupo;
    this.generoSeleccionado = null;
    this.errorGenero = '';
    this.successGenero = '';
    this.cargarGenerosAsociados(id);
  }



  cargarGenerosAsociados(idGrupo: number): void {
    this.grupoService.getGenerosByGrupo(idGrupo).subscribe({
      next: (data) => {
        this.generosAsociados = data ?? [];
        this.cdr.detectChanges();
      },
      error: () => this.generosAsociados = []
    });
  }


  // Devuelve el nombre de un género dado su ID
  nombreGenero(idGenero: number): string {
    return this.todosLosGeneros.find(g => g.idGenero === idGenero)?.nombreGenero ?? `#${idGenero}`;
  }

  asociarGenero(): void {
    if (!this.generoSeleccionado || !this.grupoGeneroAbierto) return;

    this.errorGenero = '';
    this.successGenero = '';

    const dto: GruGen = {
      idGrupo: this.grupoGeneroAbierto,
      idGenero: this.generoSeleccionado.idGenero!
    };

    this.grupoService.asociarGenero(dto).subscribe({
      next: () => {
        this.successGenero = 'Género asociado correctamente';
        this.generoSeleccionado = null;
        this.cargarGenerosAsociados(this.grupoGeneroAbierto!);
      },
      error: (err) => {
        this.errorGenero = err.status === 406
          ? 'Ese género ya está asociado'
          : 'Error al asociar el género';
      }
    });
  }

  desasociarGenero(idGenero: number): void {
    if (!this.grupoGeneroAbierto) return;
    this.errorGenero = '';
    this.successGenero = '';

    this.grupoService.desasociarGenero(this.grupoGeneroAbierto, idGenero).subscribe({
      next: () => {
        this.successGenero = 'Género desasociado correctamente';
        this.cargarGenerosAsociados(this.grupoGeneroAbierto!);
      },
      error: () => this.errorGenero = 'Error al desasociar el género'
    });
  }

  get generosDisponibles(): Genero[] {
    const asociadosIds = new Set(this.generosAsociados.map(a => a.idGenero));
    return this.todosLosGeneros.filter(g => !asociadosIds.has(g.idGenero!));
  }

  cerrarPanelGeneros(): void {
    this.grupoGeneroAbierto = null;
    this.generosAsociados = [];
    this.generoSeleccionado = null;
    this.nombreGrupoPanel = '';
    this.errorGenero = '';
    this.successGenero = '';
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


  // Panel de artistas

  togglePanelArtistas(grupo: Grupo): void {
    const id = grupo.idGrupo!;
    if (this.grupoArtistaAbierto === id) {
      this.cerrarPanelArtistas();
      return;
    }
    this.cerrarPanelGeneros();
    this.grupoArtistaAbierto = id;
    this.nombreGrupoPanel = grupo.nombreGrupo;
    this.artistaSeleccionado = null;
    this.rolNuevo = '';
    this.editandoRol = null;
    this.errorArtista = '';
    this.successArtista = '';
    this.cargarArtistasAsociados(id);
  }

  cerrarPanelArtistas(): void {
    this.grupoArtistaAbierto = null;
    this.artistasAsociados = [];
    this.artistaSeleccionado = null;
    this.rolNuevo = '';
    this.editandoRol = null;
    this.errorArtista = '';
    this.successArtista = '';
  }

  cargarArtistasAsociados(idGrupo: number): void {
    this.grupoService.getArtistasByGrupo(idGrupo).subscribe({
      next: (data) => {
        this.artistasAsociados = data ?? [];
        this.cdr.detectChanges();
      },
      error: () => this.artistasAsociados = []
    });
  }

  nombreArtista(idArtista: number): string {
    return this.todosLosArtistas.find(a => a.idArtista === idArtista)?.nombreArtista ?? `#${idArtista}`;
  }

  asociarArtista(): void {
    if (!this.artistaSeleccionado || !this.grupoArtistaAbierto) return;
    this.errorArtista = '';
    this.successArtista = '';

    const dto: GruArt = {
      idGrupo: this.grupoArtistaAbierto,
      idArtista: this.artistaSeleccionado.idArtista!,
      rol: this.rolNuevo
    };

    this.grupoService.asociarArtista(dto).subscribe({
      next: () => {
        this.successArtista = 'Artista asociado correctamente';
        this.artistaSeleccionado = null;
        this.rolNuevo = '';
        this.cargarArtistasAsociados(this.grupoArtistaAbierto!);
      },
      error: (err) => {
        this.errorArtista = err.status === 406
          ? 'Ese artista ya está en el grupo'
          : 'Error al asociar el artista';
      }
    });
  }

  iniciarEdicionRol(ga: GruArt): void {
    this.editandoRol = ga.idArtista;
    this.rolEditando = ga.rol;
  }

  guardarRol(idArtista: number): void {
    if (!this.grupoArtistaAbierto) return;
    this.errorArtista = '';
    this.successArtista = '';

    const dto: GruArt = {
      idGrupo: this.grupoArtistaAbierto,
      idArtista: idArtista,
      rol: this.rolEditando
    };

    this.grupoService.actualizarRol(this.grupoArtistaAbierto, idArtista, dto).subscribe({
      next: () => {
        this.successArtista = 'Rol actualizado correctamente';
        this.editandoRol = null;
        this.cargarArtistasAsociados(this.grupoArtistaAbierto!);
      },
      error: () => this.errorArtista = 'Error al actualizar el rol'
    });
  }

  cancelarEdicionRol(): void {
    this.editandoRol = null;
    this.rolEditando = '';
  }

  desasociarArtista(idArtista: number): void {
    if (!this.grupoArtistaAbierto) return;
    this.errorArtista = '';
    this.successArtista = '';

    this.grupoService.desasociarArtista(this.grupoArtistaAbierto, idArtista).subscribe({
      next: () => {
        this.successArtista = 'Artista removido del grupo';
        this.cargarArtistasAsociados(this.grupoArtistaAbierto!);
      },
      error: () => this.errorArtista = 'Error al desasociar el artista'
    });
  }

  get artistasDisponibles(): Artista[] {
    const asociadosIds = new Set(this.artistasAsociados.map(a => a.idArtista));
    return this.todosLosArtistas.filter(a => !asociadosIds.has(a.idArtista!));
  }

}
