import { Component, OnInit, Input } from '@angular/core';
import { SearchService } from '../../../../../core/services/search/search.service';
import { BasicIndividualData } from '../../../../../shared/models/individual/basic-individual-data';
import { CommonService } from '../../../../../core/services/common.service';

@Component({
  selector: 'app-search-results-item',
  templateUrl: './search-results-item.component.html',
  styleUrls: ['./search-results-item.component.scss']
})
export class SearchResultsItemComponent implements OnInit {

  @Input() individual: BasicIndividualData;
  @Input() last: boolean;

  public isSelected = false;

  constructor(private searchService: SearchService, public common: CommonService) { }

  ngOnInit() {
  }

  public search(individual: BasicIndividualData): void {
    this.isSelected = true;
    this.searchService.search(individual.gin, true);
  }

}
