import { Subscription } from 'rxjs';
import { Component, OnInit, OnDestroy } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { SearchService } from '../../../../../core/services/search/search.service';
import { SearchFilterService } from '../../../../../core/services/filters/search-filter.service';
import { BasicIndividualData } from '../../../../../shared/models/individual/basic-individual-data';
import { CommonService } from '../../../../../core/services/common.service';

@Component({
  selector: 'app-search-results-array',
  templateUrl: './search-results-array.component.html',
  styleUrls: ['./search-results-array.component.scss']
})
export class SearchResultsArrayComponent implements OnInit, OnDestroy {

  public individuals: BasicIndividualData[];
  public dataSource: any;
  public displayedColumns: string[];

  private subscription: Subscription;

  constructor(private searchService: SearchService, private filterService: SearchFilterService, public common: CommonService) { }

  ngOnInit() {
    this.individuals = this.filterService.getFilteredIndividuals();
    this.dataSource = new MatTableDataSource(this.individuals);
    this.displayedColumns = this.filterService.getArrayLibelle();
    this.subscription = this.filterService.filteredIndividualsObservable.subscribe((individuals: BasicIndividualData[]) => {
      this.individuals = individuals;
      this.dataSource = new MatTableDataSource(this.individuals);
    });
  }

  ngOnDestroy() {
    this.subscription.unsubscribe();
  }

  public search(individual: BasicIndividualData): void {
    this.searchService.search(individual.gin, true);
  }

}
