import { Subscription } from 'rxjs';
import { Component, OnInit, OnDestroy } from '@angular/core';
import { SearchFilterService } from '../../../../../core/services/filters/search-filter.service';
import { BasicIndividualData } from '../../../../../shared/models/individual/basic-individual-data';

@Component({
  selector: 'app-search-results-list',
  templateUrl: './search-results-list.component.html',
  styleUrls: ['./search-results-list.component.scss']
})
export class SearchResultsListComponent implements OnInit, OnDestroy {

  public individuals: BasicIndividualData[];

  private subscription: Subscription;

  constructor(private filterService: SearchFilterService) { }

  ngOnInit() {
    this.individuals = this.filterService.getFilteredIndividuals();
    this.subscription = this.filterService.filteredIndividualsObservable.subscribe((individuals: BasicIndividualData[]) => {
      this.individuals = individuals;
    });
  }

  ngOnDestroy() {
    this.subscription.unsubscribe();
  }

}
