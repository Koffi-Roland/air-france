import { SearchHistoryService } from './../services/search/search-history.service';
import { Injectable } from '@angular/core';
import { Resolve, Router } from '@angular/router';
import { SearchService } from '../services/search/search.service';
import { SearchFilterService } from '../services/filters/search-filter.service';
import { BasicIndividualData } from '../../shared/models/individual/basic-individual-data';

@Injectable({ providedIn: 'root' })
export class ResultsResolver implements Resolve<any> {

    constructor (private searchService: SearchService, private searchHistoryService: SearchHistoryService,
        private filterService: SearchFilterService, private router: Router) {}

    resolve(): Promise<BasicIndividualData[]> | BasicIndividualData[] {
        const lastSearchRequest = this.searchHistoryService.getLastSearchRequest();
        const basicIndividualData = this.searchService.getBasicIndividualData();
        if (!(basicIndividualData || lastSearchRequest)) { this.router.navigate(['']); }
        this.filterService.initSearch(basicIndividualData);
        return basicIndividualData;
    }
}
