import { Injectable } from '@angular/core';
import { Resolve, Router } from '@angular/router';
import { PcsFactorService } from '../services/pcsFactor.service';

@Injectable()
export class PcsFactorResolver implements Resolve<any> {
    constructor(private pcsFactor: PcsFactorService,
        private router: Router) { }

    resolve(): Promise<any> | boolean {
        return this.pcsFactor.getPcsFactor().then(res => {
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
