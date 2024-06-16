import { Component, Inject, OnInit, ViewChild } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { AlertService } from '../../../../../core/services/resources/alert.service';
import { FieldConfig } from '../../../../models/forms/field-config';
import { Alert } from '../../../../models/resources/alert';
import { DynamicFormComponent } from '../../../forms/dynamic-form/dynamic-form.component';
import { AlertUpdateFormConfig } from './config/alert-update-form.config';

@Component({
  selector: 'app-alert-update-dialog',
  templateUrl: './alert-update-dialog.component.html',
  styleUrls: ['./alert-update-dialog.component.scss']
})
export class AlertUpdateDialogComponent implements OnInit {

  @ViewChild('form', { static: true }) form: DynamicFormComponent;

  public alert: Alert;

  public isChanged: boolean;

  public config: FieldConfig[];

  constructor(public dialogRef: MatDialogRef<AlertUpdateDialogComponent>, @Inject(MAT_DIALOG_DATA) public data: any,
    public alertService: AlertService) { }

  ngOnInit(): void {
    this.alert = this.data.resource;
    this.config = AlertUpdateFormConfig.config(this.alert)
  }

  public updateAlert() {
    this.alert.optin = this.form.value.alert;
    this.alertService.update(this.alert);
  }

  public handleChange($event) {
    this.isChanged = $event.alert !== this.alert.optin;
  }

}
