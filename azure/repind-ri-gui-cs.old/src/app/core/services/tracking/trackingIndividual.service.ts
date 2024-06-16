import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { CommonService } from '../common.service';
import { map } from 'rxjs/operators';
import * as moment from 'moment';
import { SearchService } from '../search/search.service';
import { IndividualService } from '../individual/individual.service';
import { Individual } from '../../../shared/models/individual/individual';

@Injectable({
  providedIn: 'root'
})
export class TrackingIndividualService {

  constructor(private http: HttpClient, private common: CommonService, private individualService: IndividualService) { }

  private nextPage: string;
  private datas;
  private export;

  // Retrieve tracking data without filter
  public getTrackingByGin(gin: string, page: number): any {
    this.datas = null;
    this.nextPage = null;
    return this.getTrackingPromise(this.constructURL(gin, page, null, null)).then(data => {
      this.datas = this.transformData(data);
      return this.datas;
    });
  }

  // Get next data return by backend
  public getNextTracking(): any {
    return this.getTrackingPromise(this.nextPage).then(data => {
      this.datas = this.aggregadeData(this.datas, this.transformData(data));
      return this.datas;
    });
  }

  // Get tracking data using filter
  public getFilteredData(date: Date, filter: string): any {
    this.datas = null;
    this.nextPage = null;
    const i = this.individualService.getIndividual();
    return this.getTrackingPromise(this.constructURL(i.gin, 0, date, filter)).then(data => {
      this.datas = this.transformData(data);
      return this.datas;
    });
  }

  // Return if exist nextPage
  public isNextPage(): boolean {
    return !(!this.nextPage);
  }

  // Return Export URL
  public getExport() {
    return this.export;
  }

  /* ----- Specific method for internal service ----- */
  /* Contruct URL with parameter */
  private constructURL(gin: string, page: number, date: Date, filter: string): string {
    let url = this.common.getUrl() + 'tracking/' + gin + '/?page=' + page;
    if (date) { url += '&maxDate=' + date.valueOf(); }
    if (filter) { url += '&filter=' + filter; }
    return url;
  }

  /* Transform data for view */
  private transformData(data: any) {
    this.saveNextPageTracking(data);
    this.saveExportTracking(data);

    data = data.content;
    const result = [];
    let u = -1;
    for (let i = 0; i < data.length; i++) {
      const date = moment(data[i].dateModification).toDate();
      if (u === -1 || !this.common.dateEquals(date, result[u].date)) {
        u++;
        result[u] = {};
        result[u].date = date;
        result[u].events = [];
      }
      result[u].events.push(data[i]);
    }
    return result;
  }

  // Get next data return by backend
  private saveExportTracking(data): any {
    this.export = this.findSpecificLink(data, 'self');
    this.export = this.export.replace('/tracking/', '/tracking/export/');
  }

  private saveNextPageTracking(data) {
    this.nextPage = this.findSpecificLink(data, 'next');
  }

  /* Change port only for local */
  private changeUrlPortForLocal(url: string): string {
    return url.replace('8080', '4200');
  }

  /* Find specific link and return data */
  private findSpecificLink(data: any, link: string) {
    if (data.links.filter(x => x.rel === link)[0]) {
      /* To change for deployed */
      return this.changeUrlPortForLocal(data.links.filter(x => x.rel === link)[0].href);
    }
    return null;
  }

  /* Aggregade new data with previous ones*/
  private aggregadeData(dataBefore, dataAfter): any {
    const lastIndex = dataBefore.length - 1;
    if (this.common.dateEquals(dataBefore[lastIndex].date, dataAfter[0].date)) {
      dataBefore[lastIndex].events = dataBefore[lastIndex].events.concat(dataAfter[0].events);
      dataAfter = dataAfter.slice(1);
    }
    return dataBefore.concat(dataAfter);
  }

  /*Call HTTP*/
  private getTrackingPromise(url: string): any {
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
