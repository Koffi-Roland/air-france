import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, Resolve, RouterStateSnapshot, Router } from '@angular/router';
import { MediaService } from '../services/media.service';

@Injectable()
export class MediaResolver implements Resolve<any> {
  constructor(private media: MediaService,
    private router: Router) { }

  resolve(): Promise<any> | boolean {
    /*Load type of Tracking */
      return this.media.getMedias(true).then(res => {
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
