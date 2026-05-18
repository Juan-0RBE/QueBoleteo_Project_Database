import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';

export interface CrearVentaRequest {
  idVenta: number;
  valorTotal: number;
  fechaVenta: string;
  correoUsuario: string;
}

export interface CrearVentaResponse {
  idVenta: number;
  valorTotal: number;
  fechaVenta: string;
  correoUsuario: string;
}

export interface ComprarBoletaRequest {
  correoUsuario: string;
  idZonaConcierto: number;
  cantidad: number;
  idLugaresElegidos: number[];
}

@Injectable({ providedIn: 'root' })
export class VentaService {
  private url = `${environment.apiUrl}/venta`;

  constructor(private http: HttpClient) {}

  crearVenta(correoUsuario: string, valorTotal: number): Observable<CrearVentaResponse> {
    const body: CrearVentaRequest = {
      idVenta: 0,
      valorTotal,
      fechaVenta: new Date().toISOString(),
      correoUsuario,
    };
    return this.http.post<CrearVentaResponse>(`${this.url}/crear`, body);
  }

  comprarBoletas(payload: ComprarBoletaRequest): Observable<any> {
    return this.http.post(`${this.url}/comprar`, payload);
  }
}
