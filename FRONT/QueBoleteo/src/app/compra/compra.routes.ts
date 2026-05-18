import { Routes } from '@angular/router';

export const COMPRA_ROUTES: Routes = [
  {
    path: 'asientos',
    loadComponent: () =>
      import('../admin/seccion/concierto/seleccion-asientos/seleccion-asientos.component')
        .then(m => m.SeleccionAsientosComponent) // Asegúrate de que el componente se llame exactamente así
  },
  {
    path: 'resumen',
    loadComponent: () =>
      import('./resumen/resumen.component')
        .then(m => m.ResumenComponent)
  },
  {
    path: 'confirmacion',
    loadComponent: () =>
      import('./confirmacion/confirmacion.component')
        .then(m => m.ConfirmacionComponent)
  }
];
