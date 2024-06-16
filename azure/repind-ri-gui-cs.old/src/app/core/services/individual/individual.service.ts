import { CRUDOperation } from './../../../shared/models/crud/CRUDOperations.enum';
import { CrudService } from './../crud/crud.service';
import { HttpErrorResponse, HttpHeaders, HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { SearchApiService } from '../../http/search/search-api.service';
import { Individual } from '../../../shared/models/individual/individual';
import { CommonService } from '../common.service';
import { IndividualRequest } from '../../../shared/models/requests/w000442/IndividualRequest';
import { CRUDAction } from '../../../shared/models/crud/CRUDAction';
import { ResourceType } from '../../../shared/models/resources/resource-type';

@Injectable({
  providedIn: 'root'
})
export class IndividualService {

  public hasToBeReloaded = false;

  private individual: Individual;

  private httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json'
    })
  };

  constructor(private http: HttpClient, private common: CommonService, private crudService: CrudService,
    private searchApiService: SearchApiService) { }

  public getIndividual(): Individual {
    return this.individual;
  }

  public save(individual: Individual): void {
    this.individual = individual;
  }

  public reloadIndividualData(): Promise<Individual> {
    const gin = this.individual.gin;
    return new Promise((resolve, reject) => {
      this.searchApiService.callGetIndividualDetails(gin)
        .toPromise()
        .then((data: Individual) => {
          this.individual = data;
          this.common.showMessage('RELOAD-SUCCESS');
          resolve(data);
        })
        .catch((err: HttpErrorResponse) => reject(err));
    });
  }

  public updateIndividual(request: IndividualRequest): Promise<boolean> {
    console.table(request)
    const url = this.common.getUrl() + 'individual';
    const promise = new Promise<boolean>((resolve) => {
      this.http.put(url, request.json, this.httpOptions)
        .toPromise()
        .then(() => {
          this.crudService.addCRUDAction(new CRUDAction(CRUDOperation.Update, ResourceType.Individual));
          this.common.showMessage('UPDATE-SUCCESS-MSG');
          resolve(true);
        })
        .catch((err: HttpErrorResponse) => {
          resolve(false);
          this.common.handleError(err);
          throw err;
        });
    });
    return promise;
  }

}
