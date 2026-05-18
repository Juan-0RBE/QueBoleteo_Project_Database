import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';

export interface CompraRequest {
  correoUsuario: string;
  idZonaConcierto: number;
  cantidad: number;
  idsLugaresElegidos?: number[];
}

export interface CompraResponse {
  idVenta: number;
  valorTotal: number;
  codigosBoletos: number[];
  idsLugares: number[];
}

export interface Lugar {
  idLugar: number;
  fila: string;
  numeroAsiento: number;
}

@Injectable({
  providedIn: 'root'
})
export class CompraService {

  private url = `${environment.apiUrl}/venta`;

  constructor(private http: HttpClient) {}

  realizarCompra(data: CompraRequest): Observable<CompraResponse> {

    return this.http.post<CompraResponse>(
      `${this.url}/comprar`,
      data
    );
  }

  // Dentro de la clase CompraService, agregar:
  getLugaresDisponibles(idZonaConcierto: number): Observable<Lugar[]> {
    return this.http.get<Lugar[]>(`${this.url}/lugares-disponibles/${idZonaConcierto}`);
  }
}


