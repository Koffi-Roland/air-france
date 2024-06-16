import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { CommonService } from '../../services/common.service';
import { map } from 'rxjs/operators';
import { SearchIndividualResponse } from '../../../shared/models/responses/search-individual-response';
import { AbstractSearchRequest } from '../../../shared/models/requests/search/abstract-search-request';
import { Converter } from '../../../shared/models/serializer/converter';

@Injectable({
  providedIn: 'root'
})
export class SearchApiService {

  private httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json'
    })
  };

  constructor(private http: HttpClient, private common: CommonService) { }

  public callExistMultipleAPIEndpoint(gin: string): Observable<any> {
    const url = this.common.getUrl() + 'individual/existMultiple/' + gin;
    return this.http.get(url);
  }

  public callMultipleByGin(gin: string): Observable<any> {
    const url = this.common.getUrl() + 'individual/multipleByGinOrContract/' + gin;
    return this.http.get(url);
  }

  public callGetIndividualDetails(gin: string): Observable<any> {
    const url = this.common.getUrl() + 'individual/' + gin + '/details';
    return this.http.get(url).pipe(map((data: any) => Converter.convertToIndividual(data.individu, data.resume)));
  }

  public callSearchByMulticriteriaAPIEndpoint(request: AbstractSearchRequest): Observable<SearchIndividualResponse> {
    const url = this.common.getUrl() + request.url;
    return this.http.post<SearchIndividualResponse>(url, request.toJSON, this.httpOptions);
  }

}
