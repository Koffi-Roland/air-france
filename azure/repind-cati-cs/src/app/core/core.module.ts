import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { UserModule } from '@airfranceklm/permission';

@NgModule({
  declarations: [],
  imports: [
    CommonModule,
    HttpClientModule,
    UserModule.forRoot({ url: '/CatiWsWeb/api/rest/resources/securities/me' })
  ],
  providers: [
  ],
  exports: [
  ]
})
export class CoreModule { }
