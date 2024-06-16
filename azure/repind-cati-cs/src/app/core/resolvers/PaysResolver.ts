import { Injectable } from '@angular/core';
import { Resolve, Router } from '@angular/router';
import { PaysService } from '../services/pays.service';

@Injectable()
export class PaysResolver implements Resolve<any> {
    constructor(private pays: PaysService,
        private router: Router) { }

    resolve(): Promise<any> | boolean {
        return this.pays.getPays().then(res => {
            return res;
        }).catch(err => {
            return this.errorRoot();
        });
    }

    private errorRoot() {
        this.router.navigate(['/']);
        return null;
    }
}
