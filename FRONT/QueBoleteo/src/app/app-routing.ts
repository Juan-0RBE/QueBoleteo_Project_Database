import { Routes } from '@angular/router';
import { LoginComponent } from './autenticacion/login/login.component';
import { RegistroComponent } from './autenticacion/registro/registro.component';
import { PaginaPrincipalComponent } from './principal/paginaprincipal/paginaprincipal.component';
import { DetalleConciertoComponent } from './admin/seccion/concierto/detalle-concierto/detalle-concierto.component';
import { ResumenComponent } from './compra/resumen/resumen.component';
import { ConfirmacionComponent } from './compra/confirmacion/confirmacion.component';
import { adminRouting } from './admin/admin-routing';
import { adminGuard } from './core/guards/admin.guard';

export const routes: Routes = [
  { path: '', redirectTo: 'principal/paginaprincipal', pathMatch: 'full' },
  { path: 'autenticacion/login', component: LoginComponent },
  { path: 'autenticacion/registro', component: RegistroComponent },
  { path: 'principal/paginaprincipal', component: PaginaPrincipalComponent },
  { path: 'concierto/:id', component: DetalleConciertoComponent },
  { path: 'admin', children: adminRouting, canActivate: [adminGuard] },
  {
    path: 'compra',
    loadChildren: () => import('./compra/compra.routes').then(m => m.COMPRA_ROUTES)
  }

];
