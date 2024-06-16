import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { TranslateLoader, TranslateModule, TranslateService } from '@ngx-translate/core';
import { HttpLoaderFactory } from './translate-loaders';
import { registerLocaleData } from '@angular/common';
import localeFr from '@angular/common/locales/fr';
import localeEn from '@angular/common/locales/en';
import { HttpClient, HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';

import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

import { AppComponent } from './app.component';
import { AppRoutingModule } from './app-routing.module';
import { DomainService } from './core/services/domain.service';
import { MediaService } from './core/services/media.service';
import { CommunicationTypeService } from './core/services/communication-type.service';
import { CountryMarketService } from './core/services/country-market.service';
import { GroupTypeService } from './core/services/group-type.service';
import { GroupService } from './core/services/group.service';
import { PermissionService } from './core/services/permission.service';
import { PermissionQuestionService } from './core/services/permission-question.service';
import { CommunicationPreferencesDgtService } from './core/services/communication-preferences-dgt.service';
import { CommunicationPreferencesService } from './core/services/communication-preferences.service';
import { UserConnectedService } from './core/services/user-connected.service';
import { SharedModule } from './shared/shared.module';
import { FlexLayoutModule, CoreModule } from '@angular/flex-layout';
import { ValidatorsCustom } from './shared/widgets/validators/validators-custom.component';
import { MaterialModule } from './modules/material/material.module';

import { EventActionService } from './shared/arrayDisplayRefTable/_services/eventAction.service';
import { PreferenceService } from './core/services/preference.service';
import { VariablesService } from './core/services/variables.service';
import { AdministrationModule } from './modules/administration/administration.module';
import { RefComPrefModule } from './modules/ref-com-pref/ref-com-pref.module';
import { LoaderComponent } from './core/loader/loader.component';
import { LoaderInterceptorService } from './core/interceptors/HTTPInterceptor';
import { LoaderService } from './core/loader/_services/loader.service';

registerLocaleData(localeFr);
registerLocaleData(localeEn);

@NgModule({
  declarations: [
    AppComponent,
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
    AdministrationModule,
    RefComPrefModule,
    TranslateModule.forRoot({
      loader: { provide: TranslateLoader, useFactory: (HttpLoaderFactory), deps: [HttpClient] }
    })
  ],
  providers: [TranslateService,
    DomainService, MediaService, CommunicationTypeService, GroupTypeService, CountryMarketService,
    CommunicationPreferencesDgtService, CommunicationPreferencesService,
    PermissionService, PermissionQuestionService, GroupService,
    UserConnectedService, ValidatorsCustom, EventActionService, PreferenceService, VariablesService,
    LoaderService,
    {
      provide: HTTP_INTERCEPTORS,
      useClass: LoaderInterceptorService,
      multi: true
    }],
  bootstrap: [AppComponent]
})
export class AppModule { }
