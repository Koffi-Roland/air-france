import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ResourceRoutingModule } from './resource-routing.module';
import { SharedModule } from '../../shared/shared.module';
import { ResourceComponent } from './pages/resource/resource.component';
import { ResourceTabsCardComponent } from './components/resource-tabs/resource-tabs-card/resource-tabs-card.component';
import { ResourceDetailsCardComponent } from './components/resource-details-card/resource-details-card.component';
import { ResourceTabsComponent } from './components/resource-tabs/resource-tabs.component';

@NgModule({
  declarations: [
    ResourceComponent,
    ResourceTabsCardComponent,
    ResourceDetailsCardComponent,
    ResourceTabsComponent
  ],
  imports: [
    CommonModule,
    ResourceRoutingModule,
    SharedModule
  ]
})
export class ResourceModule { }
