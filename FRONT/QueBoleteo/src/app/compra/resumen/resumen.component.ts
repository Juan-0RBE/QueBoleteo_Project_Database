import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterModule } from '@angular/router';
import { ButtonModule } from 'primeng/button';
import { FormsModule } from '@angular/forms';
import { concatMap } from 'rxjs/operators';
import { from } from 'rxjs';
import { VentaService } from '../../core/services/venta.service';
import { AuthService } from '../../core/services/auth.service';

@Component({
  selector: 'app-resumen',
  standalone: true,
  imports: [CommonModule, RouterModule, FormsModule, ButtonModule],
  templateUrl: './resumen.component.html',
  styleUrls: ['./resumen.component.css']
})
export class ResumenComponent implements OnInit {

  concierto: any = null;
  items: any[] = [];
  total = 0;

  procesando = false;
  errorMensaje = '';

  constructor(
    private router: Router,
    private ventaService: VentaService,
    private authService: AuthService,
  ) {}

  ngOnInit(): void {
    const state = this.router.getCurrentNavigation()?.extras?.state || history.state;

    if (state?.concierto) {
      this.concierto = state.concierto;
      this.items     = state.items;
      this.total     = state.total;
    } else {
      this.router.navigate(['/principal/paginaprincipal']);
    }
  }

  get totalBoletas(): number {
    return this.items.reduce((acc: number, i: any) => acc + i.cantidad, 0);
  }

  formatPrecio(valor: number): string {
    return new Intl.NumberFormat('es-CO', {
      style: 'currency', currency: 'COP', maximumFractionDigits: 0
    }).format(valor);
  }

  confirmarCompra(): void {
    const correo = this.authService.getCorreo();

    if (!correo) {
      this.errorMensaje = 'No se pudo identificar al usuario. Por favor inicia sesión nuevamente.';
      return;
    }

    this.procesando = true;
    this.errorMensaje = '';

    // Crear la venta
    this.ventaService.crearVenta(correo, this.total).subscribe({
      next: (venta) => {
        // Comprar boleta x zona
        from(this.items).pipe(
          concatMap((item: any) =>
            this.ventaService.comprarBoletas({
              correoUsuario:    correo,
              idZonaConcierto:  item.zona.id,
              cantidad:         item.cantidad,
              // Si es numerada, envía ID, si es general, el arreglo queda vacíp
              idLugaresElegidos: item.asientos?.map((a: any) => a.id) ?? []
            })
          )
        ).subscribe({
          next: () => {},
          error: (err) => {
            console.error('Error al comprar boletas:', err);
            this.procesando = false;
            this.errorMensaje = 'Ocurrió un error al procesar las boletas. Intenta nuevamente.';
          },
          complete: () => {
            // Confirmacion
            this.procesando = false;
            this.router.navigate(['/compra/confirmacion'], {
              state: {
                concierto:    this.concierto,
                items:        this.items,
                total:        this.total,
                codigoVenta:  'QB-' + venta.idVenta
              }
            });
          }
        });
      },
      error: (err) => {
        console.error('Error al crear la venta:', err);
        this.procesando = false;
        this.errorMensaje = 'No se pudo crear la venta. Intenta nuevamente.';
      }
    });
  }

  volver(): void {
    this.router.navigate(['/concierto', this.concierto?.id || 1]);
  }
}
