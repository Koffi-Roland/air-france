import { SearchFilterService } from '../../../../../../core/services/filters/search-filter.service';
import { FilterGroup } from './../../../../../../shared/models/filters/filter-group';
import { Component, OnInit, Input, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-filter-expansion-panel',
  templateUrl: './filter-expansion-panel.component.html',
  styleUrls: ['./filter-expansion-panel.component.scss']
})
export class FilterExpansionPanelComponent implements OnInit, OnDestroy {

  @Input() filterGroup: FilterGroup;

  public selectedValue: any;

  private filtersSubscription: Subscription;
  private resetFiltersSubscription: Subscription;

  constructor(private filterService: SearchFilterService) { }

  ngOnInit() {

    this.filtersSubscription = this.filterService.filtersGroupObservable.subscribe(() => {
      this.filterGroup = this.filterService.getFilterGroup(this.filterGroup.name);
    });

    this.resetFiltersSubscription = this.filterService.filtersResetedObservable.subscribe(() => {
      this.selectedValue = '';
    });

  }

  ngOnDestroy() {
    this.filtersSubscription.unsubscribe();
    this.resetFiltersSubscription.unsubscribe();
  }

  public filter(): void {
    this.filterService.filterIndividuals(this.filterGroup, this.selectedValue);
  }

}
