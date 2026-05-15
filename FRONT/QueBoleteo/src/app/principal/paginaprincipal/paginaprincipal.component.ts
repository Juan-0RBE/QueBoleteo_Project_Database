import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';

// PrimeNG
import { ButtonModule } from 'primeng/button';
import { InputTextModule } from 'primeng/inputtext';
import { SelectModule } from 'primeng/select';
import { CardModule } from 'primeng/card';
import { TagModule } from 'primeng/tag';
import { AvatarModule } from 'primeng/avatar';
import { DividerModule } from 'primeng/divider';

export interface Concierto {
  id: number;
  nombre: string;
  artista: string;
  imagen: string;
  fecha: string;
  ciudad: string;
  genero: string;
  precioDesde: number;
  destacado?: boolean;
}

export interface Artista {
  id: number;
  nombre: string;
  genero: string;
  imagen: string;
  pais: string;
}

@Component({
  selector: 'app-paginaprincipal',
  standalone: true,
  imports: [
    CommonModule,
    RouterModule,
    FormsModule,
    ButtonModule,
    InputTextModule,
    SelectModule,
    CardModule,
    TagModule,
    AvatarModule,
    DividerModule,
  ],
  templateUrl: './paginaprincipal.component.html',
  styleUrls: ['./paginaprincipal.component.css'],
})
export class PaginaPrincipalComponent {
  busqueda = '';
  ciudadSeleccionada: string | null = null;
  generoSeleccionado: string | null = null;

  ciudades = [
    { label: 'Todas las ciudades', value: null },
    { label: 'Bogotá', value: 'Bogotá' },
    { label: 'Medellín', value: 'Medellín' },
    { label: 'Cali', value: 'Cali' },
    { label: 'Barranquilla', value: 'Barranquilla' },
  ];

  generos = [
    { label: 'Todos los géneros', value: null },
    { label: 'Rock', value: 'Rock' },
    { label: 'Pop', value: 'Pop' },
    { label: 'Electrónica', value: 'Electrónica' },
    { label: 'Reggaeton', value: 'Reggaeton' },
    { label: 'Jazz', value: 'Jazz' },
  ];

  conciertos: Concierto[] = [
    {
      id: 1,
      nombre: 'La versatilidad en persona Raaaa',
      artista: 'Faraon Love Shady',
      imagen: 'https://imgs.search.brave.com/p5SiYLdtMC-7qJ8hK5Uid6Si_LqDiITqTPmUYkrsWV0/rs:fit:860:0:0:0/g:ce/aHR0cHM6Ly9ibG9n/Z2VyLmdvb2dsZXVz/ZXJjb250ZW50LmNv/bS9pbWcvYi9SMjl2/WjJ4bC9BVnZYc0Vq/RUNqcElweFp1Ymdq/NUtBaENFblppVGxu/ZThuU3VDTVcwT1dq/UDhFZG1QTmhBdXk0/TmhzeEN6YUN0djl5/ekdPVXNiRUt6dFQ0/b1VLbFU4YUJYOHFl/ZEZPdGhfS2dqTko4/a3Vselp0ZGg2MnBz/clh1WlNkRktSNDF3/eHhnSzI1ZERaYjZz/OVNDZWRqc3VkZC0w/SFFiNmlDcEVlRTJr/X2pJcnZULXBUS3Qz/MHBHUk1tSGlMTmdZ/QWs1Y2VpTzgvdzQw/MC1oMjUwL0ZhcmFv/biUyMExvdmUlMjBT/aGFkeSUyMGVuJTIw/Y29uY2llcnRvJTIw/ZW4lMjBBcmVxdWlw/YSUyMC0lMjAwMyUy/MGRlJTIwb2N0dWJy/ZSUyMDIwMjUlMjBQ/UkVDSU8lMjBERSUy/MEVOVFJBREFTLmpw/Zw',
      fecha: 'Mañana mismo pa',
      ciudad: 'Arequipa',
      genero: 'Masculino',
      precioDesde: 1555555555555,
      destacado: false,
    },
    {
      id: 2,
      nombre: 'Chayanne en gira para el dia de las mamis',
      artista: 'Chayanne',
      imagen: 'https://imgs.search.brave.com/wvhxtrQPyg9ThZFiPGM39kUD7FidV6IvAFhSBPSWQXI/rs:fit:860:0:0:0/g:ce/aHR0cHM6Ly9tb29k/eWNlbnRlcmF0eC53/cGVuZ2luZXBvd2Vy/ZWQuY29tL3dwLWNv/bnRlbnQvdXBsb2Fk/cy8yMDI0LzAyL0No/YXlhbm5lYXRNb29k/eUNlbnRlci5qcGc',
      fecha: 'Pasado mañana',
      ciudad: 'Barranquilla',
      genero: 'Pop',
      precioDesde: 10,
      destacado: true,
    }
  ];

  artistas: Artista[] = [
    {
      id: 1,
      nombre: 'Faraon Love Shady',
      genero: 'Masculino femenino y 38 tipos de gay',
      imagen:
        'https://imgs.search.brave.com/PBgFV9Z5rBNiTYPKa9lBrGAjbdwT6lWmTtPG2ImnZ8c/rs:fit:860:0:0:0/g:ce/aHR0cHM6Ly9pLmF1/ZGlvbWFjay5jb20v/ZmFyYW9uLWxvdmUt/c2hhZHkvYjUzMmU0/YmZjYS53ZWJwP3dp/ZHRoPTQwMA',
      pais: 'Peru',
    },
  ];

  get conciertosFiltrados(): Concierto[] {
    return this.conciertos.filter((c) => {
      const coincideBusqueda =
        !this.busqueda ||
        c.nombre.toLowerCase().includes(this.busqueda.toLowerCase()) ||
        c.artista.toLowerCase().includes(this.busqueda.toLowerCase());

      const coincideCiudad = !this.ciudadSeleccionada || c.ciudad === this.ciudadSeleccionada;

      const coincideGenero = !this.generoSeleccionado || c.genero === this.generoSeleccionado;

      return coincideBusqueda && coincideCiudad && coincideGenero;
    });
  }

  get conciertoDestacado(): Concierto | undefined {
    return this.conciertos.find((c) => c.destacado);
  }

  limpiarFiltros(): void {
    this.busqueda = '';
    this.ciudadSeleccionada = null;
    this.generoSeleccionado = null;
  }

  formatPrecio(valor: number): string {
    return new Intl.NumberFormat('es-CO', {
      style: 'currency',
      currency: 'COP',
      maximumFractionDigits: 0,
    }).format(valor);
  }
}
