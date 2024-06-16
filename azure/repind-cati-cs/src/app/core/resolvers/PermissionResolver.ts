import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, Resolve, RouterStateSnapshot, Router } from '@angular/router';
import { PermissionQuestionService } from '../services/permission-question.service';

@Injectable()
export class PermissionResolver implements Resolve<any> {
  constructor(private permissionQuestion: PermissionQuestionService,
    private router: Router) { }

  resolve(): Promise<any> | boolean {
    /*Load type of Tracking */
      return this.permissionQuestion.getPermissionsQuestion(true).then(res => {
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
