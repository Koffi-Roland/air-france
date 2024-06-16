import {
  Injectable
} from '@angular/core';
import {
  environment
} from '../../../environments/environment.prod';
import {
  HttpClient,
  HttpErrorResponse
} from '@angular/common/http';
import {
  map
} from 'rxjs/operators';

const API_URL = environment.apiUrl + 'communicationpreferencesdgt/';

@Injectable({
  providedIn: 'root'
})
export class CommunicationPreferencesDgtService {

  private communicationsPreferenceDgt;
  constructor(private http: HttpClient) { }

  getCommunicationPreferencesDgts(isForceLoad: boolean = false) {
    if (this.communicationsPreferenceDgt && !isForceLoad) {
      return Promise.resolve(this.communicationsPreferenceDgt);
    } else {
      const uri = API_URL;
      return this
        .http
        .get(uri)
        .pipe(map(json => json))
        .toPromise()
        .then(res => {
          this.communicationsPreferenceDgt = res;
          return res;
        }).catch(err => {
          return (err);
        });
    }
  }

  getCommunicationPreferencesDgt(id: string) {
    const uri = API_URL + id;
    return this
      .http
      .get(uri)
      .pipe(map(json => json))
      .toPromise()
      .then(res => {
        return res;
      }).catch(err => {
        return (err);
      });
  }

  postCommunicationPreferencesDgt(data) {
    const uri = API_URL;
    return this
      .http
      .post(uri, data)
      .pipe(map(json => json))
      .toPromise()
      .then(res => {
        return res;
      }).catch(err => {
        return (err);
      });
  }

  updateCommunicationPreferencesDgt(id: string, data) {
    const uri = API_URL + id;
    return this
      .http
      .put(uri, data)
      .pipe(map(json => json))
      .toPromise()
      .then(res => {
        return res;
      }).catch(err => {
        return (err);
      });
  }

  deleteCommunicationPreferencesDgt(id: string) {
    const uri = API_URL + id;
    return this
      .http
      .delete(uri)
      .pipe(map(json => json))
      .toPromise()
      .then(res => {
        return res;
      }).catch(err => {
        return (err);
      });
  }

}
