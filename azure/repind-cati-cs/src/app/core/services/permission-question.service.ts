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

const API_URL = environment.apiUrl + 'permissionsQuestion/';

@Injectable({
  providedIn: 'root'
})
export class PermissionQuestionService {

  private permissionQuestion;

  constructor(private http: HttpClient) {}

  getPermissionsQuestion(isForceLoad: boolean = false) {
    if (this.permissionQuestion && !isForceLoad) {
      return Promise.resolve(this.permissionQuestion);
    } else {
    const uri = API_URL;
    return this
      .http
      .get(uri)
      .pipe(map(json => json))
      .toPromise()
      .then(res => {
        this.permissionQuestion = res;
        return res;
      }).catch(err => {
        return Promise.reject(err);
      });
    }
  }

  getPermissionQuestion(id: string) {
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

  postPermissionQuestion(data) {
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

  updatePermissionQuestion(id: string, data) {
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

  deletePermissionQuestion(id: string) {
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
