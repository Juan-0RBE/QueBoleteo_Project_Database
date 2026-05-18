import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';

export interface ResenaDTO {
  idResena?: number;
  comentario: string;
  calificacion: number;
  correoUsuario: string;
  idConcierto: number;
}

@Injectable({
  providedIn: 'root'
})
export class ResenaService {

  private url = `${environment.apiUrl}/resena`;

  constructor(private http: HttpClient) {}

  getByConcierto(nombreConcierto: string): Observable<ResenaDTO[]> {
    return this.http.get<ResenaDTO[]>(
      `${this.url}/concierto/${encodeURIComponent(nombreConcierto)}`
    );
  }

  crear(resena: ResenaDTO): Observable<string> {
    return this.http.post(
      `${this.url}/crear`,
      resena,
      { responseType: 'text' }
    );
  }
}
