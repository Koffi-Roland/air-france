import { Injectable } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';
import { HttpErrorResponse } from '@angular/common/http';
import { MatSnackBar } from '@angular/material/snack-bar';

@Injectable({
  providedIn: 'root'
})
export class ErrorService {

  private _lastErrorMessage = '';

  constructor(
    private snackBar: MatSnackBar,
    private translate: TranslateService
  ) {}

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

  displayDefaultError(error: any, parameters?: Object) {

    if (parameters) {
      for (const [key, value] of Object.entries(parameters)) {
        if (typeof(value) === 'string') {
          parameters[key] = this.translate.instant(value);
        }
      }
      this.snackBar.open(this.translate.instant(error, parameters), '', {
        duration: 3000
      });
    } else if (typeof error === 'string') {
      this.snackBar.open(this.translate.instant(error), '', {
        duration: 3000
      });
    } else {
      if (typeof error.error === 'object') {
        this.snackBar.open(this.translate.instant(error.error.detailMessage), '', {
          duration: 3000
        });
      } else {
      this.snackBar.open(this.translate.instant('INTERNAL-ERROR'), '', {
        duration: 3000
      });
    }
  }
  }
}
