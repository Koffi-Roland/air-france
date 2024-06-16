
import { FlexLayoutModule } from '@angular/flex-layout';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SharedModule } from '../../shared/shared.module';
import { RefComPrefRoutingModule } from './ref-com-pref-routing.module';

import { CommunicationPreferenceResolver } from '../../core/resolvers/CommunicationPreferenceResolver';
import { CommunicationPreferenceDgtResolver } from '../../core/resolvers/CommunicationPreferenceDgtResolver';
import { CommunicationTypeResolver } from '../../core/resolvers/CommunicationTypeResolver';
import { CountryMarketResolver } from '../../core/resolvers/CountryMarketResolver';
import { DomainResolver } from '../../core/resolvers/DomainResolver';
import { GroupResolver } from '../../core/resolvers/GroupResolver';
import { GroupProductResolver } from '../../core/resolvers/GroupProductResolver';
import { GroupTypeResolver } from '../../core/resolvers/GroupTypeResolver';
import { MediaResolver } from '../../core/resolvers/MediaResolver';
import { PermissionResolver } from '../../core/resolvers/PermissionResolver';

import { RefComPrefComponent } from './ref-com-pref.component';
import { CommunicationPreferencesComponent } from './communication-preferences/communication-preferences.component';
import {
  CommunicationPreferencesDgtComponent
} from './communication-preferences-dgt/communication-preferences-dgt.component';
import { CommunicationTypeComponent } from './communication-type/communication-type.component';
import { CountryMarketComponent } from './country-market/country-market.component';
import { DomainComponent } from './domain/domain.component';
import { GroupComponent } from './group/group.component';
import { GroupTypeComponent } from './group-type/group-type.component';
import { MediaComponent } from './media/media.component';
import { PermissionComponent } from './permission/permission.component';
import {
  CommunicationPreferencesEditComponent
} from './communication-preferences/communication-preferences-edit/communication-preferences-edit.component';
import { MediaEditComponent } from './media/media-edit/media-edit.component';
import { GroupInfoEditComponent } from './group/group-info-edit/group-info-edit.component';
import { GroupProductComponent } from './group/group-product/group-product.component';
import { GroupProductEditComponent } from './group/group-product/group-product-edit/group-product-edit.component';
import { ProductResolver } from 'src/app/core/resolvers/ProductResolver';


@NgModule({
  declarations: [
    RefComPrefComponent,
    CommunicationPreferencesComponent,
    CommunicationPreferencesDgtComponent,
    CommunicationTypeComponent,
    CountryMarketComponent,
    DomainComponent,
    GroupComponent,
    GroupTypeComponent,
    MediaComponent,
    PermissionComponent,
    CommunicationPreferencesEditComponent,
    MediaEditComponent,
    GroupInfoEditComponent,
    GroupProductComponent,
    GroupProductEditComponent],
  imports: [
    CommonModule,
    RefComPrefRoutingModule,
    SharedModule,
    FlexLayoutModule
  ],
  providers: [
    CommunicationPreferenceResolver,
    CommunicationPreferenceDgtResolver,
    CommunicationTypeResolver,
    CountryMarketResolver,
    DomainResolver,
    GroupResolver,
    GroupTypeResolver,
    MediaResolver,
    PermissionResolver,
    GroupProductResolver,
    ProductResolver
  ]
})
export class RefComPrefModule { }
