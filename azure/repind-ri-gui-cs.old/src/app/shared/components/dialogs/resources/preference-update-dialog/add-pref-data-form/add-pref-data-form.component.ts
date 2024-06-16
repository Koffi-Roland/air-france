import { UntypedFormGroup, UntypedFormControl, Validators } from '@angular/forms';
import { Component, OnInit, Input } from '@angular/core';
import { PreferenceValueConfiguration } from '../../../../../../shared/models/references/PreferenceValueConfiguration';
import { RefProviderService } from '../../../../../../core/services/references/ref-provider.service';
import { CommonService } from '../../../../../../core/services/common.service';
import { DateHelper } from '../../../../../../shared/utils/helpers/date.helper';
import { ReferenceData } from '../../../../../../shared/models/references/ReferenceData';
import { PreferenceData } from '../../../../../../shared/models/common/preference-data';
import { Preference } from '../../../../../../shared/models/resources/preference';

@Component({
  selector: 'app-add-pref-data-form',
  templateUrl: './add-pref-data-form.component.html',
  styleUrls: ['./add-pref-data-form.component.scss']
})
export class AddPrefDataFormComponent implements OnInit {

  @Input() type: string;
  @Input() preference: Preference;

  public keys = [];
  private allKeys = [];

  public currentLocal = '';
  public preferenceValueConfiguration: PreferenceValueConfiguration;
  public selectedPreferenceKey: string;

  public formGroup: UntypedFormGroup;
  public keyCtrl: UntypedFormControl;
  public valueCtrl: UntypedFormControl;

  constructor(private _refProviderService: RefProviderService, private common: CommonService) { }

  ngOnInit() {
    this.currentLocal = this.common.getCurrentLocal();
    this.allKeys = this._refProviderService.findReferenceKeysByType(this.type);
    const allCodes = this.preference.preferenceData.map((value: PreferenceData) => value.key.toUpperCase());
    this.keys = this.allKeys.filter((value: ReferenceData) => allCodes.indexOf(value.code) === -1);
    this.fillReferenceData();
    this.setMandatoriesKeyFirst();
    this.initializeForm();
  }

  public addPreferenceData(): void {
    const key = this.keyCtrl.value;
    const val = this.valueCtrl.value;
    const value = (this.preferenceValueConfiguration.type === 'Date') ? DateHelper.convertToString(new Date(val)) : val;
    const preferenceData = new PreferenceData(null, key.code, value);
    preferenceData.refData = key;
    this.preference.addPreferenceData(preferenceData);
    this.preferenceValueConfiguration = null;
    this.valueCtrl.setValue('');
    this.keyCtrl.setValue('');
    this.removeKey(key);
    this.common.showMessage('PREFERENCE-DATA-ADDED');
  }

  public updateValue(): void {
    const key = this.keyCtrl.value;
    if (key) {
      this.selectedPreferenceKey = (this.currentLocal === 'fr') ? key.labelFR : key.labelEN;
      const conf = this._refProviderService.findPreferenceValueConfigurationByKey(key.code, this.type);
      this.preferenceValueConfiguration = conf;
      this.valueCtrl.setValue('');
      this.valueCtrl.clearValidators();
      this.valueCtrl.setValidators([
        Validators.maxLength(this.preferenceValueConfiguration.maxLength),
        Validators.minLength(this.preferenceValueConfiguration.minLength)
      ]);
    } else {
      this.preferenceValueConfiguration = null;
    }
  }

  private initializeForm(): void {
    this.keyCtrl = new UntypedFormControl();
    this.valueCtrl = new UntypedFormControl();
    this.formGroup = new UntypedFormGroup({
      key: this.keyCtrl,
      value: this.valueCtrl
    });
  }

  /**
   * Remove a key of the keys array.
   * @param key `ReferenceData`
   */
  private removeKey(key: ReferenceData): void {
    let index = -1;
    for (let i = 0; i < this.keys.length; i++) {
      const k: ReferenceData = this.keys[i];
      if (k.code === key.code) {
        index = i;
        break;
      }
    }
    if (index === -1) { return; }
    this.keys.splice(index, 1);
  }

  /**
   * Fill the reference data with a boolean to know if it's mandatory or not
   */
  private fillReferenceData(): void {
    this.keys.forEach((key: ReferenceData) => {
      const conf = this._refProviderService.findPreferenceValueConfigurationByKey(key.code, this.type);
      key.isRequired = conf.isRequired;
    });
  }

  /**
   * Order the keys, the mandatory ones are in first.
   */
  private setMandatoriesKeyFirst(): void {
    this.keys.sort((a: ReferenceData, b: ReferenceData) => (a.isRequired && !b.isRequired) ? -1 : 1);
  }
}
