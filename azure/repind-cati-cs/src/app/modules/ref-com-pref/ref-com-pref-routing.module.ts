import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { RefComPrefComponent } from './ref-com-pref.component';
import { CommunicationPreferencesComponent } from './communication-preferences/communication-preferences.component';
import {
  CommunicationPreferencesEditComponent
} from './communication-preferences/communication-preferences-edit/communication-preferences-edit.component';
import {
  CommunicationPreferencesDgtComponent
} from './communication-preferences-dgt/communication-preferences-dgt.component';
import { CommunicationTypeComponent } from './communication-type/communication-type.component';
import { CountryMarketComponent } from './country-market/country-market.component';
import { DomainComponent } from './domain/domain.component';
import { GroupTypeComponent } from './group-type/group-type.component';
import { MediaComponent } from './media/media.component';
import { MediaEditComponent } from './media/media-edit/media-edit.component';
import { GroupComponent } from './group/group.component';
import { GroupInfoEditComponent } from './group/group-info-edit/group-info-edit.component';
import { GroupProductComponent } from './group/group-product/group-product.component';
import { PermissionComponent } from './permission/permission.component';
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
import { ProductResolver } from 'src/app/core/resolvers/ProductResolver';

const routes: Routes = [
  {
    path: '',
    component: RefComPrefComponent,
    pathMatch: 'full',
  },
  {
    path: 'communication-preferences',
    component: CommunicationPreferencesComponent,
    pathMatch: 'full',
    resolve: {
      communicationsPreferences: CommunicationPreferenceResolver,
      communicationsType: CommunicationTypeResolver,
      groupTypes: GroupTypeResolver,
      domains: DomainResolver,
      countryMarket: CountryMarketResolver,
      media: MediaResolver
    }
  },
  {
    path: 'communication-preferences/edit',
    component: CommunicationPreferencesEditComponent,
    pathMatch: 'full'
  },
  {
    path: 'communication-preferences-dgt',
    component: CommunicationPreferencesDgtComponent,
    pathMatch: 'full',
    resolve: {
      communicationsPreferencesDgt: CommunicationPreferenceDgtResolver,
      communicationsType: CommunicationTypeResolver,
      groupTypes: GroupTypeResolver,
      domains: DomainResolver
    }
  },
  {
    path: 'communication-type',
    component: CommunicationTypeComponent,
    pathMatch: 'full',
    resolve: {
      communicationsType: CommunicationTypeResolver
    }
  },
  {
    path: 'country-market',
    component: CountryMarketComponent,
    pathMatch: 'full',
    resolve: {
      countryMarket: CountryMarketResolver
    }
  },
  {
    path: 'domain',
    component: DomainComponent,
    pathMatch: 'full',
    resolve: {
      domain: DomainResolver
    }
  },
  {
    path: 'group-type',
    component: GroupTypeComponent,
    pathMatch: 'full',
    resolve: {
      groupType: GroupTypeResolver
    }
  },
  {
    path: 'media',
    component: MediaComponent,
    pathMatch: 'full',
    resolve: {
      media: MediaResolver
    }
  },
  { path: 'media/edit', component: MediaEditComponent },
  {
    path: 'group',
    component: GroupComponent,
    pathMatch: 'full',
    resolve: {
      group: GroupResolver,
      communicationsPreferencesDgt: CommunicationPreferenceDgtResolver
    }
  },
  {
    path: 'group-info/edit', component: GroupInfoEditComponent,
    pathMatch: 'full'
  },
  {
    path: 'group-product',
    component: GroupProductComponent,
    pathMatch: 'full',
    resolve: {
      group: GroupResolver,
      groupProduct: GroupProductResolver,
      product: ProductResolver
    }
  },
  {
    path: 'permission',
    component: PermissionComponent,
    pathMatch: 'full',
    resolve: {
      permission: PermissionResolver,
      communicationsPreferencesDgt: CommunicationPreferenceDgtResolver
    }
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class RefComPrefRoutingModule { }
