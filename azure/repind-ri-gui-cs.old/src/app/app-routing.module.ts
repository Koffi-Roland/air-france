import { HomeModule } from './modules/home/home.module';
import { Routes, RouterModule } from '@angular/router';
import { PageNotFoundComponent } from './pagenotfound/pagenotfound.component';
import { NgModule } from '@angular/core';
import { PageAccessDeniedComponent } from './page-access-denied/page-access-denied.component';
import { ReadGuard } from './core/guards/read.guard';
import { SearchModule } from './modules/search/search.module';
import { DashboardModule } from './modules/dashboard/dashboard.module';
import { ResourceModule } from './modules/resource/resource.module';
import { TrackingModule } from './modules/tracking/tracking.module';
import { MergeModule } from './modules/merge/merge.module';
import { MergeStatistiqueModule } from './modules/merge-statistique/merge-statistique.module';

const appRoutes: Routes = [
  {
    path: '',
    redirectTo: '/home',
    pathMatch: 'full'
  },
  {
    path: 'home',
    canActivate: [ReadGuard],
    loadChildren: () => HomeModule
  },
  {
    path: 'individuals/search',
    canActivate: [ReadGuard],
    loadChildren: () => SearchModule
  },
  {
    path: 'individuals/individual/dashboard',
    loadChildren: () => DashboardModule
  },
  {
    path: 'individuals/individual/resources',
    loadChildren: () => ResourceModule
  },
  {
    path: 'individuals/tracking',
    loadChildren: () => TrackingModule
  },
  {
    path: 'individuals/merge',
    loadChildren: () => MergeModule
  },
  {
    path: 'individuals/merge/statistiques',
    loadChildren: () => MergeStatistiqueModule
  },
  {
    path: 'accessdenied',
    component: PageAccessDeniedComponent
  },
  {
    path: '**',
    component: PageNotFoundComponent,
    canActivate: [ReadGuard]
  }
];

@NgModule({
  imports: [
    RouterModule.forRoot(appRoutes, {})
  ],
  exports: [
    RouterModule
  ]
})
export class AppRoutingModule {}
