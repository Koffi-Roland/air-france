import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { TrackingServiceResolver } from './resolvers/tracking.resolver';
import { DashboardResolver } from './resolvers/dashboard.resolver';
import { ResultsResolver } from './resolvers/results.resolver';
import { ResourceResolver } from './resolvers/resource.resolver';

@NgModule({
  declarations: [],
  imports: [
    CommonModule,
    HttpClientModule
  ],
  providers: [
    TrackingServiceResolver,
    DashboardResolver,
    ResultsResolver,
    ResourceResolver
  ]
})
export class CoreModule { }
