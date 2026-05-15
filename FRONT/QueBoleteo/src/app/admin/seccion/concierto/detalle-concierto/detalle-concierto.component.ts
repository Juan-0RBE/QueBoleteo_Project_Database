import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Router, ActivatedRoute } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { ButtonModule } from 'primeng/button';
import { InputNumberModule } from 'primeng/inputnumber';
import { DividerModule } from 'primeng/divider';

export interface ZonaDisponible {
  id: number;
  nombre: string;
  precio: number;
  cantidadDisponible: number;
  descripcion: string;
  tieneAsientos: boolean;
}

export interface ItemSeleccionado {
  zona: ZonaDisponible;
  cantidad: number;
}

@Component({
  selector: 'app-detalle-concierto',
  standalone: true,
  imports: [
    CommonModule, RouterModule, FormsModule,
    ButtonModule, InputNumberModule, DividerModule,
  ],
  templateUrl: './detalle-concierto.component.html',
  styleUrls: ['./detalle-concierto.component.css']
})
export class DetalleConciertoComponent {

  // Datos de ejemplo cuando tengamos BD usamos ID
  concierto = {
    id: 1,
    nombre: 'Noche Eléctrica',
    artista: 'The Midnight',
    imagen: 'https://images.unsplash.com/photo-1540039155733-5bb30b53aa14?w=1200&q=80',
    fecha: '15 Jun 2025',
    hora: '8:00 PM',
    ciudad: 'Bogotá',
    sede: 'Movistar Arena',
    genero: 'Electrónica',
    edadMinima: 18,
    descripcion: 'Una noche inolvidable con los mejores sonidos del synthwave y la música electrónica. The Midnight regresa a Colombia con su gira mundial, trayendo consigo toda la nostalgia y energía de sus álbumes más recientes.',
    zonas: [
      { id: 1, nombre: 'Piso',   precio: 280000, cantidadDisponible: 500, descripcion: 'Acceso al piso principal frente al escenario. De pie.',           tieneAsientos: false },
      { id: 2, nombre: 'Palco',  precio: 450000, cantidadDisponible: 120, descripcion: 'Vista privilegiada desde los palcos laterales. Asientos numerados.', tieneAsientos: true  },
      { id: 3, nombre: 'VIP',    precio: 750000, cantidadDisponible: 50,  descripcion: 'Área exclusiva con open bar, acceso anticipado y zona preferencial.', tieneAsientos: false },
      { id: 4, nombre: 'Platea', precio: 180000, cantidadDisponible: 800, descripcion: 'Zona general con buena visibilidad. De pie.',                        tieneAsientos: false },
    ] as ZonaDisponible[]
  };

  zonaSeleccionada: ZonaDisponible | null = null;
  cantidades: { [zonaId: number]: number } = {};

  constructor(private router: Router) {}

  getCantidad(zonaId: number): number {
    return this.cantidades[zonaId] || 0;
  }

  setCantidad(zonaId: number, valor: number): void {
    this.cantidades[zonaId] = Math.max(0, valor || 0);
  }

  incrementar(zona: ZonaDisponible): void {
    const actual = this.getCantidad(zona.id);
    if (actual < zona.cantidadDisponible && actual < 10) {
      this.cantidades[zona.id] = actual + 1;
    }
  }

  decrementar(zonaId: number): void {
    const actual = this.getCantidad(zonaId);
    if (actual > 0) this.cantidades[zonaId] = actual - 1;
  }

  get itemsSeleccionados(): ItemSeleccionado[] {
    return this.concierto.zonas
      .filter(z => this.getCantidad(z.id) > 0)
      .map(z => ({ zona: z, cantidad: this.getCantidad(z.id) }));
  }

  get totalBoletas(): number {
    return this.itemsSeleccionados.reduce((acc, i) => acc + i.cantidad, 0);
  }

  get totalPrecio(): number {
    return this.itemsSeleccionados.reduce((acc, i) => acc + i.zona.precio * i.cantidad, 0);
  }

  formatPrecio(valor: number): string {
    return new Intl.NumberFormat('es-CO', {
      style: 'currency', currency: 'COP', maximumFractionDigits: 0
    }).format(valor);
  }

  irAResumen(): void {
    // TODO: pasar los datos al resumen
    this.router.navigate(['/compra/resumen'], {
      state: {
        concierto: this.concierto,
        items: this.itemsSeleccionados,
        total: this.totalPrecio
      }
    });
  }
}
