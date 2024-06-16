import { Component, OnInit } from '@angular/core';
import { UntypedFormGroup } from '@angular/forms';
import { FieldConfig } from '../../../models/forms/field-config';

@Component({
  selector: 'app-input',
  templateUrl: './input.component.html',
  styleUrls: ['./input.component.scss']
})
export class InputComponent implements OnInit {

  public field: FieldConfig;
  public group: UntypedFormGroup;

  public isRequired: boolean;
  public isMaxLength: boolean;
  public maxLength: number;

  constructor() { }

  ngOnInit() {
    this.isRequired = this.hasRequiredValidator();
    this.isMaxLength = this.hasMaxLengthValidator();
  }

  public hasRequiredValidator(): boolean {
    let isRequired = false;
    if (!this.field.validations) { return isRequired; }
    this.field.validations.forEach(element => {
      if (element.name === 'required') { isRequired = true; }
    });
    return isRequired;
  }

  public hasMaxLengthValidator(): boolean {
    let hasMaxLengthValidator = false;
    if (!this.field.validations) { return hasMaxLengthValidator; }
    this.field.validations.forEach(element => {
      if (element.name === 'maxlength') {
        hasMaxLengthValidator = true;
        this.maxLength = element.maxLength;
      }
    });
    return hasMaxLengthValidator;
  }

}
