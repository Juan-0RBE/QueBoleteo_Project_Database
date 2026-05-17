import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, RouterOutlet } from '@angular/router';
import { ButtonModule } from 'primeng/button';

interface NavItem {
  label: string;
  icon: string;
  ruta: string;
}

@Component({
  selector: 'app-admin',
  standalone: true,
  imports: [CommonModule, RouterModule, RouterOutlet, ButtonModule],
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.css']
})
export class AdminComponent {

  sidebarAbierto = true;

  navItems: NavItem[] = [
    { label: 'Artistas',      icon: 'pi-microphone',  ruta: 'artista'      },
    { label: 'Grupos',      icon: 'pi-microphone',  ruta: 'grupo'      },
    { label: 'Conciertos',    icon: 'pi-ticket',      ruta: 'concierto'    },
    { label: 'Tours',         icon: 'pi-map',         ruta: 'tour'         },
    { label: 'Sedes',         icon: 'pi-building',    ruta: 'sede'         },
    { label: 'Organizadores', icon: 'pi-briefcase',   ruta: 'organizador' },
  ];

  toggleSidebar(): void {
    this.sidebarAbierto = !this.sidebarAbierto;
  }
}
