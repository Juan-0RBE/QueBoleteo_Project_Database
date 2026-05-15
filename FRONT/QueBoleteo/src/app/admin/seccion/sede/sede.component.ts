import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ButtonModule } from 'primeng/button';
import { InputTextModule } from 'primeng/inputtext';
import { TableModule } from 'primeng/table';
import { CheckboxModule } from 'primeng/checkbox';
import { SelectModule } from 'primeng/select';
import { InputNumberModule } from 'primeng/inputnumber';
import { SedesService, Sede, Zona, Lugar } from './sede.service';

interface GeneradorAsientos {
  filaInicio: string;
  filaFin: string;
  asientoInicio: number;
  asientoFin: number;
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
  sedes: Sede[] = [];
  formularioSede: Sede = this.sedeVacia();

  // ── Estado zona ──
  sedeConFormularioZona: number | null = null;
  modoEdicionZona = false;
  formularioZona: Zona = this.zonaVacia();

  // ── Estado lugares ──
  zonaConLugares: { sedeId: number; zonaId: number } | null = null;
  mostrarGenerador = false;
  generador: GeneradorAsientos = this.generadorVacio();

  tiposZona = [
    { label: 'Zona general (sin asientos)', value: false },
    { label: 'Asientos numerados',          value: true  },
  ];

  constructor(private sedesService: SedesService) {}

  ngOnInit(): void {
    this.sedes = this.sedesService.getSedes();
  }

  private sincronizar(): void {
    this.sedesService.setSedes([...this.sedes]);
  }

  sedeVacia(): Sede {
    return { id: 0, nombre: '', ciudad: '', direccion: '', accesibilidad: false, zonas: [], expandida: false };
  }

  zonaVacia(): Zona {
    return { id: 0, nombre: '', tieneAsientos: false, capacidad: 0, lugares: [] };
  }

  generadorVacio(): GeneradorAsientos {
    return { filaInicio: 'A', filaFin: 'E', asientoInicio: 1, asientoFin: 20 };
  }

  // ── CRUD Sede ──
  abrirCrearSede(): void {
    this.formularioSede = this.sedeVacia();
    this.modoEdicionSede = false;
    this.mostrarFormularioSede = true;
  }

  abrirEditarSede(sede: Sede): void {
    this.formularioSede = { ...sede, zonas: [...sede.zonas] };
    this.modoEdicionSede = true;
    this.mostrarFormularioSede = true;
  }

  guardarSede(): void {
    if (!this.formularioSede.nombre) return;
    if (this.modoEdicionSede) {
      const idx = this.sedes.findIndex(s => s.id === this.formularioSede.id);
      if (idx !== -1) this.sedes[idx] = { ...this.formularioSede };
    } else {
      const nuevoId = this.sedes.length ? Math.max(...this.sedes.map(s => s.id)) + 1 : 1;
      this.sedes = [...this.sedes, { ...this.formularioSede, id: nuevoId, zonas: [], expandida: false }];
    }
    this.sincronizar();
    this.cancelarSede();
  }

  eliminarSede(id: number): void {
    this.sedes = this.sedes.filter(s => s.id !== id);
    this.sincronizar();
  }

  cancelarSede(): void {
    this.mostrarFormularioSede = false;
    this.formularioSede = this.sedeVacia();
  }

  toggleExpandir(sede: Sede): void {
    sede.expandida = !sede.expandida;
    if (!sede.expandida) {
      if (this.sedeConFormularioZona === sede.id) this.cancelarZona();
      this.cerrarLugares();
    }
  }

  // ── CRUD Zona ──
  abrirCrearZona(sedeId: number): void {
    this.formularioZona = this.zonaVacia();
    this.modoEdicionZona = false;
    this.sedeConFormularioZona = sedeId;
    this.cerrarLugares();
  }

  abrirEditarZona(sedeId: number, zona: Zona): void {
    this.formularioZona = { ...zona, lugares: [...zona.lugares] };
    this.modoEdicionZona = true;
    this.sedeConFormularioZona = sedeId;
    this.cerrarLugares();
  }

  guardarZona(sede: Sede): void {
    if (!this.formularioZona.nombre) return;
    if (this.modoEdicionZona) {
      const idx = sede.zonas.findIndex((z: { id: any }) => z.id === this.formularioZona.id);
      if (idx !== -1) sede.zonas[idx] = { ...this.formularioZona };
    } else {
      const nuevoId = sede.zonas.length
        ? Math.max(...sede.zonas.map((z: { id: any }) => z.id)) + 1
        : 1;
      sede.zonas = [...sede.zonas, { ...this.formularioZona, id: nuevoId, lugares: [] }];
    }
    this.sincronizar();
    this.cancelarZona();
  }

  eliminarZona(sede: Sede, zonaId: number): void {
    sede.zonas = sede.zonas.filter((z: { id: number }) => z.id !== zonaId);
    this.sincronizar();
  }

  cancelarZona(): void {
    this.sedeConFormularioZona = null;
    this.formularioZona = this.zonaVacia();
  }

  // ── Gestión de lugares ──
  abrirLugares(sedeId: number, zonaId: number): void {
    this.zonaConLugares = { sedeId, zonaId };
    this.mostrarGenerador = false;
    this.generador = this.generadorVacio();
    this.cancelarZona(); // cierra formulario de zona si estaba abierto
  }

  cerrarLugares(): void {
    this.zonaConLugares = null;
    this.mostrarGenerador = false;
  }

  getZona(sedeId: number, zonaId: number): Zona | undefined {
    return this.sedes
      .find((s) => s.id === sedeId)
      ?.zonas.find((z: { id: number }) => z.id === zonaId);
  }

  get totalLugaresGenerador(): number {
    const g = this.generador;
    if (!g.filaInicio || !g.filaFin) return 0;
    const filas   = g.filaFin.toUpperCase().charCodeAt(0) - g.filaInicio.toUpperCase().charCodeAt(0) + 1;
    const asientos = g.asientoFin - g.asientoInicio + 1;
    return filas > 0 && asientos > 0 ? filas * asientos : 0;
  }

  generarLugares(sede: Sede, zona: Zona): void {
    const g = this.generador;
    if (!g.filaInicio || !g.filaFin || g.asientoInicio < 1 || g.asientoFin < g.asientoInicio) return;

    const nuevos = this.sedesService.generarLugares(g.filaInicio, g.filaFin, g.asientoInicio, g.asientoFin);

    // Mantiene ID global único sumando al máximo actual
    const maxId = zona.lugares.length ? Math.max(...zona.lugares.map((l: { id: any }) => l.id)) : 0;
    const conIds = nuevos.map((l: any, i: number) => ({ ...l, id: maxId + i + 1 }));

    zona.lugares = [...zona.lugares, ...conIds];
    zona.capacidad = zona.lugares.length;
    this.sincronizar();
    this.mostrarGenerador = false;
    this.generador = this.generadorVacio();
  }

  limpiarLugares(sede: Sede, zona: Zona): void {
    zona.lugares = [];
    zona.capacidad = 0;
    this.sincronizar();
  }

  // Agrupa lugares por fila para mostrarlos ordenados
  getLugaresPorFila(zona: Zona): { fila: string; asientos: Lugar[] }[] {
    const mapa = new Map<string, Lugar[]>();
    for (const l of zona.lugares) {
      if (!mapa.has(l.fila)) mapa.set(l.fila, []);
      mapa.get(l.fila)!.push(l);
    }
    return Array.from(mapa.entries())
      .sort(([a], [b]) => a.localeCompare(b))
      .map(([fila, asientos]) => ({ fila, asientos: asientos.sort((a, b) => a.numeroAsiento - b.numeroAsiento) }));
  }
}
