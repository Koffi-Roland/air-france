import { UserService } from '@airfranceklm/permission';
import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, Router } from '@angular/router';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class HasPermissionGuard implements CanActivate {

  constructor(private _permissionService: UserService, private router: Router) { }

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean> | Promise<boolean> | boolean {
    const requiredPermissions = route.data['requiredPermissions'] as Array<string>;
    return true;
  }
}
