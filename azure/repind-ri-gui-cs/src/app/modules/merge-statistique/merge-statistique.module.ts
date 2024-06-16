import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MergeStatistiqueComponent } from './pages/merge-statistique.component';
import { SharedModule } from '../../shared/shared.module';
import { SearchModule } from '../search/search.module';
import { MergeStatistiqueRoutingComponent } from './merge-statistique-routing.module';
import { MergeStatistiquesResolver } from '../../core/resolvers/merge-statistiques.resolver';

@NgModule({
  imports: [
    CommonModule,
    SharedModule,
    MergeStatistiqueRoutingComponent,
    SearchModule
  ],
  declarations: [
    MergeStatistiqueComponent,
  ],
  providers: [
    MergeStatistiquesResolver
  ]
})
export class MergeStatistiqueModule { }
