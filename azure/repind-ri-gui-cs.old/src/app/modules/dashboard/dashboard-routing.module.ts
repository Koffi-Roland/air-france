import { DashboardResolver } from './../../core/resolvers/dashboard.resolver';
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { ReadGuard } from '../../core/guards/read.guard';
import { DashboardComponent } from './pages/dashboard/dashboard.component';

const routes: Routes = [
  {
    path: '',
    component: DashboardComponent,
    resolve: {
      individual: DashboardResolver
    },
    canActivate: [ReadGuard]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class DashboardRoutingModule { }
