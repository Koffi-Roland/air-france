import { Injectable } from '@angular/core';
import { Resolve, Router } from '@angular/router';
import { MergeService } from '../services/merge/merge.service';
import { CommonService } from '../services/common.service';

@Injectable()
export class MergeResultResolver implements Resolve<any> {
  constructor(private mergeService: MergeService, private router: Router, private common: CommonService) { }

  resolve(): Promise<any> | boolean {
    if (!this.mergeService.getFirstGin() || !this.mergeService.getSecondGin() || !this.mergeService.getBlocsToKeep()) {
      this.router.navigate(['individuals/merge']);
      return null;
    }
    const firstGin = this.mergeService.getFirstGin();
    const secondGin = this.mergeService.getSecondGin();
    return this.mergeService.mergeIndividuals(firstGin, secondGin, this.mergeService.getBlocsToKeep()).then(res => {
      return res;
    }, err => {
      this.router.navigate(['individuals/merge']);
      return null;
    });
  }
}
