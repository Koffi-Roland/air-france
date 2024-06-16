import { Injectable } from '@angular/core';
import { ReferenceData } from '../../../shared/models/references/ReferenceData';
import { PreferenceKey } from '../../../shared/models/references/PreferenceKey';
import { TranslateService } from '@ngx-translate/core';
import { ReferenceDataType } from '../../../shared/models/references/ReferenceDataType.enum';

@Injectable({
  providedIn: 'root'
})
export class RefStoreService {

  private references: ReferenceData[] = [];
  private preferenceKeys: PreferenceKey[] = [];

  constructor(private translate: TranslateService) { }

  /**
   * Store a `ReferenceData` into the references array.
   * @param refData `ReferenceData`
   */
  public storeReferenceData(refData: ReferenceData) {
    if (refData) {
      this.references.push(refData);
      // Add code and traduction labels from DB to I18N files to be used
      const key = refData.type.toUpperCase().concat('-', refData.code.toUpperCase());
      this.translate.set(key, refData.labelFR, 'fr');
      this.translate.set(key, refData.labelEN, 'en');
    }
  }

  /**
   *
   * @param key
   */
  public storePreferenceKey(key: PreferenceKey) {
    if (key) { this.preferenceKeys.push(key); }
  }

  /**
   * Get the array of `ReferenceData`.
   */
  public getReferences(): ReferenceData[] {
    return this.references;
  }

  public getPreferenceKeys(): PreferenceKey[] {
    return this.preferenceKeys;
  }

}
