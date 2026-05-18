import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ButtonModule } from 'primeng/button';
import { InputTextModule } from 'primeng/inputtext';
import { SelectModule } from 'primeng/select';
import { TableModule } from 'primeng/table';
import { DatePickerModule } from 'primeng/datepicker';
import { TextareaModule } from 'primeng/textarea';
import { InputNumberModule } from 'primeng/inputnumber';
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
  // ── CRUD concierto ────────────────────────────────────
  mostrarFormulario = false;
  modoEdicion = false;
  conciertoEditandoId: number | null = null;

  conciertos: ConciertoDTO[] = [];
  formulario: ConciertoDTO = this.formularioVacio();

  errorMsg = '';
  successMsg = '';
  loading = false;

  // Datos para dropdowns del formulario
  sedes: Sede[] = [];
  tours: Tour[] = [];

  estados = [
    { label: 'Programado', value: 'Programado' },
    { label: 'Cancelado', value: 'Cancelado' },
    { label: 'Finalizado', value: 'Finalizado' },
  ];

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

  ngOnInit(): void {
    this.cargarConciertos();
    this.cargarSedes();
    this.cargarTours();
    this.cargarTodosLosArtistas();
    this.cargarTodosLosGrupos();
    this.cargarTodosLosOrganizadores();
  }

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
    });
  }

  // ── CRUD ──────────────────────────────────────────────

  abrirCrear(): void {
    this.formulario = this.formularioVacio();
    this.modoEdicion = false;
    this.conciertoEditandoId = null;
    this.mostrarFormulario = true;
    this.cerrarTodosLosPaneles();
  }

  abrirEditar(c: ConciertoDTO): void {
    this.formulario = { ...c };
    this.modoEdicion = true;
    this.conciertoEditandoId = c.idConcierto ?? null;
    this.mostrarFormulario = true;
    this.cerrarTodosLosPaneles();
  }

  guardar(): void {
    if (!this.formulario.nombreConcierto || !this.formulario.nombreSede) return;

    this.errorMsg = '';
    this.successMsg = '';
    this.loading = true;

    // Formatear fecha si viene como Date
    const body: ConciertoDTO = {
      ...this.formulario,
      ...this.formulario,
      fechaConcierto:
        (this.formulario.fechaConcierto as any) instanceof Date
          ? (this.formulario.fechaConcierto as any).toISOString()
          : this.formulario.fechaConcierto,
    };

    if (this.modoEdicion && this.conciertoEditandoId !== null) {
      this.conciertoService.update(this.conciertoEditandoId, body).subscribe({
        next: () => {
          this.successMsg = 'Concierto actualizado correctamente';
          this.cancelar();
          this.cargarConciertos();
        },
        error: () => {
          this.errorMsg = 'Error al actualizar el concierto';
          this.loading = false;
        },
      });
    } else {
      this.conciertoService.create(body).subscribe({
        next: () => {
          this.successMsg = 'Concierto creado correctamente';
          this.cancelar();
          this.cargarConciertos();
        },
        error: () => {
          this.errorMsg = 'Error al crear el concierto';
          this.loading = false;
        },
      });
    }
  }

  eliminar(id: number): void {
    this.conciertoService.delete(id).subscribe({
      next: () => {
        this.successMsg = 'Concierto eliminado';
        this.cargarConciertos();
      },
      error: () => (this.errorMsg = 'Error al eliminar el concierto'),
    });
  }

  cancelar(): void {
    this.mostrarFormulario = false;
    this.formulario = this.formularioVacio();
    this.modoEdicion = false;
    this.conciertoEditandoId = null;
    this.loading = false;
  }

  cerrarTodosLosPaneles(): void {
    this.cerrarPanelArtistas();
    this.cerrarPanelGrupos();
    this.cerrarPanelOrganizadores();
    this.cerrarPanelZonas();
  }

  formatFecha(fecha: string): string {
    if (!fecha) return '—';
    return new Date(fecha).toLocaleDateString('es-CO');
  }

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
  }

  cerrarPanelArtistas(): void {
    this.conciertoArtistaAbierto = null;
    this.artistasAsociados = [];
    this.artistaSeleccionado = null;
    this.errorArtista = '';
    this.successArtista = '';
  }

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
  }

  formatPrecio(valor: number): string {
    return new Intl.NumberFormat('es-CO', {
      style: 'currency',
      currency: 'COP',
      maximumFractionDigits: 0,
    }).format(valor);
  }

  private formularioVacio(): ConciertoDTO {
    return {
      nombreConcierto: '',
      descripcionConcierto: '',
      imagenConcierto: '',
      edadMinima: 0,
      recomendacion: '',
      fechaConcierto: '',
      estadoConcierto: 'Programado',
      nombreSede: '',
    };
  }
}
