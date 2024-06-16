import { Injectable } from '@angular/core';
import { Resolve, Router } from '@angular/router';
import { PreferenceService } from '../services/preference.service';

@Injectable()
export class PreferenceKeyTypeResolver implements Resolve<any> {
  constructor(private preferenceService: PreferenceService,
    private router: Router) { }

  resolve(): Promise<any> | boolean {
      return this.preferenceService.getPreferenceKeyType(true).then(res => {
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
