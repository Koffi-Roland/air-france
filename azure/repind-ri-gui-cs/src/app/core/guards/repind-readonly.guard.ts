import { UserService } from '@airfranceklm/permission';
import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { tap } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class RepindReadonlyGuard implements CanActivate {

  constructor(private _permissionService: UserService, private router: Router) { }

  canActivate(
    next: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean> | Promise<boolean> | boolean {
    return this._permissionService.hasPermission('P_RIGUI_READ_ONLY').pipe(
      tap((admin: boolean) => {
        if (!admin) {
          this.router.navigateByUrl('accessdenied');
        }
      })
    );
  }
}
