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

const API_URL = environment.apiUrl + 'PcsNonContractScore/';


@Injectable({
  providedIn: 'root'
})
export class PcsNonContractScoreService {

  constructor(private http: HttpClient) { }

  // ----------- PCS Contract Score -----------
  getPcsNonContractScore() {
    return this
      .http
      .get(API_URL)
      .pipe(map(json => json))
      .toPromise()
      .then(res => {
        return res;
      }).catch(err => {
        return (err);
      });
  }

  postPcsNonContractScore(data) {
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

  updatePcsNonContractScore(code: string, data) {
    const uri = API_URL + code;
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

  deletePcsNonContractScore(code: string) {
    const uri = API_URL + code;
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
