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

const API_URL = environment.apiUrl + 'grouptypes/';

@Injectable({
  providedIn: 'root'
})
export class GroupTypeService {

  private groupType;
  constructor(private http: HttpClient) {}

  getGroupTypes(isForceLoad: boolean = false) {
    if (this.groupType && !isForceLoad) {
      return Promise.resolve(this.groupType);
    } else {
    const uri = API_URL;
    return this
      .http
      .get(uri)
      .pipe(map(json => json))
      .toPromise()
      .then(res => {
        this.groupType = res;
        return res;
      }).catch(err => {
        return (err);
      });
    }
  }

  getGroupType(id: string) {
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

  postGroupType(data) {
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

  updateGroupType(id: string, data) {
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

  deleteGroupType(id: string) {
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
