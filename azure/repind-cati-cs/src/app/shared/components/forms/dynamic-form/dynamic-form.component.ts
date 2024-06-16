import { UntypedFormGroup, UntypedFormBuilder, Validators, ValidationErrors, AbstractControl } from '@angular/forms';
import { FieldConfig } from './../../../models/forms/field-config';
import { Component, OnInit, Input, OnDestroy } from '@angular/core';
import { EventActionMessageEnum } from '../../../models/EventActionMessage';
import { Validator } from '../../../models/forms/validator';
import { FieldValueTuple } from 'src/app/shared/models/forms/field-value-tuple';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-dynamic-form',
  templateUrl: './dynamic-form.component.html',
  styleUrls: ['./dynamic-form.component.scss']
})
export class DynamicFormComponent implements OnInit, OnDestroy {

  @Input() fields: FieldConfig[] = [];

  @Input() globalValidators: Validator[] = [];

  @Input() action: EventActionMessageEnum;

  public form: UntypedFormGroup;

  public values: any;

  private _formControlSub: Subscription;
  private _fieldMap: Map<String, FieldConfig> = new Map();

  constructor(private fb: UntypedFormBuilder) { }

  ngOnDestroy(): void {
    this._formControlSub.unsubscribe;
  }

  ngOnInit() {
    this.form = this.createControl();

    if (this.values) { // in case of non update
      this.form.patchValue(this.values);
    }
    this.createGlobalValidation();

    // to access the fields faster, later
    this.fields.forEach(f => this._fieldMap.set(f.name, f));
    this._formControlSub = this.form.valueChanges.subscribe(e => this.applyAutoFill(e));
  }

  get value(): any {
    return this.form.value;
  }

  get valid(): boolean {
    if (!this.form) { return false; }
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
      if (field.type === 'button') { return; }

      const control = this.fb.control({
        value: field.value,
        disabled: this.action === EventActionMessageEnum.UPDATE && field.disableForUpdate
      },
        this.bindValidations(field.validations || []));
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

  private createGlobalValidation() {

    const listValidators = [];
    if (this.globalValidators) {
      this.globalValidators.forEach(gValidator => {
        listValidators.push(gValidator.validator);
      });
    }

    this.form.setValidators(
      Validators.compose(listValidators)
    );

  }

  public setValues(values: any) {
    this.values = values;
  }

  /**
   * find matching value then patch and hide related field
   * @param event valueChanges in form
   */
  applyAutoFill(event: any) {
    this.fields.filter(field => field.autoFill).forEach(field => {
      const value = event[field.name];
      if (field.autoFill.toMatch.has(value)) {
        field.autoFill.toMatch.get(value).forEach(e => this._fieldMap.get(e.field).isHidden = true);
        const tuples: FieldValueTuple[] = field.autoFill.toMatch.get(value);
        tuples.forEach(tuple => {
          const patch = {};
          patch[tuple.field] = tuple.value;
          this.form.patchValue(patch, { emitEvent: false });
        });
        field.autoFill.updateToReset(field.name, true);
      } else if (field.autoFill.toReset.get(field.name)) {
        field.autoFill.resetAutofill.forEach(e => {
          this.form.get(e).patchValue(null, { emitEvent: false });
          this._fieldMap.get(e).isHidden = false;
        });
        field.autoFill.updateToReset(field.name, false);
      }
    });
  }

}
