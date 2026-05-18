import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';

// ── Interfaces que espejan los DTOs de Java ──────────────────────────────────

export interface CompraRequestDto {
  correoUsuario: string;
  idZonaConcierto: number;
  cantidad: number;
  idsLugaresElegidos?: number[]; // solo para zonas numeradas
}

export interface CompraResponseDto {
  idVenta: number;
  valorTotal: number;
  codigosBoletos: number[];
  idsLugares: number[];
}

export interface LugarDisponible {
  idLugar: number;
  numeroAsiento: number;
  fila: string;
}

// ── Servicio ──────────────────────────────────────────────────────────────────

@Injectable({ providedIn: 'root' })
export class VentaService {

  private url = `${environment.apiUrl}/venta`;

  constructor(private http: HttpClient) {}

  /**
   * POST /venta/comprar
   * Crea la venta y genera los boletos.
   * El backend puede responder con CompraResponseDto (201) o String de error (400).
   * Se usa responseType 'json' y se maneja el error en el componente.
   */
  comprar(dto: CompraRequestDto): Observable<CompraResponseDto> {
    return this.http.post<CompraResponseDto>(`${this.url}/comprar`, dto);
  }

  /**
   * GET /venta/lugares-disponibles/{idZonaConcierto}
   * Devuelve la lista de lugares (asientos) libres para una zona-concierto.
   * Solo se llama cuando la zona es de tipo numerado (tieneAsiento = true).
   */
  getLugaresDisponibles(idZonaConcierto: number): Observable<LugarDisponible[]> {
    return this.http.get<LugarDisponible[]>(
      `${this.url}/lugares-disponibles/${idZonaConcierto}`
    );
  }



  realizarCompra(datosCompra: any): Observable<any> {
    return this.http.post<any>(`${environment.apiUrl}/auth/comprar`, datosCompra);
  }
}
