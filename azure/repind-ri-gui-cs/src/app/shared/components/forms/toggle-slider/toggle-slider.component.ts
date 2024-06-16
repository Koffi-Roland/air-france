import { Component, OnInit } from '@angular/core';
import { AbstractControl, UntypedFormGroup } from '@angular/forms';
import { FieldConfig } from '../../../models/forms/field-config';

@Component({
  selector: 'app-toggle-slider',
  templateUrl: './toggle-slider.component.html',
  styleUrls: ['./toggle-slider.component.scss']
})
export class ToggleSliderComponent implements OnInit {


  public field: FieldConfig;
  public group: UntypedFormGroup;
  public formControl: AbstractControl;

  constructor() { }

  ngOnInit(): void {
    this.formControl = this.group.get(this.field.name);

    const offValue = this.field.options[0].falseValue;
    const onValue = this.field.options[0].trueValue;
    const formValue = this.formControl.value;
    // default value is falseValue of ToggleSliderOption
    if (formValue !== offValue && formValue !== onValue) {
      this.formControl.patchValue(this.field.options[0].falseValue);
    }
  }

  /**
   * use getValue(bool) of options from ToggleSliderOption 
   * assign the corresponding string value to this ToggleSliderComponent formControl
   * 
   * @param e event with toggle slide
   */
  setValue(e) {
    const value: string = this.field.options[0].getValue(e.checked);
    this.formControl.patchValue(value);
  }
}
