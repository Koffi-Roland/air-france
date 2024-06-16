import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { MergeGuard } from '../../core/guards/merge.guard';
import { MergeComponent } from './pages/merge/merge.component';
import { MergeResultComponent } from './pages/merge-result/merge-result.component';
import { MergeResultResolver } from '../../core/resolvers/merge-result.resolver';
import { MergeDetailsComponent } from './pages/merge-details/merge-details.component';
import { MergeResolver } from '../../core/resolvers/merge.resolver';

const routes: Routes = [
  {
    path: '',
    pathMatch: 'full',
    component: MergeComponent,
    canActivate: [MergeGuard]
  },
  {
    path: 'result',
    component: MergeResultComponent,
    pathMatch: 'full',
    canActivate: [MergeGuard],
    resolve: {
      mergeData: MergeResultResolver
    }
  },
  {
    path: 'details',
    component: MergeDetailsComponent,
    pathMatch: 'full',
    canActivate: [MergeGuard],
    resolve: {
      mergeData: MergeResolver
    }
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class MergeRoutingModule { }
