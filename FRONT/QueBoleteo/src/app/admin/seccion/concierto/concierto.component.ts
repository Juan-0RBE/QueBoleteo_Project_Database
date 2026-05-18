import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { ButtonModule } from 'primeng/button';
import { InputTextModule } from 'primeng/inputtext';
import { SelectModule } from 'primeng/select';
import { TableModule } from 'primeng/table';
import { DatePickerModule } from 'primeng/datepicker';
import { TextareaModule } from 'primeng/textarea';
import { InputNumberModule } from 'primeng/inputnumber';
<<<<<<< Updated upstream
import { ConciertoService} from '../../../core/services/concierto.service';
import { environment } from '../../../../environments/environment';

export interface Zona {
  idZona: number;
  nombreZona: string;
  tieneAsiento: boolean;
}

export interface Sede {
  nombreSede: string;
  ciudad: string;
  direccionSede?: string;
  tieneAccesibilidad?: boolean;
  imagenSede?: string;
}

export interface ZonaConcierto {
  id: number;
  zonaId: number;
  nombreZona: string;
  precio: number;
  cantidadDisponible: number;
  descripcion: string;
}

export interface Concierto {
  id: number;
  nombre: string;
  artista: string;
  nombreSede: string;
  fecha: Date | null;
  edadMinima: number;
  descripcion: string;
  estado: string;
  zonas: ZonaConcierto[];
  expandida?: boolean;
}

export interface ConciertoDTO {
  idConcierto: number;
  nombreConcierto: string;
  descripcionConcierto: string;
  imagenConcierto: string;
  edadMinima: number;
  recomendacion: string;
  fechaConcierto: string; // ISO String (yyyy-MM-dd)
  estadoConcierto: string;
  idTour: number;
  nombreSede: string;
}

export interface ZonaConciertoBackend {
  idConciertoZona: number;
  idZona: number;
  precio: number;
  cantidadDisponible: number; // o "cantidadDisponible" según Java
}
=======
import {
  ConciertoService,
  ConciertoDTO,
  ConArt,
  ConGru,
  ConOrg,
  ZonaConcierto,
} from '../../../core/services/concierto.service';
import { ArtistaService, Artista } from '../../../core/services/artista.service';
import { GrupoService, Grupo } from '../../../core/services/grupo.service';
import { OrganizadorService, Organizador } from '../../../core/services/organizador.service';
import { TourService, Tour } from '../../../core/services/tour.service';
import { SedeService, Sede, Zona } from '../../../core/services/sede.service';
import { catchError } from 'rxjs/operators';
import { of } from 'rxjs';
>>>>>>> Stashed changes

@Component({
  selector: 'app-admin-concierto',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    ButtonModule,
    InputTextModule,
    SelectModule,
    TableModule,
    DatePickerModule,
    TextareaModule,
    InputNumberModule,
  ],
  templateUrl: './concierto.component.html',
  styleUrls: ['./concierto.component.css'],
})
export class AdminConciertoComponent implements OnInit {
<<<<<<< Updated upstream

  //Estado concierto
=======
  // ── CRUD concierto ────────────────────────────────────
>>>>>>> Stashed changes
  mostrarFormulario = false;
  modoEdicion = false;
  conciertos: Concierto[] = [];
  formulario: Concierto = this.conciertoVacio();

  //Estado zona
  conciertoConFormularioZona: number | null = null;
  modoEdicionZona = false;
  formularioZona: ZonaConcierto = this.zonaVacia();

  //Datos del backend
  sedes: Sede[] = [];
  todasLasZonas: Zona[] = [];
  zonasDeSede: Zona[] = [];
  cargandoSedes = false;
  cargandoZonas = false;
  errorCarga = '';

  errorMsg: string = '';
  successMsg: string = '';
  loading: boolean = false;

  estados = [
    { label: 'Programado', value: 'Programado' },
    { label: 'Cancelado', value: 'Cancelado' },
    { label: 'Finalizado', value: 'Finalizado' },
  ];

<<<<<<< Updated upstream
  constructor(private http: HttpClient, private conciertoService: ConciertoService, private cdr: ChangeDetectorRef) {}
=======
  // ── Panel de artistas ─────────────────────────────────
  conciertoArtistaAbierto: number | null = null;
  artistasAsociados: ConArt[] = [];
  todosLosArtistas: Artista[] = [];
  artistaSeleccionado: Artista | null = null;
  errorArtista = '';
  successArtista = '';

  // ── Panel de grupos ───────────────────────────────────
  conciertoGrupoAbierto: number | null = null;
  gruposAsociados: ConGru[] = [];
  todosLosGrupos: Grupo[] = [];
  grupoSeleccionado: Grupo | null = null;
  errorGrupo = '';
  successGrupo = '';

  // ── Panel de organizadores ────────────────────────────
  conciertoOrgAbierto: number | null = null;
  organizadoresAsociados: ConOrg[] = [];
  todosLosOrganizadores: Organizador[] = [];
  organizadorSeleccionado: Organizador | null = null;
  errorOrg = '';
  successOrg = '';

  // ── Panel de zonas ────────────────────────────────────
  conciertoZonaAbierto: number | null = null;
  zonasConcierto: ZonaConcierto[] = [];
  todasLasZonas: Zona[] = []; // Zona viene de sede.service.ts
  zonaSeleccionada: Zona | null = null;
  precioNuevo: number = 0;
  // Para edición inline de precio/cantidad
  editandoZona: number | null = null; // idPrecio en edición
  precioEditando: number = 0;
  cantidadEditando: number = 0;
  errorZona = '';
  successZona = '';

  // Nombre del concierto activo en cualquier panel
  nombreConciertoPanel = '';

  constructor(
    private conciertoService: ConciertoService,
    private artistaService: ArtistaService,
    private grupoService: GrupoService,
    private organizadorService: OrganizadorService,
    private tourService: TourService,
    private sedeService: SedeService,
    private cdr: ChangeDetectorRef,
  ) {}
>>>>>>> Stashed changes

  ngOnInit(): void {
    this.cargarSedes();
    this.cargarZonas();
    this.cargarConciertos();
  }

<<<<<<< Updated upstream
  //Carga desde backend

  cargarSedes(): void {
    this.cargandoSedes = true;
    this.http.get<Sede[]>(`${environment.apiUrl}/sede/all`).subscribe({
      next: (sedes) => {
        this.sedes = sedes;
        this.cargandoSedes = false;
        this.cdr.detectChanges();
      },
      error: (err) => {
        console.error('Error al cargar sedes:', err);
        this.errorCarga = 'No se pudieron cargar las sedes.';
        this.cargandoSedes = false;
      }
    });
  }

  cargarZonas(): void {
    this.cargandoZonas = true;
    this.http.get<Zona[]>(`${environment.apiUrl}/zona/all`).subscribe({
      next: (zonas) => {
        this.todasLasZonas = zonas;
        this.cargandoZonas = false;
        this.cdr.detectChanges();
      },
      error: (err) => {
        console.error('Error al cargar zonas:', err);
        this.cargandoZonas = false;
      }
    });
  }

  cargarConciertos(): void {
    this.conciertoService.getAll().subscribe({
      next: (dtos: ConciertoDTO[]) => {
        // Transformamos lo que viene de Java al formato del Front
        this.conciertos = dtos.map(dto => this.mapearAFront(dto));
        this.cdr.detectChanges();
      },
      error: () => {
        this.errorMsg = 'Error al cargar los conciertos del servidor.';
      }
=======
  // ── Carga inicial ─────────────────────────────────────

  cargarConciertos(): void {
    this.conciertoService
      .getAll()
      .pipe(catchError(() => of([])))
      .subscribe({
        next: (data) => {
          this.conciertos = data ?? [];
          this.cdr.detectChanges();
        },
      });
  }

  cargarSedes(): void {
    this.sedeService
      .getAllSedes()
      .pipe(catchError(() => of([])))
      .subscribe({ next: (data) => (this.sedes = data ?? []) });
  }

  cargarTours(): void {
    this.tourService
      .getAll()
      .pipe(catchError(() => of([])))
      .subscribe({ next: (data) => (this.tours = data ?? []) });
  }

  cargarTodosLosArtistas(): void {
    this.artistaService.getAll().subscribe({
      next: (data) => (this.todosLosArtistas = data),
      error: () => {},
    });
  }

  cargarTodosLosGrupos(): void {
    this.grupoService.getAll().subscribe({
      next: (data) => (this.todosLosGrupos = data),
      error: () => {},
    });
  }

  cargarTodosLosOrganizadores(): void {
    this.organizadorService.getAll().subscribe({
      next: (data) => (this.todosLosOrganizadores = data),
      error: () => {},
>>>>>>> Stashed changes
    });
  }

  //Opciones para el selector
  get opcionesSedes() {
    return this.sedes.map(s => ({ label: s.nombreSede, value: s.nombreSede }));
  }

  //Cuando cambia la sede filtra las zonas
  onSedeChange(nombreSede: string): void {
    this.formulario.nombreSede = nombreSede;
    this.zonasDeSede = this.todasLasZonas;
  }

  //Zonas disponibles para el panel de zonas de un concierto
  zonasDisponibles(concierto: Concierto): Zona[] {
    if (!concierto.nombreSede) return [];
    return this.todasLasZonas;
  }

  onZonaChange(zona: Zona): void {
    this.formularioZona.zonaId    = zona.idZona;
    this.formularioZona.nombreZona = zona.nombreZona;
  }

  //CRUD Concierto

  conciertoVacio(): Concierto {
    return { id: 0, nombre: '', artista: '', nombreSede: '', fecha: null, edadMinima: 0, descripcion: '', estado: 'Programado', zonas: [], expandida: false };
  }

  zonaVacia(): ZonaConcierto {
    return { id: 0, zonaId: 0, nombreZona: '', precio: 0, cantidadDisponible: 0, descripcion: '' };
  }

  abrirCrear(): void {
    this.formulario = this.conciertoVacio();
    this.zonasDeSede = [];
    this.modoEdicion = false;
    this.mostrarFormulario = true;
  }

  abrirEditar(c: Concierto): void {
    this.formulario = { ...c, zonas: [...c.zonas] };
    if (c.nombreSede) this.onSedeChange(c.nombreSede);
    this.modoEdicion = true;
    this.mostrarFormulario = true;
  }

  guardar(): void {
    if (!this.formulario.nombre || !this.formulario.artista) return;

    this.errorMsg = '';
    this.successMsg = '';
    this.loading = true;

<<<<<<< Updated upstream
    // Convertimos nuestro formulario visual al JSON de Java
    const bodyBackend = this.mapearABack(this.formulario);
=======
    // Formatear fecha si viene como Date
    const body: ConciertoDTO = {
      ...this.formulario,
      ...this.formulario,
      fechaConcierto:
        (this.formulario.fechaConcierto as any) instanceof Date
          ? (this.formulario.fechaConcierto as any).toISOString()
          : this.formulario.fechaConcierto,
    };
>>>>>>> Stashed changes

    if (this.modoEdicion) {
      // Caso EDITAR (PUT)
      this.conciertoService.update(this.formulario.id, bodyBackend).subscribe({
        next: () => {
          this.successMsg = 'Concierto actualizado con éxito';
          this.cargarConciertos(); // Recarga limpia desde la Base de Datos
          this.cancelar();
        },
        error: () => {
          this.errorMsg = 'Error al actualizar el concierto';
          this.loading = false;
<<<<<<< Updated upstream
        }
=======
        },
>>>>>>> Stashed changes
      });
    } else {
      // Caso CREAR (POST)
      this.conciertoService.create(bodyBackend).subscribe({
        next: () => {
          this.successMsg = 'Concierto creado con éxito';
          this.cargarConciertos(); // Trae la lista fresca incluyendo el ID autogenerado por Java
          this.cancelar();
        },
        error: () => {
<<<<<<< Updated upstream
          this.errorMsg = 'Error al registrar el concierto';
          this.loading = false;
        }
=======
          this.errorMsg = 'Error al crear el concierto';
          this.loading = false;
        },
>>>>>>> Stashed changes
      });
    }
  }

  eliminar(id: number): void {
<<<<<<< Updated upstream
    if (confirm('¿Estás seguro de eliminar este concierto?')) {
      this.conciertoService.delete(id).subscribe({
        next: () => {
          this.successMsg = 'Concierto eliminado permanentemente';
          this.cargarConciertos();
        },
        error: () => {
          this.errorMsg = 'No se pudo eliminar el concierto';
        }
      });
    }
=======
    this.conciertoService.delete(id).subscribe({
      next: () => {
        this.successMsg = 'Concierto eliminado';
        this.cargarConciertos();
      },
      error: () => (this.errorMsg = 'Error al eliminar el concierto'),
    });
>>>>>>> Stashed changes
  }

  cancelar(): void {
    this.mostrarFormulario = false;
    this.zonasDeSede = [];
    this.formulario = this.conciertoVacio();
    this.loading = false;
  }

  toggleExpandir(concierto: Concierto): void {
    concierto.expandida = !concierto.expandida;
    if (!concierto.expandida && this.conciertoConFormularioZona === concierto.id) {
      this.cancelarZona();
    }
  }

  //CRUD Zona

  abrirCrearZona(conciertoId: number): void {
    this.formularioZona = this.zonaVacia();
    this.modoEdicionZona = false;
    this.conciertoConFormularioZona = conciertoId;
  }

<<<<<<< Updated upstream
  abrirEditarZona(conciertoId: number, zona: ZonaConcierto): void {
    this.formularioZona = { ...zona };
    this.modoEdicionZona = true;
    this.conciertoConFormularioZona = conciertoId;
=======
  // ── Panel de artistas ─────────────────────────────────

  togglePanelArtistas(c: ConciertoDTO): void {
    const id = c.idConcierto!;
    if (this.conciertoArtistaAbierto === id) {
      this.cerrarPanelArtistas();
      return;
    }
    this.cerrarTodosLosPaneles();
    this.conciertoArtistaAbierto = id;
    this.nombreConciertoPanel = c.nombreConcierto;
    this.errorArtista = '';
    this.successArtista = '';
    this.cargarArtistasAsociados(id);
>>>>>>> Stashed changes
  }

  guardarZona(concierto: Concierto): void {
    if (!this.formularioZona.nombreZona) return;
    if (this.modoEdicionZona) {
      const idx = concierto.zonas.findIndex(z => z.id === this.formularioZona.id);
      if (idx !== -1) concierto.zonas[idx] = { ...this.formularioZona };
    } else {
      const nuevoId = concierto.zonas.length ? Math.max(...concierto.zonas.map(z => z.id)) + 1 : 1;
      concierto.zonas = [...concierto.zonas, { ...this.formularioZona, id: nuevoId }];
    }
    this.cancelarZona();
  }

<<<<<<< Updated upstream
  eliminarZona(concierto: Concierto, zonaId: number): void {
    concierto.zonas = concierto.zonas.filter(z => z.id !== zonaId);
  }

  cancelarZona(): void {
    this.conciertoConFormularioZona = null;
    this.formularioZona = this.zonaVacia();
  }

  formatFecha(fecha: Date | null): string {
    return fecha ? new Date(fecha).toLocaleDateString('es-CO') : '—';
=======
  cargarArtistasAsociados(idConcierto: number): void {
    this.conciertoService.getArtistasByConcierto(idConcierto).subscribe({
      next: (data) => (this.artistasAsociados = data ?? []),
      error: () => (this.artistasAsociados = []),
    });
  }

  nombreArtista(idArtista: number): string {
    return (
      this.todosLosArtistas.find((a) => a.idArtista === idArtista)?.nombreArtista ?? `#${idArtista}`
    );
  }

  asociarArtista(): void {
    if (!this.artistaSeleccionado || !this.conciertoArtistaAbierto) return;
    this.errorArtista = '';
    this.successArtista = '';
    const dto: ConArt = {
      idConcierto: this.conciertoArtistaAbierto,
      idArtista: this.artistaSeleccionado.idArtista!,
    };
    this.conciertoService.asociarArtista(dto).subscribe({
      next: () => {
        this.successArtista = 'Artista asociado';
        this.artistaSeleccionado = null;
        this.cargarArtistasAsociados(this.conciertoArtistaAbierto!);
      },
      error: (err) => {
        this.errorArtista =
          err.status === 406 ? 'Ese artista ya está asociado' : 'Error al asociar';
      },
    });
  }

  desasociarArtista(idArtista: number): void {
    if (!this.conciertoArtistaAbierto) return;
    this.conciertoService.desasociarArtista(this.conciertoArtistaAbierto, idArtista).subscribe({
      next: () => {
        this.successArtista = 'Artista desasociado';
        this.cargarArtistasAsociados(this.conciertoArtistaAbierto!);
      },
      error: () => (this.errorArtista = 'Error al desasociar'),
    });
  }

  get artistasDisponibles(): Artista[] {
    const ids = new Set(this.artistasAsociados.map((a) => a.idArtista));
    return this.todosLosArtistas.filter((a) => !ids.has(a.idArtista!));
  }

  // ── Panel de grupos ───────────────────────────────────

  togglePanelGrupos(c: ConciertoDTO): void {
    const id = c.idConcierto!;
    if (this.conciertoGrupoAbierto === id) {
      this.cerrarPanelGrupos();
      return;
    }
    this.cerrarTodosLosPaneles();
    this.conciertoGrupoAbierto = id;
    this.nombreConciertoPanel = c.nombreConcierto;
    this.errorGrupo = '';
    this.successGrupo = '';
    this.cargarGruposAsociados(id);
  }

  cerrarPanelGrupos(): void {
    this.conciertoGrupoAbierto = null;
    this.gruposAsociados = [];
    this.grupoSeleccionado = null;
    this.errorGrupo = '';
    this.successGrupo = '';
  }

  cargarGruposAsociados(idConcierto: number): void {
    this.conciertoService.getGruposByConcierto(idConcierto).subscribe({
      next: (data) => (this.gruposAsociados = data ?? []),
      error: () => (this.gruposAsociados = []),
    });
  }

  nombreGrupo(idGrupo: number): string {
    return this.todosLosGrupos.find((g) => g.idGrupo === idGrupo)?.nombreGrupo ?? `#${idGrupo}`;
  }

  asociarGrupo(): void {
    if (!this.grupoSeleccionado || !this.conciertoGrupoAbierto) return;
    this.errorGrupo = '';
    this.successGrupo = '';
    const dto: ConGru = {
      idConcierto: this.conciertoGrupoAbierto,
      idGrupo: this.grupoSeleccionado.idGrupo!,
    };
    this.conciertoService.asociarGrupo(dto).subscribe({
      next: () => {
        this.successGrupo = 'Grupo asociado';
        this.grupoSeleccionado = null;
        this.cargarGruposAsociados(this.conciertoGrupoAbierto!);
      },
      error: (err) => {
        this.errorGrupo = err.status === 406 ? 'Ese grupo ya está asociado' : 'Error al asociar';
      },
    });
  }

  desasociarGrupo(idGrupo: number): void {
    if (!this.conciertoGrupoAbierto) return;
    this.conciertoService.desasociarGrupo(this.conciertoGrupoAbierto, idGrupo).subscribe({
      next: () => {
        this.successGrupo = 'Grupo desasociado';
        this.cargarGruposAsociados(this.conciertoGrupoAbierto!);
      },
      error: () => (this.errorGrupo = 'Error al desasociar'),
    });
  }

  get gruposDisponibles(): Grupo[] {
    const ids = new Set(this.gruposAsociados.map((g) => g.idGrupo));
    return this.todosLosGrupos.filter((g) => !ids.has(g.idGrupo!));
  }

  // ── Panel de organizadores ────────────────────────────

  togglePanelOrganizadores(c: ConciertoDTO): void {
    const id = c.idConcierto!;
    if (this.conciertoOrgAbierto === id) {
      this.cerrarPanelOrganizadores();
      return;
    }
    this.cerrarTodosLosPaneles();
    this.conciertoOrgAbierto = id;
    this.nombreConciertoPanel = c.nombreConcierto;
    this.errorOrg = '';
    this.successOrg = '';
    this.cargarOrganizadoresAsociados(id);
  }

  cerrarPanelOrganizadores(): void {
    this.conciertoOrgAbierto = null;
    this.organizadoresAsociados = [];
    this.organizadorSeleccionado = null;
    this.errorOrg = '';
    this.successOrg = '';
  }

  cargarOrganizadoresAsociados(idConcierto: number): void {
    this.conciertoService.getOrganizadoresByConcierto(idConcierto).subscribe({
      next: (data) => (this.organizadoresAsociados = data ?? []),
      error: () => (this.organizadoresAsociados = []),
    });
  }

  asociarOrganizador(): void {
    if (!this.organizadorSeleccionado || !this.conciertoOrgAbierto) return;
    this.errorOrg = '';
    this.successOrg = '';
    const dto: ConOrg = {
      idConcierto: this.conciertoOrgAbierto,
      nombreOrganizador: this.organizadorSeleccionado.nombreOrganizador,
    };
    this.conciertoService.asociarOrganizador(dto).subscribe({
      next: () => {
        this.successOrg = 'Organizador asociado';
        this.organizadorSeleccionado = null;
        this.cargarOrganizadoresAsociados(this.conciertoOrgAbierto!);
      },
      error: (err) => {
        this.errorOrg =
          err.status === 406 ? 'Ese organizador ya está asociado' : 'Error al asociar';
      },
    });
  }

  desasociarOrganizador(nombreOrganizador: string): void {
    if (!this.conciertoOrgAbierto) return;
    this.conciertoService
      .desasociarOrganizador(this.conciertoOrgAbierto, nombreOrganizador)
      .subscribe({
        next: () => {
          this.successOrg = 'Organizador desasociado';
          this.cargarOrganizadoresAsociados(this.conciertoOrgAbierto!);
        },
        error: () => (this.errorOrg = 'Error al desasociar'),
      });
  }

  get organizadoresDisponibles(): Organizador[] {
    const nombres = new Set(this.organizadoresAsociados.map((o) => o.nombreOrganizador));
    return this.todosLosOrganizadores.filter((o) => !nombres.has(o.nombreOrganizador));
  }

  // ── Panel de zonas ────────────────────────────────────

  togglePanelZonas(c: ConciertoDTO): void {
    const id = c.idConcierto!;
    if (this.conciertoZonaAbierto === id) {
      this.cerrarPanelZonas();
      return;
    }
    this.cerrarTodosLosPaneles();
    this.conciertoZonaAbierto = id;
    this.nombreConciertoPanel = c.nombreConcierto;
    this.zonaSeleccionada = null;
    this.precioNuevo = 0;
    this.editandoZona = null;
    this.errorZona = '';
    this.successZona = '';
    this.cargarZonasConcierto(id);
    // Cargar zonas de la sede de este concierto
    this.cargarZonasDeSede(c.nombreSede);
  }

  cerrarPanelZonas(): void {
    this.conciertoZonaAbierto = null;
    this.zonasConcierto = [];
    this.todasLasZonas = [];
    this.zonaSeleccionada = null;
    this.precioNuevo = 0;
    this.editandoZona = null;
    this.errorZona = '';
    this.successZona = '';
  }

  cargarZonasConcierto(idConcierto: number): void {
    this.conciertoService.getZonasByConcierto(idConcierto).subscribe({
      next: (data) => (this.zonasConcierto = data ?? []),
      error: () => (this.zonasConcierto = []),
    });
  }

  cargarZonasDeSede(nombreSede: string): void {
    this.sedeService
      .getAllZonas()
      .pipe(catchError(() => of([])))
      .subscribe({
        next: (data) => {
          this.todasLasZonas = (data ?? []).filter((z) => z.nombreSede === nombreSede);
          this.cdr.detectChanges(); // fix NG0100
        },
      });
  }

  nombreZona(idZona: number): string {
    return this.todasLasZonas.find((z) => z.idZona === idZona)?.nombreZona ?? `Zona #${idZona}`;
  }

  agregarZonaConcierto(): void {
    if (!this.zonaSeleccionada || !this.conciertoZonaAbierto) return;
    this.errorZona = '';
    this.successZona = '';

    const dto: ZonaConcierto = {
      idZona: this.zonaSeleccionada.idZona!,
      idConcierto: this.conciertoZonaAbierto,
      precio: this.precioNuevo,
    };

    this.conciertoService.crearZonaConcierto(dto).subscribe({
      next: () => {
        this.successZona = 'Zona agregada correctamente';
        this.zonaSeleccionada = null;
        this.precioNuevo = 0;
        this.cargarZonasConcierto(this.conciertoZonaAbierto!);
      },
      error: (err) => {
        if (err.status === 406) this.errorZona = 'Esa zona ya está configurada para este concierto';
        else if (err.status === 400) this.errorZona = 'La zona no tiene lugares configurados aún';
        else this.errorZona = 'Error al agregar la zona';
      },
    });
  }

  iniciarEdicionZona(zc: ZonaConcierto): void {
    this.editandoZona = zc.idPrecio!;
    this.precioEditando = zc.precio;
    this.cantidadEditando = zc.cantidadDisponible ?? 0;
  }

  guardarEdicionZona(zc: ZonaConcierto): void {
    if (!this.conciertoZonaAbierto) return;
    this.errorZona = '';
    this.successZona = '';

    const dto: ZonaConcierto = {
      ...zc,
      precio: this.precioEditando,
      cantidadDisponible: this.cantidadEditando,
    };

    this.conciertoService.actualizarZonaConcierto(zc.idPrecio!, dto).subscribe({
      next: () => {
        this.successZona = 'Zona actualizada correctamente';
        this.editandoZona = null;
        this.cargarZonasConcierto(this.conciertoZonaAbierto!);
      },
      error: () => (this.errorZona = 'Error al actualizar la zona'),
    });
  }

  cancelarEdicionZona(): void {
    this.editandoZona = null;
    this.precioEditando = 0;
    this.cantidadEditando = 0;
  }

  eliminarZonaConcierto(idPrecio: number): void {
    if (!this.conciertoZonaAbierto) return;
    this.errorZona = '';
    this.successZona = '';

    this.conciertoService.eliminarZonaConcierto(idPrecio).subscribe({
      next: () => {
        this.successZona = 'Zona eliminada';
        this.cargarZonasConcierto(this.conciertoZonaAbierto!);
      },
      error: () => (this.errorZona = 'Error al eliminar la zona'),
    });
  }

  get zonasDisponibles(): Zona[] {
    const configuradas = new Set(this.zonasConcierto.map((zc) => zc.idZona));
    return this.todasLasZonas.filter((z) => !configuradas.has(z.idZona!));
>>>>>>> Stashed changes
  }

  formatPrecio(valor: number): string {
    return new Intl.NumberFormat('es-CO', {
      style: 'currency',
      currency: 'COP',
      maximumFractionDigits: 0,
    }).format(valor);
  }

  private mapearAFront(dto: ConciertoDTO): Concierto {
    return {
      id: dto.idConcierto,
      nombre: dto.nombreConcierto,
      artista: 'Artista Ejemplo', // Vincula el artista si tu DTO lo incluye
      nombreSede: dto.nombreSede,
      fecha: dto.fechaConcierto ? new Date(dto.fechaConcierto) : null,
      edadMinima: dto.edadMinima,
      descripcion: dto.descripcionConcierto,
      estado: dto.estadoConcierto || 'Programado',
      zonas: [], // Si el GET trae zonas, las mapeas aquí de forma similar
      expandida: false
    };
  }

  private mapearABack(concierto: Concierto): ConciertoDTO {
    // Aseguramos que la fecha lleve la hora requerida por el LocalDateTime de Java
    let fechaFormateada = '';
    if (concierto.fecha instanceof Date) {
      // .toISOString() por defecto devuelve algo como "2026-05-19T00:00:00.000Z"
      fechaFormateada = concierto.fecha.toISOString();
    }

    return {
      idConcierto: concierto.id || 0,
      nombreConcierto: concierto.nombre,
      descripcionConcierto: concierto.descripcion,
      imagenConcierto: '',
      edadMinima: concierto.edadMinima,
      recomendacion: '',
<<<<<<< Updated upstream
      fechaConcierto: fechaFormateada, // <--- Enviamos el ISO completo con la 'T' y la hora
      estadoConcierto: concierto.estado,
      idTour: 1,
      nombreSede: concierto.nombreSede
=======
      fechaConcierto: '',
      estadoConcierto: 'Programado',
      nombreSede: '',
>>>>>>> Stashed changes
    };
  }
}
