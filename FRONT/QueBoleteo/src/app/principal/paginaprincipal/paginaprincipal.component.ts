import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { Subscription } from 'rxjs';

import { ButtonModule } from 'primeng/button';
import { InputTextModule } from 'primeng/inputtext';
import { SelectModule } from 'primeng/select';
import { CardModule } from 'primeng/card';
import { TagModule } from 'primeng/tag';
import { AvatarModule } from 'primeng/avatar';
import { DividerModule } from 'primeng/divider';

import { ConciertoService } from '../../core/services/concierto.service';
import { ArtistaService } from '../../core/services/artista.service';
import { AuthService} from '../../core/services/auth.service';

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
export class PaginaPrincipalComponent implements OnInit {
  busqueda = '';
  ciudadSeleccionada: string | null = null;
  generoSeleccionado: string | null = null;

  // Arrays de control que alimentarán el HTML
  conciertos: Concierto[] = [];
  artistas: Artista[] = [];

  // Estados de carga y error para dar feedback visual si quieres
  loadingConciertos = false;
  loadingArtistas = false;

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

  isLoggedIn: boolean = false;
  nombreUsuario: string = '';
  private authSubscription!: Subscription;

  constructor(
    private conciertoService: ConciertoService,
    private artistaService: ArtistaService,
    private authService: AuthService,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    this.cargarConciertos();
    this.cargarArtistas();

    this.authSubscription = this.authService.isLoggedIn$.subscribe({
      next: (logged: boolean) => {
        this.isLoggedIn = logged;
        this.nombreUsuario = logged ? (this.authService.getUsername() || 'Usuario') : '';
      }
    });
  }

  cerrarSesion(): void {
    if (confirm('¿Estás seguro de que deseas cerrar sesión en QueBoleteo?')) {
      this.authService.logout();
    }
  }

  ngOnDestroy(): void {
    if (this.authSubscription) {
      this.authSubscription.unsubscribe();
    }
  }

  cargarConciertos(): void {
    this.loadingConciertos = true;
    this.conciertoService.getAll().subscribe({
      next: (dtos: any[]) => {
        this.conciertos = dtos.map((dto) => ({
          id: dto.idConcierto,
          nombre: dto.nombreConcierto,
          artista: dto.artista,
          imagen: dto.imagenConcierto || 'https://placehold.co/600x400?text=Concierto',
          fecha: dto.fechaConcierto ? new Date(dto.fechaConcierto).toLocaleDateString('es-CO') : 'Por confirmar',
          ciudad: dto.nombreSede || 'Por confirmar',
          genero: 'Pop',
          precioDesde: 50000,
          destacado: dto.estadoConcierto === 'Programado',
        }));
        this.loadingConciertos = false;
        this.cdr.detectChanges();
      },
      error: () => {
        this.loadingConciertos = false;
      },
    });
  }

  cargarArtistas(): void {
    this.loadingArtistas = true;
    this.artistaService.getAll().subscribe({
      next: (data: any[]) => {
        this.artistas = data.map((art) => ({
          id: art.idArtista,
          nombre: art.nombreArtista,
          genero: art.lenguajeArtista || 'Variado',
          imagen: art.imagenArtista || 'https://placehold.co/150?text=Artista',
          pais: art.paisOrigenArtista || 'Internacional',
        }));
        this.loadingArtistas = false;
        this.cdr.detectChanges();
      },
      error: () => {
        this.loadingArtistas = false;
      },
    });
  }

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
