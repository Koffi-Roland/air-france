
import { FlexLayoutModule } from '@angular/flex-layout';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SharedModule } from '../../shared/shared.module';
import { HomeRoutingModule } from './home-routing.module';
import { HomeComponent } from './pages/home.component';

@NgModule({
  declarations: [
    HomeComponent],
  imports: [
    CommonModule,
    HomeRoutingModule,
    SharedModule,
    FlexLayoutModule
  ]
})
export class HomeModule { }
