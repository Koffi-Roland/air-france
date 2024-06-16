import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { CommonService } from '../../services/common.service';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ReferenceApiService {

  constructor(private http: HttpClient, private common: CommonService) { }

  public callGetRefCountryCodes(): Observable<any> {
    const url = this.common.getUrl() + 'ref/countries';
    return this.http.get<any>(url);
  }

}
