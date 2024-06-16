import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { TrackingComponent } from './pages/tracking/tracking.component';
import { AdminGuard } from '../../core/guards/admin.guard';
import { TrackingServiceResolver } from '../../core/resolvers/tracking.resolver';

const routes: Routes = [
  {
    path: '',
    component: TrackingComponent,
    pathMatch: 'full',
    canActivate: [AdminGuard],
    resolve: {
      trackingIndividual: TrackingServiceResolver
    }
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class TrackingRoutingModule { }
