import { Injectable } from '@angular/core';
import { environment } from '../../../environments/environment.prod';
import { HttpClient } from '@angular/common/http';
import { map } from 'rxjs/operators';

const API_URL = environment.apiUrl + 'permissions/';

@Injectable({
  providedIn: 'root'
})
export class PermissionService {

  private permission;

  private _communicationsPreferencesDgt;

  constructor(private http: HttpClient) { }

  getPermissions() {
    if (this.permission) {
      return Promise.resolve(this.permission);
    }
    const uri = API_URL;
    return this
      .http
      .get(uri)
      .pipe(map(json => json))
      .toPromise()
      .then(res => {
        this.permission = res;
        return res;
      }).catch(err => {
        return (err);
      });
  }

  getPermission(id: string) {
    const uri = API_URL + id;
    return this.http
      .get(uri)
      .pipe(map(json => json))
      .toPromise()
      .then(res => {
        return res;
      }).catch(err => {
        if (err.status === 404) {
          return [];
        }
        return (err);
      });
  }

  postPermission(data) {
    const uri = API_URL;
    return this.http
      .post(uri, data)
      .pipe(map(json => json))
      .toPromise()
      .then(res => {
        return res;
      }).catch(err => {
        return (err);
      });
  }

  updatePermission(id: string, data) {
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

  deletePermission(id: string) {
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

  set listComPrefDgt(list: any) {
    this._communicationsPreferencesDgt = list;
  }

  get listComPrefDgt() {
    return this._communicationsPreferencesDgt;
  }

}
