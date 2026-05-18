import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';

import { catchError } from 'rxjs/operators';
import { of } from 'rxjs';

export interface Genero {
  idGenero?: number;
  nombreGenero: string;
}


@Injectable({ providedIn: 'root' })
export class GeneroService {
  private url = `${environment.apiUrl}/genero`;

  constructor(private http: HttpClient) {}

  // GET /genero/all
  getAll(): Observable<Genero[]> {
    return this.http.get<Genero[]>(`${this.url}/all`).pipe(
      catchError(() => of([]))
    );
  }

  // GET /genero/{id}
  getById(id: number): Observable<Genero> {
    return this.http.get<Genero>(`${this.url}/${id}`);
  }

  // POST /genero/crear
  create(genero: Genero): Observable<string> {
    return this.http.post(`${this.url}/crear`, genero, { responseType: 'text' });
  }

  // PUT /genero/update/{id}
  update(id: number, genero: Genero): Observable<string> {
    return this.http.put(`${this.url}/update/${id}`, genero, { responseType: 'text' });
  }

  // DELETE /genero/delete/{id}
  delete(id: number): Observable<string> {
    return this.http.delete(`${this.url}/delete/${id}`, { responseType: 'text' });
  }

  // GET /genero/count
  count(): Observable<number> {
    return this.http.get<number>(`${this.url}/count`);
  }
}
