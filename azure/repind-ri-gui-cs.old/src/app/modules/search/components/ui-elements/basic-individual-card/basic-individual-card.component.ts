import { Component, OnInit, Input } from '@angular/core';
import { SearchService } from '../../../../../core/services/search/search.service';
import { BasicIndividualData } from '../../../../../shared/models/individual/basic-individual-data';
import { CommonService } from '../../../../../core/services/common.service';

@Component({
  selector: 'app-basic-individual-card',
  templateUrl: './basic-individual-card.component.html',
  styleUrls: ['./basic-individual-card.component.scss']
})
export class BasicIndividualCardComponent implements OnInit {

  @Input() individual: BasicIndividualData;

  constructor(private searchService: SearchService, public common: CommonService) { }

  ngOnInit() {
  }

  public search(): void {
    this.searchService.search(this.individual.gin, true);
  }

}
