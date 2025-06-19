import { Routes } from '@angular/router';
import { DashboardComponent } from './dashboard/dashboard';
import { RiskFormComponent } from './risk-form/risk-form';
import { EngineeringExcellenceComponent } from './engineering-excellence/engineering-excellence';

export const routes: Routes = [
  { path: '', redirectTo: '/engineering-excellence', pathMatch: 'full' },
  { path: 'dashboard', component: DashboardComponent },
  { path: 'engineering-excellence', component: EngineeringExcellenceComponent },
  { path: 'create-risk', component: RiskFormComponent },
  { path: 'edit-risk/:id', component: RiskFormComponent }
];
