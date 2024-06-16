import { Component, OnInit, Input, ViewChild } from '@angular/core';
import { Telecom } from '../../../../../../shared/models/resources/telecom';
import { DynamicFormComponent } from '../../../../../../shared/components/forms/dynamic-form/dynamic-form.component';
import { FieldConfig } from '../../../../../../shared/models/forms/field-config';
import { OptionItem } from '../../../../../../shared/models/contents/option-item';
import { TerminalType } from '../../../../../../shared/models/common/terminal-type';
import { TranslateService } from '@ngx-translate/core';
import { RefProviderService } from '../../../../../../core/services/references/ref-provider.service';
import { AutocompleteFilterService } from '../../../../../../core/services/filters/autocomplete-filter.service';
import { ReferenceDataType } from '../../../../../../shared/models/references/ReferenceDataType.enum';
import { Validators } from '@angular/forms';

@Component({
  selector: 'app-telecom-details-form',
  templateUrl: './telecom-details-form.component.html',
  styleUrls: ['./telecom-details-form.component.scss']
})
export class TelecomDetailsFormComponent implements OnInit {

  @ViewChild(DynamicFormComponent, { static: true }) private _form: DynamicFormComponent;

  @Input() telecom: Telecom;

  public config: FieldConfig[];

  private allTerminalTypes: Array<OptionItem> = [];

  constructor(private translateService: TranslateService,
    private refProvider: RefProviderService, private autocompleteFilterService: AutocompleteFilterService) { }

  ngOnInit() {
    this.initializeOptions();
    this.initializeFormConfiguration();
  }

  get form(): DynamicFormComponent {
    return this._form;
  }

  get valid(): boolean {
    return this.form.valid;
  }

  get value(): any {
    return this.form.value;
  }

  private initializeOptions(): void {
    Object.keys(TerminalType).map(key => {
      const termType = TerminalType[key];
      const opt = this.translateService.instant('TERMINAL-TYPE-' + termType);
      const optItem = new OptionItem(termType, opt, termType);
      this.allTerminalTypes.push(optItem);
    });
  }

  private initializeFormConfiguration(): void {

    this.config = [
      {
        type: 'autocomplete',
        name: 'countryCode',
        label: 'COUNTRYCODE',
        autofocus: true,
        value: (this.telecom) ? this.telecom.countryCode : '',
        placeholder: '33, +33, FR...',
        options: this.refProvider.findReferenceData(ReferenceDataType.COUNTRY_CODES),
        filterFct: (val: string, arr: any) => this.autocompleteFilterService.filterCountryCodes(val, arr),
        validations: [
          {
            name: 'required',
            validator: Validators.required,
            message: 'FIELD-REQUIRED'
          }
        ]
      },
      {
        type: 'input',
        label: 'PHONENUMBER',
        inputType: 'text',
        name: 'phone',
        value: (this.telecom) ? this.telecom.phoneNumberNotNormalized : '',
        placeholder: 'PLACEHOLDER-PHONE-NUMBER-FIELD',
        validations: [
          {
            name: 'required',
            validator: Validators.required,
            message: 'FIELD-REQUIRED'
          },
          {
            name: 'maxlength',
            validator: Validators.maxLength(60),
            message: 'MAX-LENGTH-MSG',
            maxLength: 60
          },
        ]
      },
      {
        type: 'select',
        name: 'terminalType',
        label: 'TERMINAL-TYPE',
        value: (this.telecom) ? this.telecom.terminalType : TerminalType.Phone,
        options: this.allTerminalTypes,
        validations: [
          {
            name: 'required',
            validator: Validators.required,
            message: 'FIELD-REQUIRED'
          }
        ]
      }
    ];

  }

}
