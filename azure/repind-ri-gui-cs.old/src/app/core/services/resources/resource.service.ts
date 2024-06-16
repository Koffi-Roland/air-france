import { CRUDOperation } from './../../../shared/models/crud/CRUDOperations.enum';
import { CRUDAction } from './../../../shared/models/crud/CRUDAction';
import { CrudService } from './../crud/crud.service';
import { HttpClient, HttpHeaders, HttpErrorResponse } from '@angular/common/http';
import { map } from 'rxjs/operators';
import { CommonService } from '../common.service';
import { Injector } from '@angular/core';
import { Resource } from '../../../shared/models/resources/resource';
import { Serializer } from '../../../shared/models/serializer/serializer';
import { IndividualService } from '../individual/individual.service';

/**
 * Abstract class for a resource service.
 * The idea is to have implementation of CRUD operations only here.
 * Indeed, because we use Resource object, they should all have the same
 * behavior and the Create, Read, Update and Delete operations doesn't
 * change that much... only the endpoint might change.
 */
export class ResourceService<T extends Resource> {

  private httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json'
    })
  };

  private common: CommonService;
  private http: HttpClient;
  private url: string;
  private crudService: CrudService;
  private individualService: IndividualService;

  constructor(private listEndpoint: string, private updateEndpoint: string,
    private deleteEndpoint: string, private createEndpoint: string, private serializer: Serializer, public inj: Injector) {
    this.setAllServices();
    this.url = this.common.getUrl();
  }

  public list(): any {
    const url = `${this.url}${this.listEndpoint}`;
    const i = this.individualService.getIndividual();
    return this.http
      .get(url.replace(':gin', i.gin))
      .pipe(map((res: any) => this.convertData(res.data), err => this.common.handleError(err)))
      .toPromise()
      .then((data: T[]) => {
        return data;
      })
      .catch((err: any) => {
        this.common.handleError(err);
      });
  }

  public update(item: T): any {
    const url = `${this.url}${this.updateEndpoint}`;
    const i = this.individualService.getIndividual();
    return this.http.put<T>(url.replace(':gin', i.gin), this.serializer.toJson(item), this.httpOptions)
      .toPromise()
      .then((data: any) => {
        if (data.success) {
          this.individualService.hasToBeReloaded = true;
          const action = new CRUDAction(CRUDOperation.Update, item.resourceType, data);
          this.crudService.addCRUDAction(action);
        }
        return data;
      })
      .catch((err: HttpErrorResponse) => {
        this.common.handleError(err);
      });
  }

  public delete(item: T): any {

    const i = this.individualService.getIndividual();

    const url = `${this.url}${this.deleteEndpoint}`.replace(':gin', i.gin).replace(':id', item.id);

    return this.http.delete<T>(url)
      .toPromise()
      .then((data: any) => {
        this.individualService.hasToBeReloaded = true;
        const action = new CRUDAction(CRUDOperation.Delete, item.resourceType, data);
        this.crudService.addCRUDAction(action);
        return data;
      })
      .catch((err: HttpErrorResponse) => {
        this.common.handleError(err);
      });
  }

  public create(item: T): any {

    const i = this.individualService.getIndividual();

    const url = `${this.url}${this.createEndpoint}`.replace(':gin', i.gin);

    return this.http.post<T>(url, this.serializer.toJson(item), this.httpOptions)
      .toPromise()
      .then((data: any) => {
        if (data) {
          this.individualService.hasToBeReloaded = true;
          const action = new CRUDAction(CRUDOperation.Create, item.resourceType, data);
          this.crudService.addCRUDAction(action);
        }
        return data;
      })
      .catch((err: HttpErrorResponse) => {
        this.common.handleError(err);
      });
  }

  private convertData(data: any): T[] {
    if (!data) { return null; }
    return this.serializer.fromJsonToArrayObject(data);
  }

  private setAllServices(): void {
    this.common = this.inj.get(CommonService);
    this.http = this.inj.get(HttpClient);
    this.url = this.common.getUrl();
    this.crudService = this.inj.get(CrudService);
    this.individualService = this.inj.get(IndividualService);
  }
}
