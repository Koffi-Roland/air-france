import { Injectable } from '@angular/core';
import { RightsProviderService } from './rights-provider.service';
import { ResourceType } from '../../../shared/models/resources/resource-type';
import { OperationType } from '../../../shared/models/common/rights/operation-type.enum';
import { Right } from '../../../shared/models/common/rights/right';
import { UserService } from '@airfranceklm/permission';



@Injectable({
  providedIn: 'root'
})
export class RightsService {

  private _myRoles: string[];

  constructor(private _permissionService: UserService, private _rightsProviderService: RightsProviderService) {
    _permissionService.getRoles().subscribe(roles => {
      this._myRoles = roles;
    });
  }

  /**
   * Given a `ResourceType`, this method returns a boolean to know if the authenticated user (Habile) can
   * create the resource in parameter. To do so, the use of `UserService` is needed to know what are the
   * roles of the Habile user.
   * @param type
   */
  public canCreateResource(type: ResourceType): Promise<boolean> {
    const roles = this.getRoles(OperationType.CREATE, type);
    return new Promise((resolve) => {
      if (roles.length === 0) { return; }
      const hasAnyRole = this.hasAnyRole(roles, this._myRoles);
      resolve(hasAnyRole);
    });
  }

  /**
   * Given a `ResourceType`, this method returns a boolean to know if the authenticated user (Habile) can
   * read the resource in parameter. To do so, the use of `UserService` is needed to know what are the
   * roles of the Habile user.
   * @param type
   */
  public canReadResource(type: ResourceType): Promise<boolean> {
    const roles = this.getRoles(OperationType.READ, type);
    return new Promise((resolve) => {
      if (roles.length === 0) { return; }
      const hasAnyRole = this.hasAnyRole(roles, this._myRoles);
      resolve(hasAnyRole);
    });
  }

  /**
   * Given a `ResourceType`, this method returns a boolean to know if the authenticated user (Habile) can
   * update the resource in parameter. To do so, the use of `UserService` is needed to know what are the
   * roles of the Habile user.
   * @param type
   */
  public canUpdateResource(type: ResourceType): Promise<boolean> {
    const roles = this.getRoles(OperationType.UPDATE, type);
    return new Promise((resolve) => {
      if (roles.length === 0) { return; }
      const hasAnyRole = this.hasAnyRole(roles, this._myRoles);
      resolve(hasAnyRole);
    });
  }

  /**
   * Given a `ResourceType`, this method returns a boolean to know if the authenticated user (Habile) can
   * delete the resource in parameter. To do so, the use of `UserService` is needed to know what are the
   * roles of the Habile user.
   * @param type
   */
  public canDeleteResource(type: ResourceType): Promise<boolean> {
    const roles = this.getRoles(OperationType.DELETE, type);
    return new Promise((resolve) => {
      if (roles.length === 0) { return; }
      const hasAnyRole = this.hasAnyRole(roles, this._myRoles);
      resolve(hasAnyRole);
    });
  }

  /**
   * Given an `OperationType`, this method calls the service `RightsProviderService` to get the rights
   * of the given `ResourceType`. After that, the method filter the rights array to only return the roles
   * of the given operation type.
   * @param operation
   * @param type
   */
  private getRoles(operation: OperationType, type: ResourceType): string[] {
    const rights: Right[] = this._rightsProviderService.provideRightsFor(type);
    let roles = [];
    rights.forEach((r: Right) => {
      if (r.operation === operation) {
        roles = r.roles;
      }
    });
    return roles;
  }

  private hasAnyRole(roles: string[], userRoles: string[]): boolean {
    let hasAnyRole = false;
    roles.forEach((str: string) => {
      const index = userRoles.indexOf(str);
      if (index !== -1) {
        hasAnyRole = true;
      }
    });
    return hasAnyRole;
  }

  public get myRoles(): string[] {
    return this._myRoles;
  }


}
