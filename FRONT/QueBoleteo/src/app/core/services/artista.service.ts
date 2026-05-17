// src/app/core/services/artista.service.ts
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';

// Interfaz — espeja exactamente el ArtistaIndividualDTO de Java
export interface Artista {
  idArtista?: number;        // opcional porque al crear no existe aún
  nombreArtista: string;
  descripcionArtista: string;
  imagenArtista: string;
  paisOrigenArtista: string;
  edadArtista: number;
  lenguajeArtista: string;
}

@Injectable({ providedIn: 'root' })
export class ArtistaService {

  private url = `${environment.apiUrl}/artistaindividual`;

  constructor(private http: HttpClient) {}

  // GET /artistaindividual/all
  getAll(): Observable<Artista[]> {
    return this.http.get<Artista[]>(`${this.url}/all`);
  }

  // GET /artistaindividual/{id}
  getById(id: number): Observable<Artista> {
    return this.http.get<Artista>(`${this.url}/${id}`);
  }

  // POST /artistaindividual/crear
  create(artista: Artista): Observable<string> {
    return this.http.post(`${this.url}/crear`, artista, { responseType: 'text' });
  }

  // PUT /artistaindividual/update/{id}
  update(id: number, artista: Artista): Observable<string> {
    return this.http.put(`${this.url}/update/${id}`, artista, { responseType: 'text' });
  }

  // DELETE /artistaindividual/delete/{id}
  delete(id: number): Observable<string> {
    return this.http.delete(`${this.url}/delete/${id}`, { responseType: 'text' });
  }

  // GET /artistaindividual/count
  count(): Observable<number> {
    return this.http.get<number>(`${this.url}/count`);
  }
}
