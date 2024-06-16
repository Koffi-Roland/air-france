import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, Resolve, RouterStateSnapshot, Router } from '@angular/router';
import { CommunicationTypeService } from '../services/communication-type.service';

@Injectable()
export class CommunicationTypeResolver implements Resolve<any> {
  constructor(private communicationType: CommunicationTypeService,
    private router: Router) { }

  resolve(): Promise<any> | boolean {
    /*Load type of Tracking */
      return this.communicationType.getCommunicationTypes(true).then(res => {
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
