import { Resource } from './resource';
import { Signature } from '../common/signatures/signature';
import { PreferenceData } from '../common/preference-data';
import { Right } from '../common/rights/right';
import { OperationType } from '../common/rights/operation-type.enum';
import { ResourceType } from './resource-type';

export class Preference extends Resource {
  constructor(
    protected _id: string,
    private _type: string,
    private _creationSignature: Signature,
    private _modificationSignature: Signature,
    private _preferenceData: PreferenceData[]
  ) {
    super(_id, '#FFD94A', ResourceType.Preference);
  }

  get type(): string {
    return this._type;
  }

  get creationSignature(): Signature {
    return this._creationSignature;
  }

  get modificationSignature(): Signature {
    return this._modificationSignature;
  }

  get preferenceData(): PreferenceData[] {
    return this._preferenceData;
  }

  get preferenceDataCount(): number {
    return this._preferenceData.length;
  }

  public deletePreferenceData(data: PreferenceData): void {
    let index = -1;
    for (let i = 0 ; i < this.preferenceDataCount ; i++) {
      const d = this.preferenceData[i];
      if (d.key === data.key) {
        index = i;
        break;
      }
    }
    this._preferenceData.splice(index, 1);
  }

  /**
   * Add a preference data to the preference data array.
   * The method returns `true` if the data is added and `false` if the data is updated!
   * @param data
   */
  public addPreferenceData(data: PreferenceData): boolean {
    let hasPreferenceData = false;
    this._preferenceData.forEach((prefData: PreferenceData) => {
      if (prefData.key.toUpperCase() === data.key) {
        hasPreferenceData = true;
      }
    });

    if (hasPreferenceData) {
      this.updatePreferenceData(data);
      return false;
    } else {
      this._preferenceData.push(data);
      return true;
    }

  }

  private updatePreferenceData(data: PreferenceData): void {
    this._preferenceData.forEach((elt: PreferenceData) => {
      if (elt.key.toUpperCase() === data.key) {
        elt.value = data.value;
      }
    });
  }

}
