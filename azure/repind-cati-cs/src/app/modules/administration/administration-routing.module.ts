import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AdministrationComponent } from './administration.component';

import { PreferenceComponent } from './preference/preference.component';
import { PreferenceResolver } from '../../core/resolvers/PreferenceResolver';
import { PreferenceDataResolver } from '../../core/resolvers/PreferenceDataResolver';
import { PreferenceDataComponent } from './preference-data/preference-data.component';
import { VariablesComponent } from './variables/variables.component';
import { VariablesResolver } from '../../core/resolvers/VariablesResolver';
import { PreferenceKeyTypeComponent } from './preference-key-type/preference-key-type.component';
import { PreferenceKeyTypeResolver } from '../../core/resolvers/PreferenceKeyTypeResolver';
import { PaysComponent } from './pays/pays.component';
import { PaysResolver } from 'src/app/core/resolvers/PaysResolver';
import { PcsFactorComponent } from './pcsFactor/pcsFactor.component';
import { PcsFactorResolver } from "../../core/resolvers/PcsFactorResolver";
import {PcsContractScoreComponent} from "./pcsContractScore/pcsContractScore.component";
import {PcsContractScoreResolver} from "../../core/resolvers/PcsContractScoreResolver";
import {PcsNonContractScoreResolver} from "../../core/resolvers/PcsNonContractScoreResolver";
import {PcsNonContractScoreComponent} from "./pcsNonContractScore/pcsNonContractScore.component";

const routes: Routes = [
  {
    path: '',
    pathMatch: 'full',
    component: AdministrationComponent
  },
  {
    path: 'preference',
    component: PreferenceComponent,
    resolve: {
      preference: PreferenceResolver
    }
  },
  {
    path: 'preference-data',
    component: PreferenceDataComponent,
    resolve: {
      preferenceData: PreferenceDataResolver
    }
  },
  {
    path: 'variables',
    component: VariablesComponent,
    resolve: {
      variables: VariablesResolver
    }
  },
  {
    path: 'preference-key-type',
    component: PreferenceKeyTypeComponent,
    resolve: {
      preferenceKeyType: PreferenceKeyTypeResolver
    }
  },
  {
    path: 'pays',
    component: PaysComponent,
    resolve: {
      pays: PaysResolver
    }
  },
  {
    path: 'PcsFactor',
    component: PcsFactorComponent,
    resolve: {
      PcsFactor: PcsFactorResolver
    }
  },
  {
    path: 'PcsContractScore',
    component: PcsContractScoreComponent,
    resolve: {
      PcsContractScore: PcsContractScoreResolver
    }
  },
  {
    path: 'PcsNonContractScore',
    component: PcsNonContractScoreComponent,
    resolve: {
      PcsNonContractScore: PcsNonContractScoreResolver
    }
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AdministrationRoutingModule { }
