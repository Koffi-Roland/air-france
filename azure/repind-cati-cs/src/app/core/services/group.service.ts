import {
  Injectable
} from '@angular/core';
import {
  environment
} from '../../../environments/environment.prod';
import {
  HttpClient,
  HttpErrorResponse,
  HttpXhrBackend
} from '@angular/common/http';
import {
  map
} from 'rxjs/operators';

const API_URL_GROUP = environment.apiUrl + 'groups/';
const API_URL_GROUPINFO = environment.apiUrl + 'groupInfos/';
const API_URL_GROUPPRODUCT = environment.apiUrl + 'groupProduct/';
const API_URL_PRODUCT = environment.apiUrl + 'products/';

@Injectable({
  providedIn: 'root'
})
export class GroupService {

  private _groups;
  private _groupProducts;

  private _communicationsPreferencesDgt;

  private _products;

  get groups() {
    return this._groups;
  }

  set groups(groups: any) {
    this._groups = groups;
  }

  get groupProducts() {
    return this._groupProducts;
  }

  get products() {
    return this._products;
  }

  set products(products: any) {
    this._products = products;
  }

  constructor(private http: HttpClient) { }

  getGroup(id: string) {
    const uri = API_URL_GROUP + id;
    return this.http
      .get(uri)
      .pipe(map(json => json))
      .toPromise()
      .then(res => {
        return res;
      }).catch(err => {
        return (err);
      });
  }

  getProducts() {
    const uri = API_URL_PRODUCT;
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

  getGroupsInfo(isForceLoad: boolean = false) {
    if (this._groups && !isForceLoad) {
      return Promise.resolve(this._groups);
    } else {
      return this.requestGetGroupsInfo();
    }
  }

  private requestGetGroupsInfo() {
    const uri = API_URL_GROUPINFO;
    return this
      .http
      .get(uri)
      .pipe(map(json => json))
      .toPromise()
      .then(res => {
        this._groups = res;
        return res;
      }).catch(err => {
        return (err);
      });
  }

  deleteGroupInfo(id: string) {
    const uri = API_URL_GROUPINFO + id;
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

  getGroupProduct(id: string) {
    const uri = API_URL_GROUPPRODUCT + id;
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

  postGroupProduct(data) {
    const uri = API_URL_GROUPPRODUCT;
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

  putGroupProduct(id: string, data) {
    const uri = API_URL_GROUPPRODUCT + id;
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


  postGroupInfo(data) {
    const uri = API_URL_GROUPINFO;
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

  putGroupInfo(id: number, data) {
    const uri = API_URL_GROUPINFO + id;
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

  getGroupsProducts(isForceLoad: boolean = false) {
    if (this.groupProducts && !isForceLoad) {
      return Promise.resolve(this._groupProducts);
    } else {
      return this.requestGetGroupsProducts();
    }
  }

  private requestGetGroupsProducts() {
    const uri = API_URL_GROUPPRODUCT;
    return this.http.get(uri)
      .pipe(map(json => json))
      .toPromise()
      .then(res => {
        this._groupProducts = res;
        return res;
      }).catch(err => {
        return (err);
      });
  }

  deleteGroupProduct(productId: string, groupId: string) {
    const uri = API_URL_GROUPPRODUCT + productId + '/' + groupId;
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

  updateGroup(data) {
    const uri = API_URL_GROUP;
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

  deleteGroup(id: string) {
    const uri = API_URL_GROUP + id;
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
