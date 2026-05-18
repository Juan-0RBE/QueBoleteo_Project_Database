import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';

export interface Concierto {
  idConcierto: number;
  nombreConcierto: string;
  descripcionConcierto: string;
  imagenConcierto: string;
  edadMinima: number;
  recomendacion: string;
  fechaConcierto: string;
  estadoConcierto: string;
  idTour: number;
  nombreSede: string;
}

@Injectable({ providedIn: 'root' })
export class ConciertoService {
  private urlConcierto = `${environment.apiUrl}/concierto`;

  constructor(private http: HttpClient) {}

  // Endpoints de CONCIERTO
  getAll(): Observable<Concierto[]> {
    return this.http.get<Concierto[]>(`${this.urlConcierto}/all`);
  }

  create(concierto: Concierto): Observable<string> {
    return this.http.post(`${this.urlConcierto}/crear`, concierto, { responseType: 'text' });
  }

  update(id: number, concierto: Concierto): Observable<string> {
    return this.http.put(`${this.urlConcierto}/update/${id}`, concierto, { responseType: 'text' });
  }

  delete(id: number): Observable<string> {
    return this.http.delete(`${this.urlConcierto}/delete/${id}`, { responseType: 'text' });
  }

  count(): Observable<number> {
    return this.http.get<number>(`${this.urlConcierto}/count`);
  }
}
