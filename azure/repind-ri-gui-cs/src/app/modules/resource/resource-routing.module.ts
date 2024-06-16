import { ReadGuard } from './../../core/guards/read.guard';
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { ResourceComponent } from './pages/resource/resource.component';
import { ResourceResolver } from '../../core/resolvers/resource.resolver';

const routes: Routes = [
  {
    path: '',
    pathMatch: 'full',
    canActivate: [ReadGuard],
    component: ResourceComponent,
    resolve: {
      resources: ResourceResolver
    }
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ResourceRoutingModule { }
