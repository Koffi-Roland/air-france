import { Injectable } from '@angular/core';
import { Resolve, Router } from '@angular/router';
import { TrackingIndividualService } from '../services/tracking/trackingIndividual.service';
import { EnumService } from '../services/enum.service';
import { IndividualService } from '../services/individual/individual.service';

@Injectable()
export class TrackingServiceResolver implements Resolve<any> {

  constructor(private trackingIndividual: TrackingIndividualService, private router: Router,
    private individualService: IndividualService, private enumService: EnumService) { }

  resolve(): Promise<any> | boolean | void {
    /*Load type of Tracking */
    this.enumService.loadRefTracking();
    const i = this.individualService.getIndividual();
    if (!i) {
      return this.errorRoot();
    }
    return this.enumService.loadRefTracking().then(ret => {
      return this.trackingIndividual.getTrackingByGin(i.gin, 0).then(res => {
        return res;
      }).catch(err => {
        return this.errorRoot();
      });
    }).catch(err => {
      return this.errorRoot();
    });
  }

  private errorRoot() {
    this.router.navigate(['/individuals/individual/dashboard']);
    return null;
  }
}
