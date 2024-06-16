import { FilterGroup } from './../../../../../shared/models/filters/filter-group';
import { SearchFilterService } from '../../../../../core/services/filters/search-filter.service';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-search-filters-card',
  templateUrl: './search-filters-card.component.html',
  styleUrls: ['./search-filters-card.component.scss']
})
export class SearchFiltersCardComponent implements OnInit {

  public filters: FilterGroup[];

  constructor(private searchFilterService: SearchFilterService) { }

  ngOnInit() {
    this.filters = this.searchFilterService.getFilterGroups();
  }

  public resetFilters(): void {
    this.searchFilterService.resetFilters();
  }

}
