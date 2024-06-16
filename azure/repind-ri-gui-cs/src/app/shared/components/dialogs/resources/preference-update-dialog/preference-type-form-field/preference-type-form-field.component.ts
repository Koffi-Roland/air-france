import { Component, Input, OnChanges } from '@angular/core';
import { UntypedFormControl } from '@angular/forms';
import { RefProviderService } from '../../../../../../core/services/references/ref-provider.service';
import { COMMON_DATA_TYPE } from '../../../../../models/common/common-data-type.enum';
import { PicklistOption } from '../../../../../models/forms/picklist-option';
import { PreferenceValueConfiguration } from '../../../../../models/references/PreferenceValueConfiguration';
import { ReferenceData } from '../../../../../models/references/ReferenceData';

@Component({
  selector: 'app-preference-type-form-field',
  templateUrl: './preference-type-form-field.component.html',
  styleUrls: ['./preference-type-form-field.component.scss']
})
export class PreferenceTypeFormFieldComponent implements OnChanges {

  @Input()
  preferenceValueConfig: PreferenceValueConfiguration;

  @Input()
  valueCtrl: UntypedFormControl;

  @Input()
  keyCtrlLabel: string;

  @Input()
  isValueSaved?: boolean;

  public options: PicklistOption[];
  public isCommon: boolean;

  constructor(private _refProviderService: RefProviderService) { }

  ngOnChanges(): void {
    const type = this.preferenceValueConfig.type;
    this.isCommon = COMMON_DATA_TYPE.isCommonType(type);

    if (!this.isCommon) {
      // do not fetch reference data if not needed
      this.options = this._refProviderService.findReferenceData(type)
        .map(refData => new PicklistOption(refData.code, this.preferenceValueConfig.type + '-' + refData.code));
    } else {
      // ohter common type
      if (type === COMMON_DATA_TYPE.BOOLEAN) {
        this.options = [new PicklistOption('true', 'BOOLEAN-TRUE'), new PicklistOption('false', 'BOOLEAN-FALSE')]
      }
    }
  }

}
