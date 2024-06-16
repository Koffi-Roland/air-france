import { Address } from './../../../../../../shared/models/resources/address';
import { Component, OnInit, Input, ViewChild } from '@angular/core';
import { DynamicFormComponent } from '../../../../../../shared/components/forms/dynamic-form/dynamic-form.component';
import { FieldConfig } from '../../../../../../shared/models/forms/field-config';
import { Validators } from '@angular/forms';

@Component({
  selector: 'app-addr-complementary-form',
  templateUrl: './addr-complementary-form.component.html',
  styleUrls: ['./addr-complementary-form.component.scss']
})
export class AddrComplementaryFormComponent implements OnInit {

  @ViewChild(DynamicFormComponent, { static: true }) addressForm: DynamicFormComponent;

  @Input() address: Address;

  public addrComplementaryFormConfig: FieldConfig[];

  constructor() { }

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

    this.addrComplementaryFormConfig = [
      {
        type: 'input',
        name: 'corporate',
        label: 'CORPORATENAME',
        autofocus: true,
        value: (this.address) ? this.address.corporateName : '',
        validations: [
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
        name: 'complement',
        label: 'COMPLEMENT',
        value: (this.address) ? this.address.complement : '',
        validations: [
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
        name: 'locality',
        label: 'LOCALITY',
        width: '65%',
        value: (this.address) ? this.address.locality : '',
        validations: [
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
        name: 'state',
        label: 'STATE',
        width: '25%',
        value: (this.address) ? this.address.state : '',
        validations: [
          {
            name: 'maxlength',
            validator: Validators.maxLength(2),
            message: 'MAX-LENGTH-MSG',
            maxLength: 2
          }
        ]
      }
    ];

  }

}
