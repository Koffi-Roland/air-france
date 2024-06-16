import { Injectable } from '@angular/core';
import { Resolve, Router } from '@angular/router';
import {PcsNonContractScoreService} from "../services/pcsNonContractScore.service";

@Injectable()
export class PcsNonContractScoreResolver implements Resolve<any> {
    constructor(private pcsNonContractScore: PcsNonContractScoreService,
        private router: Router) { }

    resolve(): Promise<any> | boolean {
        return this.pcsNonContractScore.getPcsNonContractScore().then(res => {
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
