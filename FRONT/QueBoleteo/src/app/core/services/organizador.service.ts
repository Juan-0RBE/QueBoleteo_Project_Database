// src/app/core/services/artista.service.ts
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';

export interface Organizador {
  nombreOrganizador: string;
  correoOrganizador: string;
  logo: string;
}

@Injectable({ providedIn: 'root' })

export class OrganizadorService {


  private url = `${environment.apiUrl}/organizador`;

  constructor(private http: HttpClient) {}

  // GET /organizador/all
  getAll(): Observable<Organizador[]> {
    return this.http.get<Organizador[]>(`${this.url}/all`);
  }

  // GET /organizador/{nombre}
  getById(nombre: string): Observable<Organizador> {
    return this.http.get<Organizador>(`${this.url}/${nombre}`);
  }

  // POST /organizador/crear
  create(organizador: Organizador): Observable<string> {
    return this.http.post(`${this.url}/crear`, organizador, { responseType: 'text' });
  }

  // PUT /organizador/update/{nombre}
  update(nombre: string, organizador: Organizador): Observable<string> {
    return this.http.put(`${this.url}/update/${nombre}`, organizador, { responseType: 'text' });
  }

  // DELETE /organizador/delete/{id}
  delete(nombre: string): Observable<string> {
    return this.http.delete(`${this.url}/delete/${nombre}`, { responseType: 'text' });
  }

  // GET /organizador/count
  count(): Observable<number> {
    return this.http.get<number>(`${this.url}/count`);
  }

}
