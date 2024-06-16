import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { SearchComponent } from './pages/search/search.component';
import { ResultsComponent } from './pages/results/results.component';
import { ResultsResolver } from '../../core/resolvers/results.resolver';
import { SearchResultsCardsComponent } from './components/layouts/search-results-cards/search-results-cards.component';
import { SearchResultsListComponent } from './components/layouts/search-results-list/search-results-list.component';
import { SearchResultsArrayComponent } from './components/layouts/search-results-array/search-results-array.component';
import { GinSearchFormComponent } from './components/forms/gin-search-form/gin-search-form.component';
import { MulticriteriaSearchFormComponent } from './components/forms/multicriteria-search-form/multicriteria-search-form.component';
import { TelecomSearchFormComponent } from './components/forms/telecom-search-form/telecom-search-form.component';
import { EmailSearchFormComponent } from './components/forms/email-search-form/email-search-form.component';

const routes: Routes = [
  {
    path: '',
    component: SearchComponent,
    children: [
      {
        path: 'gin',
        component: GinSearchFormComponent
      },
      {
        path: 'multicriteria',
        component: MulticriteriaSearchFormComponent
      },
      {
        path: 'telecom',
        component: TelecomSearchFormComponent
      },
      {
        path: 'email',
        component: EmailSearchFormComponent
      }
    ]
  },
  {
    path: 'results',
    component: ResultsComponent,
    resolve: {
      individuals: ResultsResolver
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
export class SearchRoutingModule { }
