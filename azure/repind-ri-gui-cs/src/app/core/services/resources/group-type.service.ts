import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { CommonService } from '../common.service';
import { ReferenceDataType } from '../../../shared/models/references/ReferenceDataType.enum';

@Injectable({
  providedIn: 'root'
})
export class GroupTypeService {

  private httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json'
    })
  };
  constructor(private commonService: CommonService,private http: HttpClient) { }


  public getAll():Observable<any> 
  {
    const url = this.commonService.getUrl() +'ref/table/'+ReferenceDataType.COMM_PREF_GTYPE;
    return this.http.get(url,this.httpOptions);
  }
}
