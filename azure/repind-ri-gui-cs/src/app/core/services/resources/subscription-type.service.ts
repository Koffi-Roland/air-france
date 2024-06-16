import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { CommonService } from '../common.service';
import { Observable } from 'rxjs';
import { ReferenceDataType } from '../../../shared/models/references/ReferenceDataType.enum';

@Injectable({
  providedIn: 'root'
})
export class SubscriptionTypeService {

  private httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json'
    })
  };

  constructor(private http: HttpClient,private commonService: CommonService) { }

  public getAll():Observable<any> 
  {
    const url = this.commonService.getUrl() +'ref/table/'+ReferenceDataType.COMM_PREF_TYPE;
    return this.http.get(url,this.httpOptions);
  }
}
