import { Component, OnInit, ViewChild, Input } from '@angular/core';
import { DynamicFormComponent } from '../../../../../../shared/components/forms/dynamic-form/dynamic-form.component';
import { FieldConfig } from '../../../../../../shared/models/forms/field-config';
import { Address } from '../../../../../../shared/models/resources/address';
import { Validators } from '@angular/forms';
import { RefProviderService } from '../../../../../../core/services/references/ref-provider.service';
import { AutocompleteFilterService } from '../../../../../../core/services/filters/autocomplete-filter.service';
import { ReferenceDataType } from '../../../../../../shared/models/references/ReferenceDataType.enum';

@Component({
  selector: 'app-addr-details-form',
  templateUrl: './addr-details-form.component.html',
  styleUrls: ['./addr-details-form.component.scss']
})
export class AddrDetailsFormComponent implements OnInit {

  @ViewChild(DynamicFormComponent, { static: true }) addressForm: DynamicFormComponent;

  @Input() address: Address;

  public addrFormConfig: FieldConfig[];

  constructor(private refProvider: RefProviderService, private autocompleteFilterService: AutocompleteFilterService) { }

  ngOnInit() {
    this.initializeFormConfiguration();
  }

  get valid(): boolean {
    return this.addressForm.valid;
  }

  get value(): any {
    return this.addressForm.value;
  }

  private initializeFormConfiguration(): void {

    this.addrFormConfig = [
      {
        type: 'input',
        name: 'noAndStreet',
        label: 'NUMBERANDSTREET',
        autofocus: true,
        value: (this.address) ? this.address.numberAndStreet : '',
        validations: [
          {
            name: 'required',
            validator: Validators.required,
            message: 'FIELD-REQUIRED'
          },
          {
            name: 'maxlength',
            validator: Validators.maxLength(42),
            message: 'MAX-LENGTH-MSG',
            maxLength: 42
          }
        ]
      },
      {
        type: 'input',
        name: 'zipCode',
        label: 'ZIPCODE',
        value: (this.address) ? this.address.zipCode : '',
        width: '25%',
        validations: [
          {
            name: 'required',
            validator: Validators.required,
            message: 'FIELD-REQUIRED'
          },
          {
            name: 'maxlength',
            validator: Validators.maxLength(10),
            message: 'MAX-LENGTH-MSG',
            maxLength: 10
          }
        ]
      },
      {
        type: 'input',
        name: 'city',
        label: 'CITY',
        width: '65%',
        value: (this.address) ? this.address.city : '',
        validations: [
          {
            name: 'required',
            validator: Validators.required,
            message: 'FIELD-REQUIRED'
          },
          {
            name: 'maxlength',
            validator: Validators.maxLength(32),
            message: 'MAX-LENGTH-MSG',
            maxLength: 32
          }
        ]
      },
      {
        type: 'autocomplete',
        name: 'countryCode',
        label: 'COUNTRYCODE',
        value: (this.address) ? this.address.country : '',
        options: this.refProvider.findReferenceData(ReferenceDataType.COUNTRY_CODES),
        filterFct: (val: string, arr: any) => this.autocompleteFilterService.filterCountryCodes(val, arr),
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
