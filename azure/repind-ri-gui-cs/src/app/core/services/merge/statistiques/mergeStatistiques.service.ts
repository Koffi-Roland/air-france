import { Injectable } from '@angular/core';
import { HttpClient, HttpParams, HttpHeaders } from '@angular/common/http';
import { map } from 'rxjs/operators';
import * as moment from 'moment';
import { CommonService } from '../../common.service';
import { IndividualService } from '../../individual/individual.service';

@Injectable({
  providedIn: 'root'
})


export class MergeStatistiquesService {


  constructor(private http: HttpClient, private common: CommonService, private individualService: IndividualService) { }

  private nextPage: string;
  private datas;
  private export;


  // Get tracking data using filter
  public getFilteredData(): any {
    return this.getMergeStatistiquePromise(this.constructURL()).then(data => {
      return data;
    });
  }

  // Get tracking data using filter
  public getExportedData(): any {
    return this.constructURL() + '/export';
  }

  // Get tracking data using filter
  public getGetExportedData(): any {
    return this.getMergeExportStatistiquePromise(this.constructURL() + '/export').subscribe((response: any) => {
      const blob = new Blob([response.body], { type: 'text/csv' });
      const element = document.createElement('a');
      element.href = URL.createObjectURL(blob);
      element.download = response.headers.get('Content-Disposition').split(';')[1].split('=')[1];
      document.body.appendChild(element);
      element.click();
      element.remove();
    });
  }

  /* ----- Specific method for internal service ----- */
  /* Contruct URL with parameter */
  private constructURL(): string {
    return this.common.getUrl() + 'merge/statistiques';
  }

  /*Call HTTP*/
  private getMergeExportStatistiquePromise(url: string): any {
    return this.http.get(url, { observe: 'response', responseType: 'blob' }).pipe(
      map((response: any) => {
        return response;
      })
    );
  }

  /*Call HTTP*/
  private getMergeStatistiquePromise(url: string): any {
    return this.http.get(url)
      .pipe(map(json => json))
      .toPromise()
      .then(res => {
        return res;
      })
      .catch(err => {
        this.common.handleError(err);
        throw err;
      });
  }

}
