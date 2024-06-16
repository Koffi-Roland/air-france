import { Injectable } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { TranslateService } from '@ngx-translate/core';
import { HttpErrorResponse } from '@angular/common/http';
import { Subject } from 'rxjs';
import { ErrorSnackbarComponent } from '../../shared/components/error-snackbar/error-snackbar.component';

@Injectable({
  providedIn: 'root'
})
export class ErrorService {

  private _lastErrorMessage = '';

  private ginNotFound = new Subject<any>();
  public ginNotFound$ = this.ginNotFound.asObservable();

  constructor(
    private snackBar: MatSnackBar,
    private translate: TranslateService
  ) { }

  public updateGinNotFoundSubject() {
    this.ginNotFound.next();
  }

  setLastErrorMessage(error: HttpErrorResponse): void {
    console.log('set up new error message');
    this._lastErrorMessage =
      this.translate.instant('ERROR') +
      this.translate.instant('ERROR-' + error.error.restError.code);
  }

  get lastErrorMessage(): string {
    return this._lastErrorMessage;
  }

  set lastErrorMessage(msg: string) {
    this._lastErrorMessage = msg;
  }

  displayDefaultError(error: string) {
    this.snackBar.open(this.translate.instant(error), '', {
      duration: 3000
    });
  }

  /**
   * For the error display, use of a custom component with its own style for the snackbar
   * @param error
   * @param duration
   */
  displayDefaultErrorFromCodeError(
    error: HttpErrorResponse,
    duration?: number
  ) {

    // Default duration is 10s
    const DEFAULT_DURATION = 10000;

    let msg = this.translate.instant('ERROR');
    if (error.error.restError) {
      msg += this.translate.instant('ERROR-' + error.error.restError.code);
    } else {
      msg += this.translate.instant('ERROR-UNKNOW');
    }

    this.snackBar.openFromComponent(ErrorSnackbarComponent, {
      duration: duration ? duration : DEFAULT_DURATION,
      panelClass: ['error-snackbar', 'bg-warn-500'],
      horizontalPosition: 'center',
      verticalPosition: 'bottom',
      data: { text: msg },
    });

  }

  displayConnectionError() {
    this.snackBar.openFromComponent(ErrorSnackbarComponent, {
      duration: 3000,
      panelClass: ['error-snackbar', 'bg-warn-500'],
      horizontalPosition: 'center',
      verticalPosition: 'bottom',
      data: {
        text: 'NEED-RECONNECTION'
      },
    });
  }

}
