import { Injectable } from '@angular/core';
import { Resolve, Router } from '@angular/router';
import { PcsContractScoreService } from '../services/pcsContractScore.service';

@Injectable()
export class PcsContractScoreResolver implements Resolve<any> {
    constructor(private pcsContractScore: PcsContractScoreService,
        private router: Router) { }

    resolve(): Promise<any> | boolean {
        return this.pcsContractScore.getPcsContractScore().then(res => {
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
