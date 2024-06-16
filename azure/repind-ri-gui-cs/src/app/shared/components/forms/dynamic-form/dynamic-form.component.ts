import {UntypedFormGroup, UntypedFormBuilder, Validators, ValidationErrors, AbstractControl} from '@angular/forms';
import {FieldConfig} from './../../../models/forms/field-config';
import {Component, OnInit, Input, Output, EventEmitter, OnDestroy} from '@angular/core';
import {Subscription} from 'rxjs';
import * as _ from 'lodash';

@Component({
  selector: 'app-dynamic-form',
  templateUrl: './dynamic-form.component.html',
  styleUrls: ['./dynamic-form.component.scss']
})
export class DynamicFormComponent implements OnInit, OnDestroy {

  @Input() fields: FieldConfig[] = [];

  @Output() onFormChange: EventEmitter<any> = new EventEmitter<any>();

  public form: UntypedFormGroup;

  public subscription$: Subscription;

  constructor(private fb: UntypedFormBuilder) {
  }

  ngOnInit() {
    this.form = this.createControl();
    // mark all as touched so we triggers the display of errors
    this.form.markAllAsTouched();
    this.subscription$ = this.form.valueChanges.subscribe(() => {
      if (this.form.valid) {
        this.onFormChange.emit(this.form.value);
      }
    });
  }

  ngOnDestroy(): void {
    this.subscription$.unsubscribe();
  }

  get value(): any {
    return this.form.value;
  }

  get valid(): boolean {
    if (!this.form) {
      return false;
    }
    return this.form.valid;
  }

  public getControl(controlName: string): AbstractControl {
    return this.form.controls[controlName];
  }

  public setError(controlName: string, err: ValidationErrors) {
    this.form.controls[controlName].setErrors(err);
  }

  private createControl(): UntypedFormGroup {
    const group = this.fb.group({});
    this.fields.forEach(field => {
      if (field.type === 'button') {
        return;
      }
      const control = this.fb.control(field.value, this.bindValidations(field.validations || []));
      group.addControl(field.name, control);
    });
    return group;
  }

  private bindValidations(validations: any) {
    if (validations.length > 0) {
      const validList = [];
      validations.forEach(valid => {
        validList.push(valid.validator);
      });
      return Validators.compose(validList);
    }
    return null;
  }

  isDisplayed(field: FieldConfig): boolean {
    return _.isUndefined(field.isDisplayed) ? true : field.isDisplayed();
  }
}
