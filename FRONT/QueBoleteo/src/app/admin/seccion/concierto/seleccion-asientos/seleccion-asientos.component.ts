import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { ButtonModule } from 'primeng/button';
import { TagModule } from 'primeng/tag';
import { ToastModule } from 'primeng/toast';
import { MessageService } from 'primeng/api';
import { VentaService, LugarDisponible, CompraRequestDto } from '../../../../core/services/venta.service';
import { AuthService } from '../../../../core/services/auth.service';

// Zona y contexto que llegan desde detalle-concierto vía router state
export interface ZonaCompraContext {
  idZonaConcierto: number; // idPrecio del backend — el FK real
  nombreZona: string;
  precio: number;
  cantidadMaxima: number;   // cantidadDisponible de la zona
}

interface AsientoUI {
  idLugar: number;
  fila: string;
  numeroAsiento: number;
  seleccionado: boolean;
}

@Component({
  selector: 'app-seleccion-asientos',
  standalone: true,
  imports: [CommonModule, ButtonModule, TagModule, ToastModule],
  providers: [MessageService],
  templateUrl: './seleccion-asientos.component.html',
  styleUrls: ['./seleccion-asientos.component.css']
})
export class SeleccionAsientosComponent implements OnInit {

  context!: ZonaCompraContext;
  asientos: AsientoUI[] = [];
  filas: string[] = [];
  loading = true;
  procesando = false;

  constructor(
    private router: Router,
    private ventaService: VentaService,
    private authService: AuthService,
    private cdr: ChangeDetectorRef,
    private messageService: MessageService
  ) {
    // El contexto llega por state del router desde detalle-concierto
    const nav = this.router.getCurrentNavigation();
    const state = nav?.extras?.state as { context: ZonaCompraContext } | undefined;
    if (state?.context) {
      this.context = state.context;
    }
  }

  ngOnInit(): void {
    if (!this.context) {
      // Si se accede directo sin state, volver
      this.router.navigate(['/principal/paginaprincipal']);
      return;
    }
    this.cargarAsientos();
  }

  cargarAsientos(): void {
    this.loading = true;
    this.ventaService.getLugaresDisponibles(this.context.idZonaConcierto).subscribe({
      next: (lugares: LugarDisponible[]) => {
        this.asientos = lugares.map(l => ({
          idLugar: l.idLugar,
          fila: l.fila,
          numeroAsiento: l.numeroAsiento,
          seleccionado: false
        }));
        // Extraemos las filas únicas ordenadas para renderizar el mapa
        this.filas = [...new Set(this.asientos.map(a => a.fila))].sort();
        this.loading = false;
        this.cdr.detectChanges();
      },
      error: () => {
        this.messageService.add({
          severity: 'error',
          summary: 'Error',
          detail: 'No se pudieron cargar los asientos disponibles.'
        });
        this.loading = false;
      }
    });
  }

  getAsientosDeFila(fila: string): AsientoUI[] {
    return this.asientos
      .filter(a => a.fila === fila)
      .sort((a, b) => a.numeroAsiento - b.numeroAsiento);
  }

  toggleAsiento(asiento: AsientoUI): void {
    const seleccionados = this.asientosSeleccionados;

    if (asiento.seleccionado) {
      asiento.seleccionado = false;
    } else {
      // Respetar la cantidad máxima permitida
      if (seleccionados.length >= this.context.cantidadMaxima) {
        this.messageService.add({
          severity: 'warn',
          summary: 'Límite alcanzado',
          detail: `Solo puedes seleccionar hasta ${this.context.cantidadMaxima} asientos en esta zona.`
        });
        return;
      }
      asiento.seleccionado = true;
    }
  }

  get asientosSeleccionados(): AsientoUI[] {
    return this.asientos.filter(a => a.seleccionado);
  }

  get totalPrecio(): number {
    return this.asientosSeleccionados.length * this.context.precio;
  }

  formatPrecio(valor: number): string {
    return new Intl.NumberFormat('es-CO', {
      style: 'currency', currency: 'COP', maximumFractionDigits: 0
    }).format(valor);
  }

  confirmarCompra(): void {
    const seleccionados = this.asientosSeleccionados;

    if (seleccionados.length === 0) {
      this.messageService.add({
        severity: 'warn',
        summary: 'Sin asientos',
        detail: 'Selecciona al menos un asiento para continuar.'
      });
      return;
    }

    const correo = this.authService.getCorreo();
    if (!correo) {
      this.messageService.add({
        severity: 'error',
        summary: 'Sesión inválida',
        detail: 'No se pudo obtener tu información de sesión. Por favor vuelve a iniciar sesión.'
      });
      return;
    }

    const dto: CompraRequestDto = {
      correoUsuario: correo,
      idZonaConcierto: this.context.idZonaConcierto,
      cantidad: seleccionados.length,
      idsLugaresElegidos: seleccionados.map(a => a.idLugar)
    };

    this.procesando = true;
    this.ventaService.comprar(dto).subscribe({
      next: (response) => {
        this.procesando = false;
        this.router.navigate(['/compra/confirmacion'], {
          state: { respuesta: response, zona: this.context }
        });
      },
      error: (err) => {
        this.procesando = false;
        const mensaje = typeof err.error === 'string'
          ? err.error
          : 'Ocurrió un error al procesar la compra.';
        this.messageService.add({
          severity: 'error',
          summary: 'Error en la compra',
          detail: mensaje
        });
      }
    });
  }

  volver(): void {
    this.router.navigate(['/principal/paginaprincipal']);
  }
}
