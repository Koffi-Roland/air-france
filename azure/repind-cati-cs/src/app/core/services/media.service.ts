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

const API_URL = environment.apiUrl + 'medias/';

@Injectable({
  providedIn: 'root'
})
export class MediaService {

  private media;
  constructor(private http: HttpClient) {}

  getMedias(isForceLoad: boolean = false) {
    if (this.media && !isForceLoad) {
      return Promise.resolve(this.media);
    } else {
    const uri = API_URL;
    return this
      .http
      .get(uri)
      .pipe(map(json => json))
      .toPromise()
      .then(res => {
        this.media = res;
        return res;
      }).catch(err => {
        return (err);
      });
    }
  }

  getMedia(id: string) {
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

  postMedia(data) {
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

  updateMedia(id: string, data) {
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

  deleteMedia(id: string) {
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
