import { Routes } from '@angular/router';
import { ReservaComponent } from './feature/reserva.component';

export const routes: Routes = [
  { path: '', redirectTo: 'reserva', pathMatch: 'full' },
  
  // Ruta directa a la pantalla de reservas
  { path: 'reserva', component: ReservaComponent },
  
  { path: '**', redirectTo: 'reserva' }
];