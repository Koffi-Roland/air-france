import { Injectable } from '@angular/core';
import { RefStoreService } from './ref-store.service';
import { ReferenceDataType } from '../../../shared/models/references/ReferenceDataType.enum';
import { ReferenceData } from '../../../shared/models/references/ReferenceData';
import { PreferenceKey } from '../../../shared/models/references/PreferenceKey';
import { PreferenceValueConfiguration } from '../../../shared/models/references/PreferenceValueConfiguration';

@Injectable({
  providedIn: 'root'
})
export class RefProviderService {

  constructor(private _refStoreService: RefStoreService) { }

  /**
   * Given a `ReferenceDataType` or `string` (ref. table name) in parameter, this method returns an array of `ReferenceData`
   * that contains the code and the labels in EN and FR.
   * @param type `ReferenceDataType` or `string`
   */
  public findReferenceData(type: ReferenceDataType | string): ReferenceData[] {

    if (!this.isTableNameExist(type)) {
      return [];
    }

    return this._refStoreService.getReferences().filter((refData: ReferenceData) => refData.type === type);
  }

  private isTableNameExist(type: string): boolean {
    return Object.keys(ReferenceDataType).find(key => ReferenceDataType[key] === type) ? true : false;
  }

  /**
   * Given a `ReferenceDataType` and a `string` that represents a code, return the appropriate `ReferenceData` that
   * has the code given in parameters.
   * @param type `ReferenceDataType`
   * @param code `string`
   */
  public findReferenceDataByCode(type: ReferenceDataType, code: string): ReferenceData {
    const references = this._refStoreService.getReferences().filter((refData: ReferenceData) => refData.type === type);
    const filteredReferences = references.filter((data: ReferenceData) => data.code === code);
    return (filteredReferences.length === 1) ? filteredReferences[0] : null;
  }

  /**
   *
   * @param type
   */
  public findReferenceKeysByType(type: string): ReferenceData[] {
    const keysFiltered = this._refStoreService.getPreferenceKeys().filter((key: PreferenceKey) => key.type === type);
    const references = [];
    keysFiltered.forEach((key: PreferenceKey) => {
      const ref = this.findReferenceDataByCode(ReferenceDataType.PREFERENCE_KEYS, key.key);
      references.push(ref);
    });
    return references;
  }

  /**
   *
   * @param key
   * @param type
   */
  public findPreferenceValueConfigurationByKey(key: string, type: string): PreferenceValueConfiguration {
    const filteredKeys = this._refStoreService.getPreferenceKeys().filter((data: PreferenceKey) => {
      return (data.key === key && data.type === type);
    });
    return (filteredKeys.length === 1) ? filteredKeys[0].valueConfig : null;
  }

}
