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

const API_URL = environment.apiUrl + 'PcsFactor/';


@Injectable({
  providedIn: 'root'
})
export class PcsFactorService {

  constructor(private http: HttpClient) { }

  // ----------- PCS Factor -----------
  getPcsFactor() {
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

  postPcsFactor(data) {
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

  updatePcsFactor(code: string, data) {
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

  deletePcsfactor(code: string) {
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
