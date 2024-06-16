import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { SearchRoutingModule } from './search-routing.module';
import { SearchComponent } from './pages/search/search.component';
import { SharedModule } from '../../shared/shared.module';
import { ResultsComponent } from './pages/results/results.component';
import { SearchResultsCardsComponent } from './components/layouts/search-results-cards/search-results-cards.component';
import { SearchResultsListComponent } from './components/layouts/search-results-list/search-results-list.component';
import { SearchResultsArrayComponent } from './components/layouts/search-results-array/search-results-array.component';
import { GinSearchFormComponent } from './components/forms/gin-search-form/gin-search-form.component';
import { MulticriteriaSearchFormComponent } from './components/forms/multicriteria-search-form/multicriteria-search-form.component';
import { TelecomSearchFormComponent } from './components/forms/telecom-search-form/telecom-search-form.component';
import { EmailSearchFormComponent } from './components/forms/email-search-form/email-search-form.component';
import { SearchPickerComponent } from './components/pickers/search-picker/search-picker.component';
import { ResultsViewPickerComponent } from './components/pickers/results-view-picker/results-view-picker.component';
import { SearchButtonComponent } from './components/ui-elements/search-button/search-button.component';
import { BasicIndividualCardComponent } from './components/ui-elements/basic-individual-card/basic-individual-card.component';
import { ResultsHeaderComponent } from './components/ui-elements/results-header/results-header.component';
import { SearchResultsItemComponent } from './components/ui-elements/search-results-item/search-results-item.component';
import { SearchFiltersCardComponent } from './components/ui-elements/search-filters-card/search-filters-card.component';
// tslint:disable-next-line: max-line-length
import { FilterExpansionPanelComponent } from './components/ui-elements/search-filters-card/filter-expansion-panel/filter-expansion-panel.component';

@NgModule({
  declarations: [
    SearchComponent,
    SearchPickerComponent,
    GinSearchFormComponent,
    MulticriteriaSearchFormComponent,
    TelecomSearchFormComponent,
    EmailSearchFormComponent,
    SearchButtonComponent,
    ResultsComponent,
    BasicIndividualCardComponent,
    SearchResultsCardsComponent,
    ResultsHeaderComponent,
    ResultsViewPickerComponent,
    SearchResultsListComponent,
    SearchResultsArrayComponent,
    SearchResultsItemComponent,
    SearchFiltersCardComponent,
    FilterExpansionPanelComponent
  ],
  imports: [
    CommonModule,
    SearchRoutingModule,
    SharedModule
  ],
  exports: [
    SearchResultsCardsComponent,
    SearchResultsListComponent,
    SearchResultsArrayComponent,
    ResultsComponent,
    SearchFiltersCardComponent,
    ResultsHeaderComponent,
    BasicIndividualCardComponent,
    SearchResultsItemComponent,
    FilterExpansionPanelComponent,
    ResultsViewPickerComponent
  ]
})
export class SearchModule { }
