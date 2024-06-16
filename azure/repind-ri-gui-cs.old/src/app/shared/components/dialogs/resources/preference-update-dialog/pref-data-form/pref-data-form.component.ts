import { UntypedFormGroup, UntypedFormControl, Validators } from '@angular/forms';
import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { ReferenceData } from '../../../../../../shared/models/references/ReferenceData';
import { PreferenceData } from '../../../../../../shared/models/common/preference-data';
import { PreferenceValueConfiguration } from '../../../../../../shared/models/references/PreferenceValueConfiguration';
import { CommonService } from '../../../../../../core/services/common.service';
import { RefProviderService } from '../../../../../../core/services/references/ref-provider.service';
import { ReferenceDataType } from '../../../../../../shared/models/references/ReferenceDataType.enum';
import { DateHelper } from '../../../../../../shared/utils/helpers/date.helper';

@Component({
  selector: 'app-pref-data-form',
  templateUrl: './pref-data-form.component.html',
  styleUrls: ['./pref-data-form.component.scss']
})
export class PrefDataFormComponent implements OnInit {

  @Input() type: string;
  @Input() preferenceData: PreferenceData;
  @Output() valueHasChanged = new EventEmitter();
  @Output() valueHasBeenSaved = new EventEmitter();

  public referenceData: ReferenceData;
  public preferenceValueConfiguration: PreferenceValueConfiguration;

  public formGroup: UntypedFormGroup;
  public keyCtrlLabel: string;
  public valueCtrl: UntypedFormControl;

  public isDateField: boolean;
  public isValueSaved = true;

  constructor(private common: CommonService, private _refProviderService: RefProviderService) { }

  ngOnInit() {
    const code = this.preferenceData.key.toUpperCase();
    this.referenceData = this._refProviderService.findReferenceDataByCode(ReferenceDataType.PREFERENCE_KEYS, code);
    this.preferenceValueConfiguration = this._refProviderService.findPreferenceValueConfigurationByKey(code, this.type);
    this.isDateField = this.preferenceValueConfiguration.type === 'Date';
    this.initializeForm();
  }

  public saveValueContent(): void {
    this.valueCtrl.disable();
    this.isValueSaved = true;
    const ctrlVal = this.valueCtrl.value;
    const val = (this.preferenceValueConfiguration.type === 'Date') ? DateHelper.convertToString(ctrlVal) : ctrlVal;
    this.preferenceData.value = val;
    this.valueHasBeenSaved.emit();
  }

  public editValueContent(): void {
    this.valueCtrl.enable();
    this.isValueSaved = false;
    this.valueHasChanged.emit();
  }

  public clearValueContent(): void {
    this.valueCtrl.setValue('');
    this.valueCtrl.disable();
    this.isValueSaved = false;
    this.valueHasChanged.emit();
  }

  private initializeForm(): void {
    const locale = this.common.getCurrentLocal();

    /** Initialize the form controls */
    this.keyCtrlLabel = (locale === 'fr') ? this.referenceData.labelFR : this.referenceData.labelEN;

    // Convert the default value in date if needed
    const value = (this.isDateField) ? DateHelper.convertToDate(this.preferenceData.value) : this.preferenceData.value;
    this.valueCtrl = new UntypedFormControl({ value: value, disabled: this.isValueSaved });
    this.valueCtrl.setValidators([
      Validators.maxLength(this.preferenceValueConfiguration.maxLength),
      Validators.minLength(this.preferenceValueConfiguration.minLength)
    ]);

    /** Initialize the form group */
    this.formGroup = new UntypedFormGroup({
      value: this.valueCtrl
    });

  }

}
