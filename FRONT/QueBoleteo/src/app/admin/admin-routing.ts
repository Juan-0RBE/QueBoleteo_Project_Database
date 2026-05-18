import { Routes } from '@angular/router';
import { AdminComponent } from './admin.component';
import { AdminArtistaComponent } from './seccion/artista/artista.component';
import { AdminConciertoComponent } from './seccion/concierto/concierto.component';
import { AdminTourComponent } from './seccion/tour/tour.component';
import { AdminSedeComponent } from './seccion/sede/sede.component';
import { AdminOrganizadorComponent } from './seccion/organizador/organizador.component';
import { AdminGrupoComponent } from './seccion/grupo/grupo.component';
import { AdminGeneroComponent } from './seccion/genero/genero.component';

export const adminRouting: Routes = [
  {
    path: '',
    component: AdminComponent,
    children: [
      { path: '', redirectTo: 'artista', pathMatch: 'full' },
      { path: 'artista', component: AdminArtistaComponent },
      { path: 'grupo', component: AdminGrupoComponent },
      { path: 'genero', component: AdminGeneroComponent },
      { path: 'concierto',    component: AdminConciertoComponent    },
      { path: 'tour',         component: AdminTourComponent         },
      { path: 'sede',         component: AdminSedeComponent         },
      { path: 'organizador', component: AdminOrganizadorComponent },
    ],
  },
];
