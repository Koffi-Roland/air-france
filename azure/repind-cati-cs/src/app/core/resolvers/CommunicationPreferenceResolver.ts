import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, Resolve, RouterStateSnapshot, Router } from '@angular/router';
import { CommunicationPreferencesService } from '../services/communication-preferences.service';

@Injectable()
export class CommunicationPreferenceResolver implements Resolve<any> {
  constructor(private communicationPreferences: CommunicationPreferencesService,
    private router: Router) { }

  resolve(): Promise<any> | boolean {
    /*Load type of Tracking */
      return this.communicationPreferences.getCommunicationPreferences(true).then(res => {
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
