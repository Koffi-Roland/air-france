import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { SearchHistoryService } from '../../../../core/services/search/search-history.service';
import { SearchType } from '../../../../shared/models/requests/search/search-type.enum';
import { BasicIndividualData } from '../../../../shared/models/individual/basic-individual-data';

@Component({
  selector: 'app-results',
  templateUrl: './results.component.html',
  styleUrls: ['./results.component.scss']
})
export class ResultsComponent implements OnInit {

  public individuals: BasicIndividualData[];

  constructor(private route: ActivatedRoute, private router: Router, private searchHistoryService: SearchHistoryService) { }

  ngOnInit() {
    this.individuals = this.route.snapshot.data['individuals'];
  }

  public backToSearch(): void {
    const lastSearchType = this.searchHistoryService.getLastSearchRequest().type;
    switch (lastSearchType) {
      case SearchType.GIN_OR_CIN:
        this.router.navigate(['/individuals/search/gin']);
        break;
      case SearchType.TELECOM:
        this.router.navigate(['/individuals/search/telecom']);
        break;
      case SearchType.EMAIL:
        this.router.navigate(['/individuals/search/email']);
        break;
      case SearchType.MULTICRITERIA:
        this.router.navigate(['/individuals/search/multicriteria']);
        break;
    }
  }

}
