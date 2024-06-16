import { Component, OnInit } from '@angular/core';
import { BasicIndividualData } from '../../../shared/models/individual/basic-individual-data';
import { ActivatedRoute, Router } from '@angular/router';
import { SearchHistoryService } from '../../../core/services/search/search-history.service';
import { SearchFilterService } from '../../../core/services/filters/search-filter.service';
import { MergeStatistiquesService } from '../../../core/services/merge/statistiques/mergeStatistiques.service';

@Component({
  selector: 'app-merge-statistique',
  templateUrl: './merge-statistique.component.html',
  styleUrls: ['./merge-statistique.component.scss']
})
export class MergeStatistiqueComponent implements OnInit {

  public individuals: BasicIndividualData[];
  private export: any;

  constructor(private route: ActivatedRoute, private router: Router,
    private searchHistoryService: SearchHistoryService, private filterService: SearchFilterService,
    private mergeStatistiquesService: MergeStatistiquesService) { }

  ngOnInit() {
    this.individuals = this.route.snapshot.data['statistiques'];
    this.filterService.initMergeStatistiques(this.individuals);
    this.export = this.mergeStatistiquesService.getExportedData();
  }


  getExport() {
    this.mergeStatistiquesService.getGetExportedData();
  }

  public backToSearch(): void {
    this.router.navigate(['/home']);
    }


}
