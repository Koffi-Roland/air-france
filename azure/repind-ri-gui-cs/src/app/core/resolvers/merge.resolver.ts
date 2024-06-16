import { Injectable } from '@angular/core';
import { Resolve, Router } from '@angular/router';
import { MergeService } from '../services/merge/merge.service';
import { CommonService } from '../services/common.service';

@Injectable()
export class MergeResolver implements Resolve<any> {
  constructor(private mergeService: MergeService, private router: Router, private common: CommonService) { }

  resolve(): Promise<any> | boolean {
    if (!this.mergeService.getFirstGin() || !this.mergeService.getSecondGin()) {
      this.router.navigate(['individuals/merge']);
      return null;
    }
    return this.mergeService.getIndividualsForMerge(this.mergeService.getFirstGin(), this.mergeService.getSecondGin(),
    this.mergeService.getForce()).then(res => {
      return res;
    }, err => {
      this.router.navigate(['individuals/merge']);
      return null;
    });
  }
}
