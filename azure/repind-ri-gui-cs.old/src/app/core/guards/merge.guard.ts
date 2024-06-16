import { UserService } from '@airfranceklm/permission';
import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { tap } from 'rxjs/operators';
import { RightsService } from '../services/rights/rights.service';

@Injectable({
  providedIn: 'root'
})
export class MergeGuard implements CanActivate {

  constructor(private _rightsService: RightsService, private router: Router) { }

  static authorizedRoles: string[] = ['ROLE_ADMIN', 'ROLE_MERGE_FB', 'ROLE_MERGE_GP', 'ROLE_MERGE_FB_MINOR'];

  canActivate(
    next: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean> | Promise<boolean> | boolean {
    const roles: string[] = this._rightsService.myRoles;

    for (const role in roles) {
      if (MergeGuard.authorizedRoles.indexOf(roles[role]) > -1) {
        return true;
      }
    }

    this.router.navigate(['accessdenied']);

  }
}
