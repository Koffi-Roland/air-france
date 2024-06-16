import { CommonService } from '../common.service';
import { SearchHistoryService } from './search-history.service';
import { HttpErrorResponse } from '@angular/common/http';
import { SearchIndividualResponse } from '../../../shared/models/responses/search-individual-response';
import { Individual } from '../../../shared/models/individual/individual';
import { SearchApiService } from '../../http/search/search-api.service';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { GinSearchRequest } from '../../../shared/models/requests/search/gin-search-request';
import { AbstractSearchRequest } from '../../../shared/models/requests/search/abstract-search-request';
import { SearchType } from '../../../shared/models/requests/search/search-type.enum';
import { BasicIndividualData } from '../../../shared/models/individual/basic-individual-data';
import { IndividualService } from '../individual/individual.service';

@Injectable({
  providedIn: 'root'
})
export class SearchService {

  private basicIndividualData: BasicIndividualData[];
  private hasFoundMultipleIndividuals: boolean;

  constructor(private searchApiService: SearchApiService, private searchHistoryService: SearchHistoryService,
    private router: Router, private common: CommonService, private individualService: IndividualService) { }

  /**
   * This method takes a `request` in parameter and call the appropriate search method. Also, the
   * second parameter `forceSearch` is a boolean that, if set to `true` will force the search and so not redirect
   * to the results view if no results found.
   * @param request an `AbstractSearchRequest` object
   * @param forceSearch a `boolean`
   */
  public searchIndividuals(request: AbstractSearchRequest, forceSearch: boolean): void {
    this.searchHistoryService.saveLastSearchRequest(request);
    if (request.type === SearchType.GIN_OR_CIN) {
      this.search((request as GinSearchRequest).gin, forceSearch);
    } else {
      this.searchByMulticriteria(request, forceSearch);
    }
  }

  // Method that returns an array of basic individual data
  public getBasicIndividualData(): BasicIndividualData[] {
    return this.basicIndividualData;
  }

  // Method that returns wether multiple individuals have been found or not
  public getHasFoundMultipleIndividuals(): boolean {
    return this.hasFoundMultipleIndividuals;
  }

  /**
   * Search an individual by `gin` and the boolean `forceSearch` must be set to `true` if we want
   * to force the search.
   * @param gin a `string`
   * @param forceSearch a `boolean`
   */
  public search(gin: string, forceSearch: boolean): void {
    const ginToSend = (gin.length === 12) ? gin : '00' + gin;
    this.isExistMultiple(gin).then((isExistMultiple: boolean) => {
      if (!isExistMultiple) {
        this.searchApiService.callGetIndividualDetails(ginToSend)
          .toPromise()
          .then((data: Individual) => {
            this.individualService.save(data);
            this.searchHistoryService.saveIndividualFound(data);
            if (!forceSearch) { this.hasFoundMultipleIndividuals = false; }
            this.router.navigate(['individuals/individual/dashboard']);
          })
          .catch((err: HttpErrorResponse) => {
            this.common.handleError(err);
          });
      }
    });
  }

  /**
   * This method takes a `request` and do the appropriate API call according to the type of the
   * request given. The `boolean` is here to tell wether the search has to be forced or not.
   * @param request an `AbstractSearchRequest` object
   * @param forceSearch a `boolean`
   */
  private searchByMulticriteria(request: AbstractSearchRequest, forceSearch: boolean): void {
    this.searchApiService.callSearchByMulticriteriaAPIEndpoint(request)
      .toPromise()
      .then((data: SearchIndividualResponse) => this.analyseSearchResponse(data, forceSearch))
      .catch((err: HttpErrorResponse) => this.common.handleError(err));
  }

  /**
   * This methods will call the API endpoint to know if there exists individuals with same contract
   * number and GIN number. It returns a `Promise<boolean>` that depends on the result of the API call.
   * @param gin a `string`
   */
  private isExistMultiple(gin: string): Promise<boolean> {
    return new Promise((resolve) => {
      this.searchApiService.callExistMultipleAPIEndpoint(gin).toPromise().then((data: any) => {
        const isExistMultiple = data > 0;
        resolve(isExistMultiple);
      })
        .catch((err: HttpErrorResponse) => this.common.handleError(err));
    });
  }

  /**
   * This method analyze the result from a call to `SearchIndividualByMulticriteria` web service. It sets
   * the attribute `basicIndividualData` with the data provided by the WS. Then use the router to navigate
   * to the route to display all the results.
   * @param response a `SearchIndividualResponse` object
   * @param forceSearch a `boolean`
   */
  private analyseSearchResponse(response: SearchIndividualResponse, forceSearch: boolean): void {
    if (response.count === 1) {
      const gin = response.data[0].gin;
      this.search(gin, forceSearch);
    } else {
      this.basicIndividualData = response.data;
      this.hasFoundMultipleIndividuals = true;
      this.router.navigate(['/individuals/search/results/grid']);
    }
  }

}
