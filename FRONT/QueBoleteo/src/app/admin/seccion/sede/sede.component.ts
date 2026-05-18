import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ButtonModule } from 'primeng/button';
import { InputTextModule } from 'primeng/inputtext';
import { TableModule } from 'primeng/table';
import { CheckboxModule } from 'primeng/checkbox';
import { SelectModule } from 'primeng/select';
import { InputNumberModule } from 'primeng/inputnumber';
import { SedeService, Sede, Zona, ConfiguracionLugar } from '../../../core/services/sede.service';




interface GeneradorAsientos {
  filaInicio: string;
  filaFin: string;
  asientoInicio: number;
  asientoFin: number;
}

interface LugarVista {
  idLugar: number;
  fila: string;
  numeroAsiento: number;
  idZona: number;
}

@Component({
  selector: 'app-admin-sedes',
  standalone: true,
  imports: [
    CommonModule, FormsModule,
    ButtonModule, InputTextModule,
    TableModule, CheckboxModule,
    SelectModule, InputNumberModule,
  ],
  templateUrl: './sede.component.html',
  styleUrls: ['./sede.component.css']
})
export class AdminSedeComponent implements OnInit {

  // ── Estado sede ──
  mostrarFormularioSede = false;
  modoEdicionSede = false;
  sedeEditandoNombre: string | null = null;
  sedes: Sede[] = [];
  formularioSede: Sede = this.sedeVacia();

  // ── Estado zona ──
  // sedeExpandida guarda el nombreSede de la fila expandida
  sedeExpandida: string | null = null;
  // zonasPorSede guarda las zonas ya cargadas de la sede expandida
  zonasPorSede: Zona[] = [];
  sedeConFormularioZona: string | null = null;
  modoEdicionZona = false;
  zonaEditandoId: number | null = null;
  formularioZona: Zona = this.zonaVacia();

  // ── Estado lugares ──
  zonaActivaLugares: Zona | null = null;
  mostrarGenerador = false;
  formLugares: ConfiguracionLugar = {
    filas: 0,
    asientosPorFila: 0,
    capacidadGeneral: 0
  };

  conteoZonasPorSede: Map<string, number> = new Map();


  // ── Mensajes ──
  errorMsg = '';
  successMsg = '';

  tiposZona = [
    { label: 'Zona general (sin asientos)', value: false },
    { label: 'Asientos numerados',          value: true  },
  ];

  constructor(private sedeService: SedeService,
  private cdr: ChangeDetectorRef  // ← agrega
) {}

  ngOnInit(): void {
    this.cargarSedes();
  }

  // ── CRUD SEDE ──────────────────────────────────

  cargarSedes(): void {
    this.sedeService.getAllSedes().subscribe({
      next: (sedes) => {
        this.sedes = sedes;
        this.cdr.detectChanges();
        // Carga el conteo de zonas para todas las sedes
        this.sedeService.getAllZonas().subscribe({
          next: (zonas) => {
            this.conteoZonasPorSede = new Map();
            zonas.forEach(z => {
              const actual = this.conteoZonasPorSede.get(z.nombreSede) ?? 0;
              this.conteoZonasPorSede.set(z.nombreSede, actual + 1);
            });
          }
        });
      },
      error: () => this.errorMsg = 'Error al cargar las sedes'
    });
  }

  abrirCrearSede(): void {
    this.formularioSede = this.sedeVacia();
    this.modoEdicionSede = false;
    this.sedeEditandoNombre = null;
    this.mostrarFormularioSede = true;
  }

  abrirEditarSede(sede: Sede): void {
    this.formularioSede = { ...sede };
    this.modoEdicionSede = true;
    this.sedeEditandoNombre = sede.nombreSede;
    this.mostrarFormularioSede = true;
  }

  guardarSede(): void {
    if (!this.formularioSede.nombreSede) return;
    this.errorMsg = '';
    this.successMsg = '';

    if (this.modoEdicionSede && this.sedeEditandoNombre) {
      this.sedeService.updateSede(this.sedeEditandoNombre, this.formularioSede).subscribe({
        next: () => {
          this.successMsg = 'Sede actualizada correctamente';
          this.cancelarSede();
          this.cargarSedes();
          this.cdr.detectChanges();
        },
        error: () => this.errorMsg = 'Error al actualizar la sede'
      });
    } else {
      this.sedeService.createSede(this.formularioSede).subscribe({
        next: () => {
          this.successMsg = 'Sede creada correctamente';
          this.cancelarSede();
          this.cargarSedes();
        },
        error: () => this.errorMsg = 'Error al crear la sede'
      });
    }
  }

  eliminarSede(nombreSede: string): void {
    this.sedeService.deleteSede(nombreSede).subscribe({
      next: () => {
        this.successMsg = 'Sede eliminada correctamente';
        if (this.sedeExpandida === nombreSede) this.sedeExpandida = null;
        this.cargarSedes();
      },
      error: () => this.errorMsg = 'Error al eliminar la sede'
    });
  }

  cancelarSede(): void {
    this.mostrarFormularioSede = false;
    this.formularioSede = this.sedeVacia();
    this.modoEdicionSede = false;
    this.sedeEditandoNombre = null;
  }

  // Expande o colapsa la fila de una sede
  toggleExpandir(sede: Sede): void {
    if (this.sedeExpandida === sede.nombreSede) {
      this.sedeExpandida = null;
      this.zonasPorSede = [];
      this.cancelarZona();
      this.cerrarLugares();
    } else {
      this.sedeExpandida = sede.nombreSede;
      this.cargarZonasDeSede(sede.nombreSede);
      this.cerrarLugares();
    }
  }

  // ── CRUD ZONA ──────────────────────────────────

  cargarZonasDeSede(nombreSede: string): void {
    this.sedeService.getAllZonas().subscribe({
      next: (data) => {
        this.zonasPorSede = data.filter(z => z.nombreSede === nombreSede);
        this.cdr.detectChanges();
      },
      error: () => this.errorMsg = 'Error al cargar las zonas'
    });
  }

  abrirCrearZona(nombreSede: string): void {
    this.formularioZona = this.zonaVacia();
    this.formularioZona.nombreSede = nombreSede;
    this.modoEdicionZona = false;
    this.zonaEditandoId = null;
    this.sedeConFormularioZona = nombreSede;
    this.cerrarLugares();
  }

  abrirEditarZona(nombreSede: string, zona: Zona): void {
    this.formularioZona = { ...zona };
    this.modoEdicionZona = true;
    this.zonaEditandoId = zona.idZona ?? null;
    this.sedeConFormularioZona = nombreSede;
    this.cerrarLugares();
  }

  guardarZona(): void {
    if (!this.formularioZona.nombreZona) return;
    this.errorMsg = '';
    this.successMsg = '';

    if (this.modoEdicionZona && this.zonaEditandoId !== null) {
      this.sedeService.updateZona(this.zonaEditandoId, this.formularioZona).subscribe({
        next: () => {
          this.successMsg = 'Zona actualizada correctamente';
          this.cancelarZona();
          this.cargarZonasDeSede(this.sedeExpandida!);
        },
        error: () => this.errorMsg = 'Error al actualizar la zona'
      });
    } else {
      this.sedeService.createZona(this.formularioZona).subscribe({
        next: () => {
          this.successMsg = 'Zona creada correctamente';
          this.cancelarZona();
          this.cargarZonasDeSede(this.sedeExpandida!);
        },
        error: () => this.errorMsg = 'Error al crear la zona'
      });
    }
  }

  eliminarZona(idZona: number): void {
    this.sedeService.deleteZona(idZona).subscribe({
      next: () => {
        this.successMsg = 'Zona eliminada correctamente';
        this.cargarZonasDeSede(this.sedeExpandida!);
      },
      error: () => this.errorMsg = 'Error al eliminar la zona'
    });
  }

  cancelarZona(): void {
    this.sedeConFormularioZona = null;
    this.formularioZona = this.zonaVacia();
    this.modoEdicionZona = false;
    this.zonaEditandoId = null;
  }

  // ── LUGARES ────────────────────────────────────

/*  abrirLugares(zona: Zona): void {
    this.zonaActivaLugares = zona;
    this.mostrarGenerador = false;
    this.formLugares = {};
    this.cancelarZona();
  }*/

  cerrarLugares(): void {
    this.zonaActivaLugares = null;
    this.mostrarGenerador = false;
    this.formLugares = { filas: 0, asientosPorFila: 0, capacidadGeneral: 0 };
  }

  // Calcula preview del total de asientos a generar
  get totalLugaresGenerador(): number {
    if (this.zonaActivaLugares?.tieneAsiento) {
      const f = this.formLugares.filas ?? 0;
      const a = this.formLugares.asientosPorFila ?? 0;
      return f > 0 && a > 0 ? f * a : 0;
    }
    return this.formLugares.capacidadGeneral ?? 0;
  }
/*  configurarLugares(): void {
    console.log('zonaActiva:', this.zonaActivaLugares);
    console.log('formLugares:', this.formLugares);  // ← agrega esto
    if (!this.zonaActivaLugares?.idZona) return;
    this.errorMsg = '';
    this.successMsg = '';

    this.sedeService.configurarLugares(this.zonaActivaLugares.idZona, this.formLugares).subscribe({
      next: () => {
        this.successMsg = 'Lugares configurados correctamente';
        this.cerrarLugares();
      },
      error: () => this.errorMsg = 'Error al configurar los lugares'
    });
  }*/

  zonasConfiguradas: Set<number> = new Set();
/*  configurarLugares(): void {
    if (!this.zonaActivaLugares?.idZona) return;
    this.errorMsg = '';
    this.successMsg = '';

    this.sedeService.configurarLugares(this.zonaActivaLugares.idZona, this.formLugares).subscribe({
      next: () => {
        this.successMsg = 'Lugares configurados correctamente';
        this.zonasConfiguradas.add(this.zonaActivaLugares!.idZona!); // ← agrega esto
        this.mostrarGenerador = false;
      },
      error: (err) => {
        // Si el backend devuelve 409 es porque ya está configurada
        this.errorMsg = err.status === 409
          ? 'Esta zona ya tiene lugares configurados'
          : 'Error al configurar los lugares';
      }
    });
  }*/

  configurarLugares(): void {
    if (!this.zonaActivaLugares?.idZona) return;
    this.errorMsg = '';
    this.successMsg = '';

    this.sedeService.configurarLugares(this.zonaActivaLugares.idZona, this.formLugares).subscribe({
      next: () => {
        this.successMsg = 'Lugares configurados correctamente';
        this.mostrarGenerador = false;
        // Recarga el mapa visual
        this.sedeService.getAllLugares().subscribe({
          next: (data) => {
            const filtrados = data.filter((l: any) => l.idZona === this.zonaActivaLugares?.idZona);
            this.lugaresDeZonaActiva = filtrados.length;
            this.lugaresVista = filtrados;
          },
          error: () => {
            this.lugaresDeZonaActiva = 0;
            this.lugaresVista = [];
          }
        });
      },
      error: (err) => {
        this.errorMsg = err.status === 409
          ? 'Esta zona ya tiene lugares configurados'
          : 'Error al configurar los lugares';
      }
    });
  }

  // ── OBJETOS VACÍOS ─────────────────────────────

  sedeVacia(): Sede {
    return {
      nombreSede: '', calle: '', carrera: '',
      ciudad: '', tieneAccesibilidad: false,
      imagenSede: '', imagenSeccion: ''
    };
  }

  zonaVacia(): Zona {
    return { nombreZona: '', tieneAsiento: false, nombreSede: '' };
  }

  lugaresDeZonaActiva: number = 0;
  lugaresVista: LugarVista[] = [];  // ← agrega esta
  cargandoLugares: boolean = false;
  abrirLugares(zona: Zona): void {
    this.zonaActivaLugares = zona;
    this.mostrarGenerador = false;
    this.formLugares = { filas: 0, asientosPorFila: 0, capacidadGeneral: 0 };
    this.lugaresDeZonaActiva = 0;
    this.lugaresVista = [];
    this.cargandoLugares = true;
    this.cancelarZona();

    this.sedeService.getAllLugares().subscribe({
      next: (data) => {
        console.log('Lugares recibidos:', data);
        console.log('idZona buscado:', zona.idZona);
        const filtrados = data.filter((l: any) => l.idZona === zona.idZona);
        console.log('Lugares filtrados:', filtrados);
        this.lugaresDeZonaActiva = filtrados.length;
        this.lugaresVista = filtrados;
        this.cargandoLugares = false;
        this.cdr.detectChanges();  // ← agrega
      },
      error: (err) => {
        console.log('Error:', err.status);
        this.lugaresDeZonaActiva = 0;
        this.lugaresVista = [];
        this.cargandoLugares = false;
        this.cdr.detectChanges();  // ← agrega
      }
    });
  }

  getLugaresPorFila(): { fila: string; asientos: LugarVista[] }[] {
    const mapa = new Map<string, LugarVista[]>();
    for (const l of this.lugaresVista) {
      const fila = l.fila ?? 'General';
      if (!mapa.has(fila)) mapa.set(fila, []);
      mapa.get(fila)!.push(l);
    }
    return Array.from(mapa.entries())
      .sort(([a], [b]) => a.localeCompare(b))
      .map(([fila, asientos]) => ({
        fila,
        asientos: asientos.sort((a, b) => a.numeroAsiento - b.numeroAsiento)
      }));
  }
}
