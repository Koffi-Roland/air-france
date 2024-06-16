import { Injectable } from '@angular/core';
import { environment } from '../../../environments/environment.prod';
import { HttpClient } from '@angular/common/http';
import { map } from 'rxjs/operators';

const API_URL = environment.apiUrl + 'pays/';

@Injectable({ providedIn: 'root' })
export class PaysService {

    constructor(private http: HttpClient) { }

    getPays() {
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

    updatePays(id: string, data: any) {
        const route = API_URL + id;
        return this
            .http
            .put(route, data)
            .pipe(map(json => json))
            .toPromise()
            .then(res => {
                return res;
            }).catch(err => {
                return (err);
            });
    }
}