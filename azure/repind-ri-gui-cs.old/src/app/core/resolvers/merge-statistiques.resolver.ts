import { Injectable } from '@angular/core';
import { Resolve, Router } from '@angular/router';
import { CommonService } from '../services/common.service';
import { MergeStatistiquesService } from '../services/merge/statistiques/mergeStatistiques.service';
import { SearchFilterService } from '../services/filters/search-filter.service';
import { BasicIndividualData } from '../../shared/models/individual/basic-individual-data';

@Injectable()
export class MergeStatistiquesResolver implements Resolve<any> {
  constructor(private mergeStatistiquesService: MergeStatistiquesService, private router: Router, private common: CommonService) { }

    resolve(): Promise<any> {
      return this.mergeStatistiquesService.getFilteredData().then(res => {
        return res.data;
      }, err => {
        this.router.navigate(['individuals/home']);
        return null;
      });
  }
}
