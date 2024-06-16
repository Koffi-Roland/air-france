import { Component, OnInit, OnDestroy } from '@angular/core';
import { SearchFilterService } from '../../../../../core/services/filters/search-filter.service';
import { Subscription } from 'rxjs';
import { BasicIndividualData } from '../../../../../shared/models/individual/basic-individual-data';

@Component({
  selector: 'app-results-header',
  templateUrl: './results-header.component.html',
  styleUrls: ['./results-header.component.scss']
})
export class ResultsHeaderComponent implements OnInit, OnDestroy {

  public individualsFoundCount: number;

  private subscription: Subscription;

  constructor(private filterService: SearchFilterService) { }

  ngOnInit() {
    this.individualsFoundCount = this.filterService.getFilteredIndividuals() ?
      this.filterService.getFilteredIndividuals().length : 0 ;
    this.subscription = this.filterService.filteredIndividualsObservable.subscribe((individuals: BasicIndividualData[]) => {
      this.individualsFoundCount = individuals.length;
    });
  }

  ngOnDestroy() {
    this.subscription.unsubscribe();
  }

}
