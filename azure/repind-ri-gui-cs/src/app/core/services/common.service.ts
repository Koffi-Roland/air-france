import { Injectable } from '@angular/core';
import { environment } from '../../../environments/environment';
import { HttpErrorResponse } from '@angular/common/http';
import { ErrorService } from './error.service';
import { TranslateService } from '@ngx-translate/core';
import { ReferenceDataType } from '../../shared/models/references/ReferenceDataType.enum';

@Injectable()
export class CommonService {

  public referenceDataType = ReferenceDataType;

  public static getTransformEnumTypeStat(ref: ReferenceDataType) {
    return ref + '-';
  }

  constructor(private errorService: ErrorService, private translate: TranslateService) { }

  // Get API URI by environment
  public getUrl(): string {
    return environment.apiUrl + '/';
  }

  // Handle service error
  public handleError(error: HttpErrorResponse, duration?: number): void {
    if (error.status === 0) {
      this.errorService.displayConnectionError();
    } else if (error.status === 401) {
      this.showMessage('UNAUTHORIZED');
    } else if (error.status === 500) {
      error.error.restError.code = 'business.500.004';
      this.showErrorCode(error, duration);
    } else {
      this.showErrorCode(error, duration);
    }
    throw error;
  }

  // Handle service message
  public showMessage(message: string): void {
    this.errorService.displayDefaultError(message);
  }

  // Handle service error
  public showErrorCode(error: HttpErrorResponse, duration?: number): void {
    // this.errorService.setLastErrorMessage(error);
    // In the old version we displayed a snackbar with the following function call
    this.errorService.displayDefaultErrorFromCodeError(error, duration);
  }

  // Open a snackbar to inform the user
  copyToClipboard(): void {
    this.errorService.displayDefaultError('CLIPBOARD-SUCCESS');
  }

  // Compare two date only on day, month and year
  public dateEquals(date: Date, date2: Date) {
    return (date.getDate() === date2.getDate() && date.getMonth() === date2.getMonth() && date.getFullYear() === date2.getFullYear());
  }

  public getCurrentLocal(): string {
    return this.translate.currentLang;
  }

  public getTransformEnumType(ref: ReferenceDataType) {
    return ref + '-';
  }

  public selectFile(contentType, multiple) {
    return new Promise(resolve => {
      const input = document.createElement('input');
      input.type = 'file';
      input.multiple = multiple;
      input.accept = contentType;

      input.onchange = () => {
        const files = Array.from(input.files);
        if (multiple) {
          resolve(files);
        } else {
          resolve(files[0]);
        }
      };
      input.click();
    });
  }

}
