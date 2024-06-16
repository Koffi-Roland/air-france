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

const API_URL = environment.apiUrl + 'communicationpreferences/';

@Injectable({
  providedIn: 'root'
})
export class CommunicationPreferencesService {

  private _communicationsPreferences;

  private _listDomains;

  private _listTypes;

  private _listGTypes;

  private _listCountryMarkets;

  private _listMedias;


  constructor(private http: HttpClient) {}


  getCommunicationPreferences(isForceLoad: boolean = false) {
    if (this._communicationsPreferences && !isForceLoad) {
      return Promise.resolve(this._communicationsPreferences);
    } else {
      const uri = API_URL;
      return this
        .http
        .get(uri)
        .pipe(map(json => json))
        .toPromise()
        .then(res => {
          this._communicationsPreferences = res;
          return res;
        }).catch(err => {
          return (err);
        });
    }
  }

  getOneCommunicationPreferences(id: string) {
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

  postCommunicationPreferences(data) {
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

  updateCommunicationPreferences(id: string, data) {
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

  deleteCommunicationPreferences(id: string) {
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

  set listDomains(list: any) {
    this._listDomains = list;
  }

  get listDomains() {
    return this._listDomains;
  }

  set listTypes(list: any) {
    this._listTypes = list;
  }

  get listTypes() {
    return this._listTypes;
  }

  set listGTypes(list: any) {
    this._listGTypes = list;
  }

  get listGTypes() {
    return this._listGTypes;
  }

  set listCountryMarkets(list: any) {
    this._listCountryMarkets = list;
  }

  get listCountryMarkets() {
    return this._listCountryMarkets;
  }

  set listMedias(list: any) {
    this._listMedias = list;
  }

  get listMedias() {
    return this._listMedias;
  }

  set listComPref(list: any) {
    this._communicationsPreferences = list;
  }

  get listComPref() {
    return this._communicationsPreferences;
  }
}
