import { Component, OnInit, Inject, ChangeDetectorRef, AfterViewChecked, ViewChildren, QueryList, AfterViewInit, ViewChild } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { EventActionService } from '../../../_services/eventAction.service';
import { CommonArrayDisplayRefTableService } from '../../../_services/commonArrayDisplayRefTable.service';
import { EventActionMessage, EventActionMessageEnum } from '../../../../models/EventActionMessage';
import { TranslateService } from '@ngx-translate/core';
import { DynamicFormComponent } from '../../../../components/forms/dynamic-form/dynamic-form.component';
import { STEPPER_GLOBAL_OPTIONS } from '@angular/cdk/stepper';
import { StepperFormConfig } from 'src/app/shared/models/forms/stepper-form/stepper-form-config';
import { MatStepper } from '@angular/material/stepper';

@Component({
  selector: 'app-create-update-action',
  templateUrl: './createUpdateAction.component.html',
  styleUrls: ['./createUpdateAction.component.scss'],
  providers: [{
    provide: STEPPER_GLOBAL_OPTIONS, useValue: { showError: true }
  }]
})
export class CreateUpdateActionComponent implements OnInit, AfterViewChecked, AfterViewInit {

  @ViewChildren(DynamicFormComponent) private _dynamicForms: QueryList<DynamicFormComponent>;
  @ViewChild(MatStepper) private _horizontalStepperForm: MatStepper;

  /**
   * Type of data display (and which action will be used)
   * Exemple: PREFERENCE
   */
  public type: string;

  /**
   * Label to display
   * Exemple: UPDATE
   */
  public label: string;

  /**
   * Title to display and to translatz using i18n
   *
   */
  public title: string;

  /**
   * Action to do
   * Exemple: CREATE
   */
  public action: EventActionMessageEnum;

  public values: any;

  public config: any;

  public idForUpdate: string;

  public columnId: string;

  public globalValidators: any;

  public functions: any;

  // stepper
  public isNoError: boolean;
  public missingFields: string[];
  public objectToSubmit: any;
  public stepperFormConfig: StepperFormConfig;
  private selectedIndex: number;
  public isFirstIndex: boolean = true; // when the stepper is opened the first index is displayed
  public isLastIndex: boolean;

  /**
   * Construct the modal, check if in options pass some Key are available to use the datas
   * @param data Data to pass to modal
   * @param dialogRef Type of modal to open
   * @param eventActionService Service to send event
   */
  constructor(private translateService: TranslateService, @Inject(MAT_DIALOG_DATA) public data: any,
    public dialogRef: MatDialogRef<CreateUpdateActionComponent>, private eventActionService: EventActionService,
    private cdRef: ChangeDetectorRef) {

    this.type = CommonArrayDisplayRefTableService.getValueByKey('type', data.options);
    this.config = CommonArrayDisplayRefTableService.getValueByKey('form', data.options);
    this.columnId = CommonArrayDisplayRefTableService.getValueByKey('id', data.options);
    this.globalValidators = CommonArrayDisplayRefTableService.getValueByKey('globalValidators', data.options);
    this.functions = CommonArrayDisplayRefTableService.getValueByKey('functions', data.options);
    this.stepperFormConfig = CommonArrayDisplayRefTableService.getValueByKey('stepperForm', data.options);

    this.label = this.data.label;
    this.title = this.label === 'UPDATE' ? 'UPDATE_TYPE' : 'CREATE_NEW_TYPE';
    this.action = this.data.label;

    this.values = this.data.element;
    this.idForUpdate = this.data.element[this.columnId];
  }

  ngAfterViewInit(): void {
    // assign dynamicForm's control to config (needed for error check in step header)
    if (this.stepperFormConfig) {
      let index = 0;
      this._dynamicForms.forEach(d => { this.stepperFormConfig.steps[index++].stepControl = d.form });
    }

    // fill with current value for update
    if (this.action === 'UPDATE') {
      this._dynamicForms.forEach(d => {
        d.form.patchValue(this.values);
      });
    }
  }

  ngOnInit() {
    // this._forms?.setValues(this.values);
    if (this.functions) {
      this.functions.forEach(f => {
        f(this.values);
      });
    }
  }

  ngAfterViewChecked() {
    this.isNoError = this._dynamicForms.map(e => e.form.valid).reduce((acc, curr) => acc && curr, true);
    this.objectToSubmit = {};
    this._dynamicForms.forEach(d => {
      this.objectToSubmit = Object.assign(this.objectToSubmit, d.form.value);
    });
    this.cdRef.detectChanges();
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

  /**
   * Send the Event 'actionSentToBeApply' to execute the action!
   */
  submit() {
    const values = this.objectToSubmit;
    if (this.idForUpdate !== undefined) {
      values[this.columnId] = this.idForUpdate;
    }
    this.eventActionService.actionSentToBeApply(new EventActionMessage(this.action.toString(), this.objectToSubmit));
  }

  /**
   * on summary step: 
   *    fill summary (last) step with a representation of data that will be inserted in database
   *    or indicates all missing field when forms have errors
   * 
   * @param event of step change
   */
  selectionChange(event) {
    this.selectedIndex = event.selectedIndex;

    this.updateIndex();

    if (this.stepperFormConfig.isWithSummary && event.selectedIndex === this.stepperFormConfig.steps.length) {
      this.missingFields = [];
      this._dynamicForms.forEach(e => {
        Object.keys(e.form.value).forEach(k => {
          if (!e.form.value[k] || e.form.value[k]?.length === 0) {
            this.missingFields.push(k);
          }
        });
      });
    }

  }

  private updateIndex() {
    const stepperLength = this.stepperFormConfig.steps.length;
    this.isLastIndex = this.selectedIndex === stepperLength;
    this.isFirstIndex = this.selectedIndex === 0;
  }

  public goNext() {
    this._horizontalStepperForm.next();
  }

  public goPrevious() {
    this._horizontalStepperForm.previous();
  }

  getKeys(obj: any): string[] {
    return Object.keys(obj);
  }

}
