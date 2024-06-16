import { UserService } from '@airfranceklm/permission';
import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { tap } from 'rxjs/operators';
import { RightsService } from '../services/rights/rights.service';

@Injectable({
  providedIn: 'root'
})
export class ReadGuard implements CanActivate {

  constructor(private _permissionService: UserService, private router: Router) { }

  canActivate(
    next: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean> | Promise<boolean> | boolean {
    return this._permissionService.hasRole('ROLE_READ').pipe(
      tap((hasRole: boolean) => {
        if (!hasRole) {
          this.router.navigate(['accessdenied']);
        }
      })
    );
  }
}
