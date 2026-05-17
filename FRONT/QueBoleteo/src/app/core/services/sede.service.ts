import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';

export interface Sede {
  nombreSede: string;
  calle: string;
  carrera: string;
  ciudad: string;
  tieneAccesibilidad: boolean;
  imagenSede?: string;
  imagenSeccion?: string;
}

export interface Zona {
  idZona?: number;
  nombreZona: string;
  tieneAsiento: boolean;
  nombreSede: string;
}

export interface ConfiguracionLugar {
  filas?: number;
  asientosPorFila?: number;
  capacidadGeneral?: number;
}

@Injectable({ providedIn: 'root' })
export class SedeService {

  private urlSede = `${environment.apiUrl}/sede`;
  private urlZona = `${environment.apiUrl}/zona`;

  constructor(private http: HttpClient) {}

  // Endpoints de SEDE
  getAllSedes(): Observable<Sede[]> {
    return this.http.get<Sede[]>(`${this.urlSede}/all`);
  }

  createSede(sede: Sede): Observable<string> {
    return this.http.post(`${this.urlSede}/crear`, sede, { responseType: 'text' });
  }

  updateSede(nombre: string, sede: Sede): Observable<string> {
    return this.http.put(`${this.urlSede}/update/${nombre}`, sede, { responseType: 'text' });
  }

  deleteSede(nombre: string): Observable<string> {
    return this.http.delete(`${this.urlSede}/delete/${nombre}`, { responseType: 'text' });
  }

  // Endpoints de ZONA
  getAllZonas(): Observable<Zona[]> {
    return this.http.get<Zona[]>(`${this.urlZona}/all`);
  }

  createZona(zona: Zona): Observable<string> {
    return this.http.post(`${this.urlZona}/crear`, zona, { responseType: 'text' });
  }

  updateZona(id: number, zona: Zona): Observable<string> {
    return this.http.put(`${this.urlZona}/update/${id}`, zona, { responseType: 'text' });
  }

  deleteZona(id: number): Observable<string> {
    return this.http.delete(`${this.urlZona}/delete/${id}`, { responseType: 'text' });
  }

  // Endpoints de LUGAR
  configurarLugares(idZona: number, config: ConfiguracionLugar): Observable<string> {
    return this.http.post(
      `${this.urlZona}/configurar-lugares/${idZona}`,
      config,
      { responseType: 'text' }
    );
  }

  // GET /lugar/all y filtra por zona — o mejor, agrega endpoint en el backend
  contarLugaresPorZona(idZona: number): Observable<number> {
    return this.http.get<number>(`${environment.apiUrl}/lugar/count-by-zona/${idZona}`);
  }

  getAllLugares(): Observable<any[]> {
    return this.http.get<any[]>(`${environment.apiUrl}/lugar/all`);
  }
}
