import { Component, OnInit } from '@angular/core';
import { FieldConfig } from '../../../models/forms/field-config';
import { UntypedFormGroup } from '@angular/forms';

@Component({
  selector: 'app-date',
  templateUrl: './date.component.html',
  styleUrls: ['./date.component.scss']
})
export class DateComponent implements OnInit {

  public field: FieldConfig;
  public group: UntypedFormGroup;

  public isRequired: boolean;

  constructor() { }

  ngOnInit() {
    this.isRequired = this.hasRequiredValidator();
  }

  public hasRequiredValidator(): boolean {
    let isRequired = false;
    if (!this.field.validations) { return isRequired; }
    this.field.validations.forEach(element => {
      if (element.name === 'required') { isRequired = true; }
    });
    return isRequired;
  }

}
