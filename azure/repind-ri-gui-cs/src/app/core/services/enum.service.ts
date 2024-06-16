import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { CommonService } from './common.service';
import { map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class EnumService {

constructor(private http: HttpClient,  private common: CommonService) { }

public static REF_TRACKING;
private static TRACKING_ICONS;

static getIcon(type): any {
  for (let i = 0; i < this.TRACKING_ICONS.length; i++) {
    if (this.TRACKING_ICONS[i].valueNormalized === type) {
      return this.TRACKING_ICONS[i].icon;
    }
  }
  return '';
}

  public loadRefTracking(): any {
    if (!EnumService.REF_TRACKING) {
      return this.getRefTracking().then(data => {
        EnumService.REF_TRACKING = this.transformRefTrackingData(data);
        EnumService.TRACKING_ICONS = EnumService.REF_TRACKING.filter(x => x.code === 'TYPE')[0].valuesNormalized;
      });
    }
    return Promise.resolve();
  }

  private getRefTracking(): any {
    return this.http.get(this.common.getUrl() + 'reftracking')
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

  private transformRefTrackingData(data: any): any {
    const result = [];
    let u = -1;
    for (let i = 0; i < data.length; i++) {
      const code = data[i].code;
      let isCodeExisting = false;
      for (let y = 0; y < result.length && !isCodeExisting; y++) {
        if (result[y].code === code) {
          isCodeExisting = true;
          u = y;
        }
      }
      if (!isCodeExisting) {
        u++;
        result[u] = {};
        result[u].code = code;
        result[u].valuesNormalized = [];
      }
      result[u].valuesNormalized.push(data[i]);
    }
    return result;
  }

}
