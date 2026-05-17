import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { Artista } from './artista.service';

// Interfaz — espeja exactamente el grupoDTO de Java
export interface Grupo {
  idGrupo?: number;        // opcional porque al crear no existe aún
  nombreGrupo: string;
  descripcionGrupo: string;
  imagenGrupo: string;
  paisOrigenGrupo: string;
  tiempoDuracion: number;
  lenguajeGrupo: string;
}

@Injectable({ providedIn: 'root' })
export class GrupoService {

  private url = `${environment.apiUrl}/grupo`;

  constructor(private http: HttpClient) {}

  // GET /grupo/all
  getAll(): Observable<Grupo[]> {
    return this.http.get<Grupo[]>(`${this.url}/all`);
  }
  // GET /grupo/{id}
  getById(id: number): Observable<Grupo> {
    return this.http.get<Grupo>(`${this.url}/${id}`);
  }

  // POST /grupo/crear
  create(grupo: Grupo): Observable<string> {
    return this.http.post(`${this.url}/crear`, grupo, { responseType: 'text' });
  }

  // PUT /grupo/update/{id}
  update(id: number, grupo: Grupo): Observable<string> {
    return this.http.put(`${this.url}/update/${id}`, grupo, { responseType: 'text' });
  }

  // DELETE /grupo/delete/{id}
  delete(id: number): Observable<string> {
    return this.http.delete(`${this.url}/delete/${id}`, { responseType: 'text' });
  }

  // GET /grupo/count
  count(): Observable<number> {
    return this.http.get<number>(`${this.url}/count`);
  }
}
