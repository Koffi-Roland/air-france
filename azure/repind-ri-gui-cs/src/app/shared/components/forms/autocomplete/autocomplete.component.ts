import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { FieldConfig } from '../../../models/forms/field-config';
import { UntypedFormGroup } from '@angular/forms';
import { Observable } from 'rxjs';
import { startWith, map } from 'rxjs/operators';
import { OptionItem } from '../../../models/contents/option-item';

@Component({
  selector: 'app-autocomplete',
  templateUrl: './autocomplete.component.html',
  styleUrls: ['./autocomplete.component.scss']
})
export class AutocompleteComponent implements OnInit {

  public field: FieldConfig;
  public group: UntypedFormGroup;

  public isRequired: boolean;
  public isMaxLength: boolean;
  public maxLength: number;

  public filteredOptions: Observable<OptionItem[]>;
  public toHighLight = '';

  constructor() { }

  ngOnInit() {
    this.isRequired = this.hasRequiredValidator();
    this.isMaxLength = this.hasMaxLengthValidator();
    this.filteredOptions = this.group.controls[this.field.name].valueChanges
      .pipe(
        // "startWith('')" below, automatically show the auto-complete.
        startWith(''),
        map((value: string) => {
        const filteredOptions = this.field.filterFct(value, this.field.options);
        this.toHighLight = value;
        return filteredOptions;
      }));
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
