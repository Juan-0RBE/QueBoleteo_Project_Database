  // src/app/core/services/artista.service.ts
  import { Injectable } from '@angular/core';
  import { HttpClient } from '@angular/common/http';
  import { Observable } from 'rxjs';
  import { environment } from '../../../environments/environment';

  import { catchError } from 'rxjs/operators';
  import { of } from 'rxjs';

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

  // Interfaz para la conexión con género

  export interface ArtGen {
    idArtista: number;
    idGenero: number;
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


    // Endpoints de ART_GEN

  // GET /artgen/artista/{id}
    getGenerosByArtista(idArtista: number): Observable<ArtGen[]> {
      return this.http.get<ArtGen[]>(
        `${environment.apiUrl}/artgen/artista/${idArtista}`
      ).pipe(
        catchError(() => of([]))
      );
    }

  // POST /artgen/crear
    asociarGenero(dto: ArtGen): Observable<string> {
      return this.http.post(
        `${environment.apiUrl}/artgen/crear`, dto, { responseType: 'text' }
      );
    }

  // DELETE /artgen/{idArtista}/{idGenero}
    desasociarGenero(idArtista: number, idGenero: number): Observable<string> {
      return this.http.delete(
        `${environment.apiUrl}/artgen/${idArtista}/${idGenero}`, { responseType: 'text' }
      );
    }

  }


