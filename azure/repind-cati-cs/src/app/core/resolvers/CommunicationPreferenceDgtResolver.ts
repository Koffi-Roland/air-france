import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, Resolve, RouterStateSnapshot, Router } from '@angular/router';
import { CommunicationPreferencesDgtService } from '../services/communication-preferences-dgt.service';

@Injectable()
export class CommunicationPreferenceDgtResolver implements Resolve<any> {
  constructor(private communicationPreferencesDgt: CommunicationPreferencesDgtService,
    private router: Router) { }

  resolve(): Promise<any> | boolean {
    /*Load type of Tracking */
      return this.communicationPreferencesDgt.getCommunicationPreferencesDgts(true).then(res => {
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
