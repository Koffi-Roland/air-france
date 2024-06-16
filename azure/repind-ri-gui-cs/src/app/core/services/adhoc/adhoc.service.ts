import {Injectable} from '@angular/core';
import {HttpHeaders, HttpClient} from '@angular/common/http';
import {CommonService} from '../common.service';
import {Observable} from 'rxjs';
import {AdhocRequest} from '../../../shared/models/requests/adhoc/adhoc-request';
import {OptionItem} from '../../../shared/models/contents/option-item';
import {CountriesService} from '../resources/countries.service';
import {LanguagesService} from '../resources/languages.service';

@Injectable({
  providedIn: 'root'
})
export class AdhocService {

  private httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json'
    })
  };

  constructor(private http: HttpClient,
              private commonService: CommonService) {
  }

  /**
   * Function used to validate upload data
   *
   * @param request data needed to be validated
   * @param airlineCode  airline code: AF|KL
   * @returns validation data
   */
  public validation(request: AdhocRequest, airlineCode: any): Observable<any> {
    const url = this.commonService.getUrl() + 'adhoc/validation/' + airlineCode;
    return this.http.post<any>(url, request, this.httpOptions);
  }

  /**
   * Function used to  upload data
   *
   * @param request data to be uploaded
   * @param airlineCode  airline code: AF|KL
   * @returns Boolean True | False
   */
  public upload(request: any, airlineCode: any): Observable<any> {
    const url = this.commonService.getUrl() + 'adhoc/upload/' + airlineCode;
    return this.http.post<any>(url, request, this.httpOptions);
  }

  public getReferencesAdhoc() {
    return Promise.all(
      [
        this.getDomains(),
        this.getGroupTypes(),
        this.getCivilities(),
        this.getSubscriptionTypes(),
        this.getStatus()
      ]
    );
  }


  private getDomains() {
    return Promise.resolve(
      [new OptionItem('S', 'S', 'S')]
    );
  }

  private getGroupTypes() {
    return Promise.resolve(
      [new OptionItem('N', 'N', 'N')]
    );
  }


  private getCivilities() {
    return Promise.resolve(
      [
        new OptionItem('MR', 'MR', 'MR'),
        new OptionItem('MS', 'MS', 'MS'),
        new OptionItem('M.', 'M.', 'M.'),
        new OptionItem('MX', 'MX', 'MX')
      ]
    );
  }

  private getSubscriptionTypes() {
    return Promise.resolve(
      [
        new OptionItem('KL', 'KL', 'KL'),
        new OptionItem('KL_PART', 'KL_PART', 'KL_PART'),
        new OptionItem('AF', 'AF', 'AF')
      ]
    );
  }

  private getStatus() {
    return Promise.resolve(
      [
        new OptionItem('Y', 'Y', 'Y'),
        new OptionItem('N', 'N', 'N')
      ]
    );
  }

}

