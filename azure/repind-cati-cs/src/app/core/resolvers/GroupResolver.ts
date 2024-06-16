import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, Resolve, RouterStateSnapshot, Router } from '@angular/router';
import { GroupService } from '../services/group.service';

@Injectable()
export class GroupResolver implements Resolve<any> {
  constructor(private group: GroupService,
    private router: Router) { }

  resolve(): Promise<any> | boolean {
    /*Load type of Tracking */
      return this.group.getGroupsInfo(true).then(res => {
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
