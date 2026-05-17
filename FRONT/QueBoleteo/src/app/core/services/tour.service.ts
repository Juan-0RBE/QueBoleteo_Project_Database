import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';

export interface Tour {
  idTour?: number;
  nombreTour: string;
  descripcionTour: string;
  imagenTour: string;
  fechaInicial: string;
  fechaFinal: string;
}

@Injectable({ providedIn: 'root' })

export class TourService {

  private readonly TOKEN_KEY = 'qb_token';
  private readonly ROLE_KEY  = 'qb_role';
  private readonly USER_KEY  = 'qb_user';

  constructor(private http: HttpClient) {}

  private url = `${environment.apiUrl}/tour`;

  // GET /tour/all
  getAll(): Observable<Tour[]> {
    return this.http.get<Tour[]>(`${this.url}/all`);
  }

  // GET /tour/{id}
  getById(id: number): Observable<Tour> {
    return this.http.get<Tour>(`${this.url}/${id}`);
  }

  // POST /tour/crear
  create(tour: Tour): Observable<string> {
    return this.http.post(`${this.url}/crear`, tour, { responseType: 'text' });
  }

  // PUT /tour/update/{id}
  update(id: number, tour: Tour): Observable<string> {
    return this.http.put(`${this.url}/update/${id}`, tour, { responseType: 'text' });
  }

  // DELETE /tour/delete/{id}
  delete(id: number): Observable<string> {
    return this.http.delete(`${this.url}/delete/${id}`, { responseType: 'text' });
  }

  // GET /tour/count
  count(): Observable<number> {
    return this.http.get<number>(`${this.url}/count`);
  }

}
