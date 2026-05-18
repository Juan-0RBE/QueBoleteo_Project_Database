import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { environment } from '../../../environments/environment';

export interface ConciertoDTO {
  idConcierto?: number;
  nombreConcierto: string;
  descripcionConcierto: string;
  imagenConcierto: string;
  edadMinima: number;
  recomendacion: string;
  fechaConcierto: string;
  estadoConcierto: string;
  idTour?: number;
  nombreSede: string;
}

export interface ConArt {
  idConcierto: number;
  idArtista: number;
}

export interface ConGru {
  idConcierto: number;
  idGrupo: number;
}

export interface ConOrg {
  idConcierto: number;
  nombreOrganizador: string;
}

export interface ZonaConcierto {
  idPrecio?: number;
  precio: number;
  cantidadDisponible?: number;
  idZona: number;
  idConcierto: number;
}

@Injectable({ providedIn: 'root' })
export class ConciertoService {

  private url = `${environment.apiUrl}/concierto`;

  constructor(private http: HttpClient) {}

  // Endpoints CONCIERTO

  getAll(): Observable<ConciertoDTO[]> {
    return this.http.get<ConciertoDTO[]>(`${this.url}/all`);
  }

  getById(id: number): Observable<ConciertoDTO> {
    return this.http.get<ConciertoDTO>(`${this.url}/${id}`);
  }

  create(concierto: ConciertoDTO): Observable<string> {
    return this.http.post(`${this.url}/crear`, concierto, { responseType: 'text' });
  }

  update(id: number, concierto: ConciertoDTO): Observable<string> {
    return this.http.put(`${this.url}/update/${id}`, concierto, { responseType: 'text' });
  }

  delete(id: number): Observable<string> {
    return this.http.delete(`${this.url}/delete/${id}`, { responseType: 'text' });
  }

  count(): Observable<number> {
    return this.http.get<number>(`${this.url}/count`);
  }

  // Endpoints CON_ART

  getArtistasByConcierto(idConcierto: number): Observable<ConArt[]> {
    return this.http.get<ConArt[]>(
      `${environment.apiUrl}/conart/concierto/${idConcierto}`
    ).pipe(catchError(() => of([])));
  }

  asociarArtista(dto: ConArt): Observable<string> {
    return this.http.post(
      `${environment.apiUrl}/conart/crear`, dto, { responseType: 'text' }
    );
  }

  desasociarArtista(idConcierto: number, idArtista: number): Observable<string> {
    return this.http.delete(
      `${environment.apiUrl}/conart/${idConcierto}/${idArtista}`, { responseType: 'text' }
    );
  }

  // Endpoints CON_GRU

  getGruposByConcierto(idConcierto: number): Observable<ConGru[]> {
    return this.http.get<ConGru[]>(
      `${environment.apiUrl}/congru/concierto/${idConcierto}`
    ).pipe(catchError(() => of([])));
  }

  asociarGrupo(dto: ConGru): Observable<string> {
    return this.http.post(
      `${environment.apiUrl}/congru/crear`, dto, { responseType: 'text' }
    );
  }

  desasociarGrupo(idConcierto: number, idGrupo: number): Observable<string> {
    return this.http.delete(
      `${environment.apiUrl}/congru/${idConcierto}/${idGrupo}`, { responseType: 'text' }
    );
  }

  // Endpoints CON_ORG

  getOrganizadoresByConcierto(idConcierto: number): Observable<ConOrg[]> {
    return this.http.get<ConOrg[]>(
      `${environment.apiUrl}/conorg/concierto/${idConcierto}`
    ).pipe(catchError(() => of([])));
  }

  asociarOrganizador(dto: ConOrg): Observable<string> {
    return this.http.post(
      `${environment.apiUrl}/conorg/crear`, dto, { responseType: 'text' }
    );
  }

  desasociarOrganizador(idConcierto: number, nombreOrganizador: string): Observable<string> {
    return this.http.delete(
      `${environment.apiUrl}/conorg/${idConcierto}/${nombreOrganizador}`, { responseType: 'text' }
    );
  }

  //  Endpoints ZONA_CONCIERTO

// GET /zonaconcierto/concierto/{idConcierto}
  getZonasByConcierto(idConcierto: number): Observable<ZonaConcierto[]> {
    return this.http.get<ZonaConcierto[]>(
      `${environment.apiUrl}/zonaconcierto/concierto/${idConcierto}`
    ).pipe(catchError(() => of([])));
  }

// POST /zonaconcierto/crear
  crearZonaConcierto(dto: ZonaConcierto): Observable<string> {
    return this.http.post(
      `${environment.apiUrl}/zonaconcierto/crear`, dto, { responseType: 'text' }
    );
  }

// PUT /zonaconcierto/update/{id}
  actualizarZonaConcierto(id: number, dto: ZonaConcierto): Observable<string> {
    return this.http.put(
      `${environment.apiUrl}/zonaconcierto/update/${id}`, dto, { responseType: 'text' }
    );
  }

// DELETE /zonaconcierto/delete/{id}
  eliminarZonaConcierto(id: number): Observable<string> {
    return this.http.delete(
      `${environment.apiUrl}/zonaconcierto/delete/${id}`, { responseType: 'text' }
    );
  }

}
