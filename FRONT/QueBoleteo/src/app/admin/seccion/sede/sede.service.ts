import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

export interface Lugar {
  id: number;
  fila: string;
  numeroAsiento: number;
}

export interface Zona {
  id: number;
  nombre: string;
  tieneAsientos: boolean;
  capacidad: number;
  lugares: Lugar[];
}

export interface Sede {
  id: number;
  nombre: string;
  ciudad: string;
  direccion: string;
  accesibilidad: boolean;
  imagenSede?: string;
  imagenSeccion?: string;
  zonas: Zona[];
  expandida?: boolean;
}

@Injectable({ providedIn: 'root' })
export class SedesService {
  private sedesSubject = new BehaviorSubject<Sede[]>([]);
  sedes$ = this.sedesSubject.asObservable();

  getSedes(): Sede[] {
    return this.sedesSubject.getValue();
  }

  setSedes(sedes: Sede[]): void {
    this.sedesSubject.next(sedes);
  }

  getZonasDeSede(sedeId: number): Zona[] {
    const sede = this.getSedes().find(s => s.id === sedeId);
    return sede ? sede.zonas : [];
  }

  // Genera lugares automáticamente dado un rango de filas y asientos
  generarLugares(filaInicio: string, filaFin: string, asientoInicio: number, asientoFin: number): Lugar[] {
    const lugares: Lugar[] = [];
    const inicio = filaInicio.toUpperCase().charCodeAt(0);
    const fin    = filaFin.toUpperCase().charCodeAt(0);
    let id = 1;

    for (let f = inicio; f <= fin; f++) {
      const fila = String.fromCharCode(f);
      for (let a = asientoInicio; a <= asientoFin; a++) {
        lugares.push({ id: id++, fila, numeroAsiento: a });
      }
    }
    return lugares;
  }
}
