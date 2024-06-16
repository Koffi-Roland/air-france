import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { TrackingRoutingModule } from './tracking-routing.module';
import { TrackingComponent } from './pages/tracking/tracking.component';
import { SharedModule } from '../../shared/shared.module';
import { FlexLayoutModule } from '@angular/flex-layout';

@NgModule({
  declarations: [TrackingComponent],
  imports: [
    CommonModule,
    TrackingRoutingModule,
    SharedModule,
    FlexLayoutModule
  ]
})
export class TrackingModule { }
