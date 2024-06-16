import { SharedModule } from './shared/shared.module';
import { CoreModule } from './core/core.module';
import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ReactiveFormsModule } from '@angular/forms';
import { FormsModule } from '@angular/forms';
import { FlexLayoutModule } from '@angular/flex-layout';
import { TranslateLoader, TranslateModule, TranslateService } from '@ngx-translate/core';
import { HttpClient, HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { HttpLoaderFactory } from './translate-loaders';
import { AppComponent } from './app.component';
import { PageNotFoundComponent } from './pagenotfound/pagenotfound.component';
import { CommonService } from './core/services/common.service';
import { LayoutModule } from '@angular/cdk/layout';
import { registerLocaleData } from '@angular/common';
import localeFr from '@angular/common/locales/fr';
import localeEn from '@angular/common/locales/en';
import { AppRoutingModule } from './app-routing.module';
import { ClipboardModule } from 'ngx-clipboard';
import { MatPaginatorIntl } from '@angular/material/paginator';
import { CustomMatPaginatorIntl } from './core/paginators/custom-mat-paginator-int';
import { MergeService } from './core/services/merge/merge.service';
import { TrackingIndividualService } from './core/services/tracking/trackingIndividual.service';
import { ErrorService } from './core/services/error.service';
import { PageAccessDeniedComponent } from './page-access-denied/page-access-denied.component';
import { LoaderComponent } from './core/loader/loader.component';
import { LoaderInterceptorService } from './core/interceptors/HTTPInterceptor';
import { MaterialModule } from './modules/material/material.module';
import { EmailService } from './core/services/resources/email.service';
import { TelecomService } from './core/services/resources/telecom.service';
import { MergeTransformService } from './core/services/transform/mergeTransform.service';
import { MergeStatistiquesService } from './core/services/merge/statistiques/mergeStatistiques.service';
import { DragDropModule } from '@angular/cdk/drag-drop';
import { UserModule } from '@airfranceklm/permission';
registerLocaleData(localeFr);
registerLocaleData(localeEn);

@NgModule({
  declarations: [
    AppComponent,
    PageNotFoundComponent,
    PageAccessDeniedComponent,
    LoaderComponent
  ],
  imports: [
    AppRoutingModule,
    BrowserModule,
    BrowserAnimationsModule,
    MaterialModule,
    CoreModule,
    SharedModule,
    HttpClientModule,
    FlexLayoutModule,
    ReactiveFormsModule,
    FormsModule,
    LayoutModule,
    ClipboardModule,
    DragDropModule,
    UserModule.forRoot({
      url: '/rigui-api/api/rest/resources/me'
    }),
    TranslateModule.forRoot({
      loader: { provide: TranslateLoader, useFactory: (HttpLoaderFactory), deps: [HttpClient] }
    })
  ],
  providers: [CommonService, TranslateService,
    TrackingIndividualService, EmailService, TelecomService,
    MergeService, ErrorService, MergeTransformService, MergeStatistiquesService,
    { provide: MatPaginatorIntl, useClass: CustomMatPaginatorIntl },
    {
      provide: HTTP_INTERCEPTORS,
      useClass: LoaderInterceptorService,
      multi: true
    }],
  bootstrap: [AppComponent]
})
export class AppModule { }
