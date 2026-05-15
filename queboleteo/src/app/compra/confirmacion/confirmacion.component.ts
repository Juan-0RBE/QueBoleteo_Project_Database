import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterModule } from '@angular/router';
import { ButtonModule } from 'primeng/button';

@Component({
  selector: 'app-confirmacion',
  standalone: true,
  imports: [CommonModule, RouterModule, ButtonModule],
  templateUrl: './confirmacion.component.html',
  styleUrls: ['./confirmacion.component.css']
})
export class ConfirmacionComponent implements OnInit {

  concierto: any = null;
  items: any[] = [];
  total = 0;
  codigoVenta = '';

  constructor(private router: Router) {}

  ngOnInit(): void {
    const state = this.router.getCurrentNavigation()?.extras?.state || history.state;
    if (state?.codigoVenta) {
      this.concierto   = state.concierto;
      this.items       = state.items;
      this.total       = state.total;
      this.codigoVenta = state.codigoVenta;
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
}
