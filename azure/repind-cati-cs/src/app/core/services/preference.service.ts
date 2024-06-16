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

const API_URL_PREFERENCE = environment.apiUrl + 'preferenceTypes/';
const API_URL_PREFERENCE_DATA = environment.apiUrl + 'preferenceDataKeys/';
const API_URL_PREFERENCE_KEY_TYPE = environment.apiUrl + 'preferenceKeyTypes/'

@Injectable({
  providedIn: 'root'
})
export class PreferenceService {

  private _preferences;
  private _preferenceDatas;
  private _preferenceKeyType;

  constructor(private http: HttpClient) { }

  get preferences() {
    return this._preferences;
  }

  get preferenceDatas() {
    return this._preferenceDatas;
  }

  get preferenceKeyType() {
    return this._preferenceKeyType;
  }

  // ----------- PREFERENCES -----------
  getPreferences(isForceLoad: boolean = false) {
    if (this._preferences && !isForceLoad) {
      return Promise.resolve(this._preferences);
    } else {
      return this.requestGetPreferences();
    }
  }

  deletePreference(code: string) {
    const uri = API_URL_PREFERENCE + code;
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

  postPreference(data) {
    const uri = API_URL_PREFERENCE;
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

  updatePreference(code: string, data) {
    const uri = API_URL_PREFERENCE + code;
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

  // ----------- PREFERENCE DATAS -----------
  getPreferenceDatas(isForceLoad: boolean = false) {
    if (this._preferenceDatas && !isForceLoad) {
      return Promise.resolve(this._preferenceDatas);
    } else {
      return this.requestGetPreferenceDatas();
    }
  }

  deletePreferenceData(code: string) {
    const uri = API_URL_PREFERENCE_DATA + code;
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

  postPreferenceData(data) {
    // autogenerate and assign postpreference CODE from NORMALIZED_KEY
    data.code = data.normalizedKey.slice().toUpperCase();

    const uri = API_URL_PREFERENCE_DATA;
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

  updatePreferenceData(code: string, data) {
    const uri = API_URL_PREFERENCE_DATA + code;
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

  // ----------- PREFERENCE KEY TYPE -----------
  getPreferenceKeyType(isForceLoad: boolean = false) {
    if (this._preferenceKeyType && !isForceLoad) {
      return Promise.resolve(this._preferenceKeyType);
    } else {
      return this.requestGetPreferenceKeyType();
    }
  }

  postPreferenceKeyType(data) {
    const uri = API_URL_PREFERENCE_KEY_TYPE;

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

  updatePreferenceKeyType(code: string, data) {
    const uri = API_URL_PREFERENCE_KEY_TYPE + code;
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

  deletePreferenceKeyType(id: string) {
    const uri = API_URL_PREFERENCE_KEY_TYPE + id;
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


  // ----------- PRIVATE METHOD -----------
  private requestGetPreferences() {
    const uri = API_URL_PREFERENCE;
    return this.http.get(uri)
      .pipe(map(json => json))
      .toPromise()
      .then(res => {
        this._preferences = res;
        return res;
      }).catch(err => {
        return (err);
      });
  }

  private requestGetPreferenceDatas() {
    const uri = API_URL_PREFERENCE_DATA;
    return this.http.get(uri)
      .pipe(map(json => json))
      .toPromise()
      .then(res => {
        this._preferenceDatas = res;
        return res;
      }).catch(err => {
        return (err);
      });
  }

  private requestGetPreferenceKeyType() {
    const uri = API_URL_PREFERENCE_KEY_TYPE;
    return this.http.get(uri)
      .pipe(map(json => json))
      .toPromise()
      .then(res => {
        this._preferenceKeyType = res;
        return res;
      }).catch(err => {
        return (err);
      })
  }
}
