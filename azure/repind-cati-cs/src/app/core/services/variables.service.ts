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

const API_URL_VARIABLES = environment.apiUrl + 'variables/';

@Injectable({
  providedIn: 'root'
})
export class VariablesService {

  private _variables;

  constructor(private http: HttpClient) {}

  get preferences() {
    return this._variables;
  }

// ----------- VARIABLES -----------
  getVariables(isForceLoad: boolean = false) {
    if (this._variables && !isForceLoad) {
      return Promise.resolve(this._variables);
    } else {
      return this.requestGetVariables();
    }
  }

  postVariables(data) {
    const uri = API_URL_VARIABLES;
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

  updateVariables(id: string, data) {
    const uri = API_URL_VARIABLES + id;
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

// ----------- PRIVATE METHOD -----------
  private requestGetVariables() {
    const uri = API_URL_VARIABLES;
    return this.http.get(uri)
      .pipe(map(json => json))
      .toPromise()
      .then(res => {
        this._variables = res;
        return res;
      }).catch(err => {
        return (err);
      });
  }
}
