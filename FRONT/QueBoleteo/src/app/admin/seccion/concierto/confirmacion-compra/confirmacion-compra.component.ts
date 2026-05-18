import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { ButtonModule } from 'primeng/button';
import { DividerModule } from 'primeng/divider';
import { CompraResponseDto } from '../../../../core/services/venta.service';
import { ZonaCompraContext } from '../seleccion-asientos/seleccion-asientos.component';

@Component({
  selector: 'app-confirmacion-compra',
  standalone: true,
  imports: [CommonModule, ButtonModule, DividerModule],
  templateUrl: './confirmacion-compra.component.html',
  styleUrls: ['./confirmacion-compra.component.css']
})
export class ConfirmacionCompraComponent implements OnInit {

  respuesta!: CompraResponseDto;
  zona!: ZonaCompraContext;
  cargado = false;

  constructor(private router: Router) {
    const nav = this.router.getCurrentNavigation();
    const state = nav?.extras?.state as {
      respuesta: CompraResponseDto;
      zona: ZonaCompraContext;
    } | undefined;

    if (state?.respuesta) {
      this.respuesta = state.respuesta;
      this.zona = state.zona;
      this.cargado = true;
    }
  }

  ngOnInit(): void {
    if (!this.cargado) {
      this.router.navigate(['/principal/paginaprincipal']);
    }
  }

  formatPrecio(valor: number): string {
    return new Intl.NumberFormat('es-CO', {
      style: 'currency', currency: 'COP', maximumFractionDigits: 0
    }).format(valor);
  }

  irAlInicio(): void {
    this.router.navigate(['/principal/paginaprincipal']);
  }
}
