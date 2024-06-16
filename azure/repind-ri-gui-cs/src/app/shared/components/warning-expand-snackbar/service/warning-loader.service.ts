import { Injectable } from '@angular/core';
import { MatSnackBar, MatSnackBarConfig } from '@angular/material/snack-bar';
import { WarningExpandSnackbarModel } from '../model/warning-expand-snackbar-model';
import { WarningExpandSnackbarComponent } from '../warning-expand-snackbar.component';

@Injectable({
  providedIn: 'root'
})
export class WarningLoaderService extends MatSnackBar {

  /**
   * open a custom warning snackbar with action button
   * will be displayed indefinitely
   * 
   * @param warningModel the data needed for the snackbar
   */
  displayWarningAction(warningModel: WarningExpandSnackbarModel) {

    const config: MatSnackBarConfig = {
      duration: undefined,
      horizontalPosition: 'left',
      verticalPosition: 'bottom',
      data: warningModel,
      panelClass: ['snackbar-without-background']
    };

    this.openFromComponent(WarningExpandSnackbarComponent, config);
  }

}
