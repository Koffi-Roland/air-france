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

const API_URL = environment.apiUrl + 'communicationtypes/';

@Injectable({
  providedIn: 'root'
})
export class CommunicationTypeService {
  private _communicationTypes;

  constructor(private http: HttpClient) {}

  get communicationTypes() {
    return this._communicationTypes;
  }

  getCommunicationTypes(isForceLoad: boolean = false) {
    if (this._communicationTypes && !isForceLoad) {
      return Promise.resolve(this._communicationTypes);
    } else {
      const uri = API_URL;
      return this
        .http
        .get(uri)
        .pipe(map(json => json))
        .toPromise()
        .then(res => {
          this._communicationTypes = res;
          return res;
        }).catch(err => {
          return (err);
        });
      }
  }

  getCommunicationType(id: string) {
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

  postCommunicationType(data) {
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

  updateCommunicationType(id: string, data) {
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

  deleteCommunicationType(id: string) {
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
