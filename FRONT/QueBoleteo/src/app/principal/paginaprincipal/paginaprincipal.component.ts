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
  sede: string;
  estado: string;
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
  sedeSeleccionada: string | null = null;
  estadoSeleccionado: string | null = null;

  conciertos: Concierto[] = [];
  artistas: Artista[] = [];

  loadingConciertos = false;
  loadingArtistas = false;

  sedes: { label: string; value: string | null }[] = [];

  estados = [
    { label: 'Todos los estados', value: null },
    { label: 'Programado', value: 'Programado' },
    { label: 'Cancelado', value: 'Cancelado' },
    { label: 'Finalizado', value: 'Finalizado' },
  ];

  isLoggedIn: boolean = false;
  isAdmin: boolean = false;
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
        this.isAdmin = logged && this.authService.isAdmin();
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
          artista: dto.artista || dto.nombreArtista || '',
          imagen: dto.imagenConcierto || 'https://placehold.co/600x400?text=Concierto',
          fecha: dto.fechaConcierto
            ? new Date(dto.fechaConcierto).toLocaleDateString('es-CO')
            : 'Por confirmar',
          sede: dto.nombreSede || 'Por confirmar',
          estado: dto.estadoConcierto || 'Sin estado',
          destacado: false,
        }));

        const programados = this.conciertos
          .map((c, i) => ({ c, i }))
          .filter(({ c }) => c.estado === 'Programado');

        if (programados.length > 0) {
          const { i } = programados[Math.floor(Math.random() * programados.length)];
          this.conciertos[i].destacado = true;
        } else if (this.conciertos.length > 0) {

          this.conciertos[Math.floor(Math.random() * this.conciertos.length)].destacado = true;
        }

        const sedesUnicas = [...new Set(this.conciertos.map((c) => c.sede))];
        this.sedes = [
          { label: 'Todas las sedes', value: null },
          ...sedesUnicas.map((s) => ({ label: s, value: s })),
        ];

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
        const todos = data.map((art) => ({
          id: art.idArtista,
          nombre: art.nombreArtista,
          genero: art.lenguajeArtista || 'Variado',
          imagen: art.imagenArtista || 'https://placehold.co/150?text=Artista',
          pais: art.paisOrigenArtista || 'Internacional',
        }));

        // Aleatorios, máximo 5
        this.artistas = todos
          .sort(() => Math.random() - 0.5)
          .slice(0, 5);

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
      const nombreConcierto = c.nombre?.toLowerCase() ?? '';
      const artistaConcierto = c.artista?.toLowerCase() ?? '';
      const busquedaMinuscula = this.busqueda.toLowerCase();

      const coincideBusqueda =
        !this.busqueda ||
        nombreConcierto.includes(busquedaMinuscula) ||
        artistaConcierto.includes(busquedaMinuscula);

      const coincideSede = !this.sedeSeleccionada || c.sede === this.sedeSeleccionada;
      const coincideEstado = !this.estadoSeleccionado || c.estado === this.estadoSeleccionado;

      return coincideBusqueda && coincideSede && coincideEstado;
    });
  }

  get conciertoDestacado(): Concierto | undefined {
    return this.conciertos.find((c) => c.destacado);
  }

  limpiarFiltros(): void {
    this.busqueda = '';
    this.sedeSeleccionada = null;
    this.estadoSeleccionado = null;
  }

  formatPrecio(valor: number): string {
    return new Intl.NumberFormat('es-CO', {
      style: 'currency',
      currency: 'COP',
      maximumFractionDigits: 0,
    }).format(valor);
  }
}
