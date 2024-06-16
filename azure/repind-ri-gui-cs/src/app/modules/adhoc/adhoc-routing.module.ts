import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {AdhocComponent} from './pages/adhoc/adhoc.component';
import {AdhocEditComponent} from './pages/adhoc-edit/adhoc-edit.component';

const routes: Routes = [
  {
    path: '',
    component: AdhocComponent,
  },
  {
    path: 'af',
    component: AdhocEditComponent,
    data: {airline: 'AF'}
  },
  {
    path: 'kl',
    component: AdhocEditComponent,
    data: {airline: 'KL'}
  },
  {
    path: '**',
    redirectTo: ''
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AdhocRoutingModule { }
