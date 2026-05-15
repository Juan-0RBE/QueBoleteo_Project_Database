import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterModule } from '@angular/router';
import { ButtonModule } from 'primeng/button';
import { InputTextModule } from 'primeng/inputtext';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-resumen',
  standalone: true,
  imports: [CommonModule, RouterModule, FormsModule, ButtonModule, InputTextModule],
  templateUrl: './resumen.component.html',
  styleUrls: ['./resumen.component.css']
})
export class ResumenComponent implements OnInit {

  concierto: any = null;
  items: any[] = [];
  total = 0;
  procesando = false;

  constructor(private router: Router) {}

  ngOnInit(): void {
    // Lee el estado pasado desde detalle-concierto
    const nav = this.router.getCurrentNavigation();
    const state = nav?.extras?.state || history.state;

    if (state?.concierto) {
      this.concierto = state.concierto;
      this.items     = state.items;
      this.total     = state.total;
    } else {
      // Si no hay datos, redirige al inicio
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
    this.procesando = true;
    // TODO: llamar al backend para crear la venta y generar boletos
    setTimeout(() => {
      this.procesando = false;
      this.router.navigate(['/compra/confirmacion'], {
        state: {
          concierto: this.concierto,
          items: this.items,
          total: this.total,
          codigoVenta: 'QB-' + Math.random().toString(36).substring(2, 8).toUpperCase()
        }
      });
    }, 1500);
  }

  volver(): void {
    this.router.navigate(['/concierto', this.concierto?.id || 1]);
  }
}
