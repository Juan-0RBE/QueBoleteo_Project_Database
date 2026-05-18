import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { environment } from '../../../environments/environment';
import { catchError } from 'rxjs/operators';



export interface Grupo {
  idGrupo?: number;        // opcional porque al crear no existe aún
  nombreGrupo: string;
  descripcionGrupo: string;
  imagenGrupo: string;
  paisOrigenGrupo: string;
  tiempoDuracion: number;
  lenguajeGrupo: string;
}

// Interfaz para la conexión con género

export interface GruGen {
  idGrupo: number;
  idGenero: number;
}

// Interfaz para la conexión con artistas
export interface GruArt {
  idGrupo: number;
  idArtista: number;
  rol: string;
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


  // Endpoints de GRU_GEN

// GET /grugen/grupo/{id}
  getGenerosByGrupo(idGrupo: number): Observable<GruGen[]> {
    return this.http.get<GruGen[]>(
      `${environment.apiUrl}/grugen/grupo/${idGrupo}`
    ).pipe(
      catchError(() => of([]))
    );
  }

// POST /grugen/crear
  asociarGenero(dto: GruGen): Observable<string> {
    return this.http.post(
      `${environment.apiUrl}/grugen/crear`, dto, { responseType: 'text' }
    );
  }

// DELETE /grugen/{idGrupo}/{idGenero}
  desasociarGenero(idGrupo: number, idGenero: number): Observable<string> {
    return this.http.delete(
      `${environment.apiUrl}/grugen/${idGrupo}/${idGenero}`, { responseType: 'text' }
    );
  }

  // Endpoints de GruArt

  // GET /gruart/grupo/{idGrupo}
  getArtistasByGrupo(idGrupo: number): Observable<GruArt[]> {
    return this.http.get<GruArt[]>(
      `${environment.apiUrl}/gruart/grupo/${idGrupo}`
    ).pipe(
      catchError(() => of([]))
    );
  }

// POST /gruart/crear
  asociarArtista(dto: GruArt): Observable<string> {
    return this.http.post(
      `${environment.apiUrl}/gruart/crear`, dto, { responseType: 'text' }
    );
  }

// PUT /gruart/update/{idGrupo}/{idArtista}
  actualizarRol(idGrupo: number, idArtista: number, dto: GruArt): Observable<string> {
    return this.http.put(
      `${environment.apiUrl}/gruart/update/${idGrupo}/${idArtista}`, dto, { responseType: 'text' }
    );
  }

// DELETE /gruart/{idGrupo}/{idArtista}
  desasociarArtista(idGrupo: number, idArtista: number): Observable<string> {
    return this.http.delete(
      `${environment.apiUrl}/gruart/${idGrupo}/${idArtista}`, { responseType: 'text' }
    );
  }

}
