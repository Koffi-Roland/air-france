import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

const routes: Routes = [
  {
    path: '',
    redirectTo: '/home',
    pathMatch: 'full'
  },
  {
    path: 'home',
    loadChildren: () => import('./modules/home/home.module').then(m => m.HomeModule)
  },
  {
    path: 'ref-com-pref',
    loadChildren: () => import('./modules/ref-com-pref/ref-com-pref.module').then(m => m.RefComPrefModule)
  },
  {
    path: 'administration',
    loadChildren: () => import('./modules/administration/administration.module').then(m => m.AdministrationModule)
  },
  {
    path: '**',
    redirectTo: '/home',
    pathMatch: 'full'
  }

];

@NgModule({
  imports: [RouterModule.forRoot(routes, {})],
  providers: [
  ],
  exports: [RouterModule]
})
export class AppRoutingModule {}
