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

const API_URL = environment.apiUrl + 'domains/';

@Injectable({
  providedIn: 'root'
})
export class DomainService {

  private domain;
  constructor(private http: HttpClient) {}

  getDomains(isForceLoad: boolean = false) {
    if (this.domain && !isForceLoad) {
      return Promise.resolve(this.domain);
    } else {
      const uri = API_URL;
      return this
        .http
        .get(uri)
        .pipe(map(json => json))
        .toPromise()
        .then(res => {
          this.domain = res;
          return res;
        }).catch(err => {
          return (err);
        });
    }
  }

  getDomain(id: string) {
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

  postDomain(data) {
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

  updateDomain(id: string, data) {
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

  deleteDomain(id: string) {
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
