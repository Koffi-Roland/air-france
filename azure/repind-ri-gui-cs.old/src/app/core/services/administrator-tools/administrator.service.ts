import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { map } from 'rxjs/operators';
import { CommonService } from './../common.service';
import { CrudService } from '../crud/crud.service';
import { CRUDAction } from '../../../shared/models/crud/CRUDAction';
import { CRUDOperation } from '../../../shared/models/crud/CRUDOperations.enum';
import { ResourceType } from '../../../shared/models/resources/resource-type';
import { IndividualService } from '../individual/individual.service';
import { Individual } from '../../../shared/models/individual/individual';
import { reject } from 'q';

@Injectable({
  providedIn: 'root'
})
export class AdministratorService {

constructor(private http: HttpClient,  private common: CommonService, private individualService: IndividualService,
  private crudService: CrudService) { }

private httpOptions = {
  headers: new HttpHeaders({
    'Content-Type': 'application/json'
  })
};

public unmergeIndividual(gin: String): Promise<boolean> {
  const url = this.common.getUrl() + 'administrator-tools/unmerge/'+ gin;
  const promise = new Promise<boolean>((resolve) => {
    this.http.put(url, null, this.httpOptions)
      .toPromise()
      .then(() => {
        this.crudService.addCRUDAction(new CRUDAction(CRUDOperation.Update, ResourceType.Individual));
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

public reactivateAccountIndividual(gin: String): Promise<boolean> {
  const url = this.common.getUrl() + 'administrator-tools/reactivate/'+ gin;
  const promise = new Promise<boolean>((resolve) => {
    this.http.put(url, null, this.httpOptions)
      .toPromise()
      .then(() => {
        this.crudService.addCRUDAction(new CRUDAction(CRUDOperation.Update, ResourceType.Individual));
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
