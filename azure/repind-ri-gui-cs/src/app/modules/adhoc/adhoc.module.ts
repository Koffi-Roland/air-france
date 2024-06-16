import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';

import {AdhocRoutingModule} from './adhoc-routing.module';
import {SharedModule} from '../../shared/shared.module';
// tslint:disable-next-line: max-line-length
import {AdhocComponent} from './pages/adhoc/adhoc.component';
import { AdhocEditComponent} from './pages/adhoc-edit/adhoc-edit.component';
import {MatTableModule} from '@angular/material/table';
import {MatPaginatorModule} from '@angular/material/paginator';
import { AdhocTableComponent } from './components/adhoc-table/adhoc-table.component';
import { AdhocHeaderErrorModalComponent } from './components/adhoc-header-error-modal/adhoc-header-error-modal.component';
import { ConfirmationModalComponent } from '../../shared/components/confirmation-modal/confirmation-modal.component';

@NgModule({
  declarations: [
    AdhocComponent,
    AdhocEditComponent,
    AdhocTableComponent,
    AdhocHeaderErrorModalComponent,
    ConfirmationModalComponent
    ],
  imports: [
    CommonModule,
    AdhocRoutingModule,
    SharedModule,
    MatTableModule,
    MatPaginatorModule,
    MatPaginatorModule
  ],
  exports: []
})
export class AdhocModule {
}
