import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { ResultsResolver } from '../../core/resolvers/results.resolver';
import { SearchResultsCardsComponent } from '../search/components/layouts/search-results-cards/search-results-cards.component';
import { SearchResultsListComponent } from '../search/components/layouts/search-results-list/search-results-list.component';
import { SearchResultsArrayComponent } from '../search/components/layouts/search-results-array/search-results-array.component';
import { MergeStatistiqueComponent } from './pages/merge-statistique.component';
import { MergeStatistiquesResolver } from '../../core/resolvers/merge-statistiques.resolver';
import { MergeStatistiquesGuard } from '../../core/guards/mergeStatistiques.guard';

const routes: Routes = [
  {
    path: '',
    canActivate: [MergeStatistiquesGuard],
    component: MergeStatistiqueComponent,
    resolve: {
      statistiques: MergeStatistiquesResolver
    },
    children: [
      {
        path: 'grid',
        component: SearchResultsCardsComponent
      },
      {
        path: 'list',
        component: SearchResultsListComponent
      },
      {
        path: 'array',
        component: SearchResultsArrayComponent
      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class MergeStatistiqueRoutingComponent { }
