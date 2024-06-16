import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, Resolve, RouterStateSnapshot, Router } from '@angular/router';
import { PreferenceService } from '../services/preference.service';

@Injectable()
export class PreferenceDataResolver implements Resolve<any> {
  constructor(private preferenceData: PreferenceService,
    private router: Router) { }

  resolve(): Promise<any> | boolean {
    /*Load type of Tracking */
      return this.preferenceData.getPreferenceDatas(true).then(res => {
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
