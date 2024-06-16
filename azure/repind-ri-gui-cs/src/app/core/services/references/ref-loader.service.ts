import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { RefStoreService } from './ref-store.service';
import { CommonService } from '../common.service';
import { ReferenceData } from '../../../shared/models/references/ReferenceData';
import { PreferenceKey } from '../../../shared/models/references/PreferenceKey';
import { PreferenceValueConfiguration } from '../../../shared/models/references/PreferenceValueConfiguration';
import { ReferenceDataType } from '../../../shared/models/references/ReferenceDataType.enum';
import { TranslateService } from '@ngx-translate/core';
import { WarningExpandSnackbarModel } from '../../../shared/components/warning-expand-snackbar/model/warning-expand-snackbar-model';
import { WarningLoaderService } from '../../../shared/components/warning-expand-snackbar/service/warning-loader.service';

@Injectable({
  providedIn: 'root'
})
export class RefLoaderService {

  private _isRefreshPopUpdisplayed: boolean = false;
  private _missingReferences = [];

  constructor(private http: HttpClient, private common: CommonService, private _refStoreService: RefStoreService, private _warningService: WarningLoaderService, private _translate: TranslateService) { }

  /**
   * Loads all the `ReferenceDataType` from backend.
   */
  public loadAllReferences(): void {
    Object.keys(ReferenceDataType).map(key => this.loadReferenceData(ReferenceDataType[key]));
    this.loadPreferenceKeys();
  }

  /**
   * Given a `ReferenceDataType` this method makes a HTTP call to the backend and then translate the answer
   * into a `ReferenceData` that will be then stored in the `RefStoreService`.
   * @param type a `ReferenceDataType`
   */
  private loadReferenceData(type: ReferenceDataType) {
    const url = this.common.getUrl() + 'ref/table/' + type;
    this.http.get(url).toPromise()
      .then((data: any) => {
        data.referenceDatas.map((elt: any) => {
          const referenceData = this.convertToReferenceData(type, elt);
          this._refStoreService.storeReferenceData(referenceData);
        });
      })
      .catch((err: HttpErrorResponse) => {
        this.handleRefTableServerCallError();
      });
  }


  public loadPreferenceKeys(): void {
    const url = this.common.getUrl() + 'ref/preference/keys';
    this.http.get(url).toPromise()
      .then((data: any) => {
        data.forEach((elt: any) => {
          const key = this.convertToPreferenceKey(elt);
          this._refStoreService.storePreferenceKey(key);
        });
      })
      .catch((err: HttpErrorResponse) => {
        this.handleRefTableServerCallError();
      });
  }


  private handleRefTableServerCallError() {
    if (!this._isRefreshPopUpdisplayed) {
      this.lookForMissingReferences();
      this._warningService.displayWarningAction(this.buildWarningModel());
      this._isRefreshPopUpdisplayed = true;
    }
  }

  private buildWarningModel(): WarningExpandSnackbarModel {
    const model: WarningExpandSnackbarModel = {
      warningMessage: this._translate.instant('MISSING-REFERENCE', { number: this._missingReferences.length + (this._refStoreService.getPreferenceKeys().length ? 1 : 0) }),
      actionIconName: 'refresh',
      actionIconTooltip: 'REFRESH',
      actionFunction: (() => this.loadMissingReferences())
    };

    return model;
  }

  /**
   * Method to convert any data into a ReferenceData.
   * @param type `ReferenceDataType`
   * @param data `any`
   */
  private convertToReferenceData(type: ReferenceDataType, data: any): ReferenceData {
    const code = data.code;
    const labelFR = data.libelleFR;
    const labelEN = data.libelleEN;
    return new ReferenceData(type, code, labelFR, labelEN);
  }

  /**
   *
   * @param data
   */
  private convertToPreferenceKey(data: any): PreferenceKey {
    const key = data.key;
    const type = data.type;
    const valueConfiguration = this.convertToValueConfiguration(data);
    return new PreferenceKey(key, type, valueConfiguration);
  }

  /**
   *
   * @param data
   */
  private convertToValueConfiguration(data: any): PreferenceValueConfiguration {
    const min = data.min_length;
    const max = data.max_length;
    const type = data.data_type;
    const condition = data.condition;
    return new PreferenceValueConfiguration(min, max, type, condition);
  }


  private lookForMissingReferences() {
    const referenceLoaded: string[] = [... new Set(this._refStoreService.getReferences().map(ref => ref.type))];
    this._missingReferences = [];

    for (const [key, value] of Object.entries(ReferenceDataType)) {
      if (!referenceLoaded.includes(value)) {
        this._missingReferences.push(key);
      }
    }
  }

  private loadMissingReferences() {
    this._missingReferences.forEach(ref => {
      this.loadReferenceData(ReferenceDataType[ref]);
    });

    if (this._refStoreService.getPreferenceKeys().length === 0) {
      this.loadPreferenceKeys();
    }
    this._isRefreshPopUpdisplayed = false;
  }

}
