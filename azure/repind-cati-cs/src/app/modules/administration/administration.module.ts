
import { FlexLayoutModule } from '@angular/flex-layout';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SharedModule } from '../../shared/shared.module';
import { AdministrationRoutingModule } from './administration-routing.module';
import { AdministrationComponent } from './administration.component';

import { PreferenceComponent } from './preference/preference.component';
import { PreferenceResolver } from '../../core/resolvers/PreferenceResolver';
import { PreferenceDataResolver } from '../../core/resolvers/PreferenceDataResolver';
import { PreferenceDataComponent } from './preference-data/preference-data.component';
import { VariablesComponent } from './variables/variables.component';
import { VariablesResolver } from '../../core/resolvers/VariablesResolver';
import { PreferenceKeyTypeComponent } from './preference-key-type/preference-key-type.component';
import { PreferenceKeyTypeResolver } from 'src/app/core/resolvers/PreferenceKeyTypeResolver';
import { PaysComponent } from './pays/pays.component';
import { PaysResolver } from 'src/app/core/resolvers/PaysResolver';
import {PcsFactorComponent} from "./pcsFactor/pcsFactor.component";
import {PcsFactorResolver} from "../../core/resolvers/PcsFactorResolver";
import {PcsContractScoreComponent} from "./pcsContractScore/pcsContractScore.component";
import {PcsContractScoreResolver} from "../../core/resolvers/PcsContractScoreResolver";
import {PcsNonContractScoreComponent} from "./pcsNonContractScore/pcsNonContractScore.component";
import {PcsNonContractScoreResolver} from "../../core/resolvers/PcsNonContractScoreResolver";

@NgModule({
  declarations: [
    AdministrationComponent,
    PreferenceComponent,
    PreferenceDataComponent,
    VariablesComponent,
    PreferenceKeyTypeComponent,
    PaysComponent,
    PcsFactorComponent,
    PcsContractScoreComponent,
    PcsNonContractScoreComponent
  ],
  imports: [
    CommonModule,
    AdministrationRoutingModule,
    SharedModule,
    FlexLayoutModule
  ],
  providers: [
    PreferenceResolver,
    PreferenceDataResolver,
    VariablesResolver,
    PreferenceKeyTypeResolver,
    PaysResolver,
    PcsFactorResolver,
    PcsContractScoreResolver,
    PcsNonContractScoreResolver
  ]
})
export class AdministrationModule { }
