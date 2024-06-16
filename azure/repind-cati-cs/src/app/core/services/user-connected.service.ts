import {
    Injectable
} from '@angular/core';
import {
    environment
} from '../../../environments/environment.prod';
import {
    HttpClient
} from '@angular/common/http';
import {
    map
} from 'rxjs/operators';
import {User, UserService} from "@airfranceklm/permission";
import {Observable} from "rxjs";

const API_URL = environment.apiUrl + 'securities/user';

@Injectable({
    providedIn: 'root'
})
export class UserConnectedService {

    constructor(private http: HttpClient, private userService: UserService) {
    }

    public getUser() {
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

    public getUserFirstNameAndLastName(): Observable<Pick<User, 'firstname' |'lastname'>> {
        return this
            .userService
            .getUser()
            .pipe(
                map((item: User) => ({firstname: item.firstname, lastname: item.lastname})));
    }

}
