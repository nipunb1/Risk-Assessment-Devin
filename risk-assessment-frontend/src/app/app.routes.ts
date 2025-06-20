import { Routes } from '@angular/router';
import { DashboardComponent } from './dashboard/dashboard';
import { RiskFormComponent } from './risk-form/risk-form';

export const routes: Routes = [
  { path: '', redirectTo: '/dashboard', pathMatch: 'full' },
  { path: 'dashboard', component: DashboardComponent },
  { path: 'create-risk', component: RiskFormComponent },
  { path: 'edit-risk/:id', component: RiskFormComponent }
];
