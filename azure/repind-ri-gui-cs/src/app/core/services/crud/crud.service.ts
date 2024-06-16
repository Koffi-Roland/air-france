import { CRUDAction } from './../../../shared/models/crud/CRUDAction';
import { Subject } from 'rxjs';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class CrudService {

  private crudSubject = new Subject<CRUDAction>();
  public crud$ = this.crudSubject.asObservable();

  constructor() { }

  public addCRUDAction(action: CRUDAction): void {
    this.crudSubject.next(action);
  }

}
