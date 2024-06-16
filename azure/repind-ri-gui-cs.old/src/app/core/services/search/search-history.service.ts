import { Injectable } from '@angular/core';
import { AbstractSearchRequest } from '../../../shared/models/requests/search/abstract-search-request';
import { Individual } from '../../../shared/models/individual/individual';

@Injectable({
  providedIn: 'root'
})
export class SearchHistoryService {

  private lastSearchRequest = null;
  private lastIndividualsFound = [];

  constructor() { }

  // This method returns the last search request done
  public getLastSearchRequest(): AbstractSearchRequest {
    return this.lastSearchRequest;
  }

  // This method returns the last individuals found in an array
  public getLastIndividualsFound(): Individual[] {
    return this.lastIndividualsFound;
  }

  /**
   * This method saves the last search request in parameter in a private attribute.
   * @param request an `AbstractSearchRequest` object
   */
  public saveLastSearchRequest(request: AbstractSearchRequest): void {
    this.lastSearchRequest = request;
  }

  /**
   * This method saves the last individual found given in parameter in a private attribute.
   * @param individual an `Individual` object
   */
  public saveIndividualFound(individual: Individual): void {
    const hasAlreadyStoredGIN = this.lastIndividualsFound.filter((data: Individual) => data.gin === individual.gin).length >= 1;
    if (!hasAlreadyStoredGIN) {
      this.lastIndividualsFound.unshift(individual);
    }
  }

}
