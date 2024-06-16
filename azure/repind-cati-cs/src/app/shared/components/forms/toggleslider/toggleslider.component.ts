import { AfterViewInit, Component, OnInit } from '@angular/core';
import { AbstractControl, UntypedFormGroup } from '@angular/forms';
import { FieldConfig } from 'src/app/shared/models/forms/field-config';

@Component({
  selector: 'app-toggleslider',
  templateUrl: './toggleslider.component.html',
  styleUrls: ['./toggleslider.component.scss']
})
export class TogglesliderComponent implements OnInit {

  public field: FieldConfig;
  public group: UntypedFormGroup;
  public formControl: AbstractControl;

  constructor() {
  }

  ngOnInit(): void {
    this.formControl = this.group.get(this.field.name);

    // default value is falseValue of ToggleSliderOption
    if (!this.formControl.value) {
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
