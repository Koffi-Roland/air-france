import { Component, OnInit, Inject, ViewChild } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatStepper } from '@angular/material/stepper';
import { HttpErrorResponse } from '@angular/common/http';
import { TelecomService } from '../../../../../core/services/resources/telecom.service';
import { Telecom } from '../../../../models/resources/telecom';
import { Converter } from '../../../../models/serializer/converter';
import { ResourceGeneralInfoFormComponent } from '../resource-general-info-form/resource-general-info-form.component';
import { TelecomDetailsFormComponent } from './telecom-details-form/telecom-details-form.component';
import { Status } from '../../../../models/common/status';
import { TerminalType } from '../../../../models/common/terminal-type';
import { Type } from '../../../../models/common/type';

@Component({
  selector: 'app-telecom-update-dialog',
  templateUrl: './telecom-update-dialog.component.html',
  styleUrls: ['./telecom-update-dialog.component.scss']
})
export class TelecomUpdateDialogComponent implements OnInit {

  @ViewChild('genInfoForm', { static: true }) genInfoForm: ResourceGeneralInfoFormComponent;
  @ViewChild('telecomDetailsForm', { static: true }) telecomDetailsForm: TelecomDetailsFormComponent;

  @ViewChild('stepper', { static: true }) stepper: MatStepper;

  public currentLocal = '';

  public telecom: Telecom;
  public numberOfSteps = 3;

  constructor(
    public dialogRef: MatDialogRef<TelecomUpdateDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    private _telecomService: TelecomService
  ) { }

  ngOnInit() {
    this.telecom = (this.data.isUpdate) ? this.data.resource : null;
  }

  public createTelecom(): void {
    const createdTelecom = this.getTelecom();
    this._telecomService.create(createdTelecom)
      .then(() => {
        this.dialogRef.close();
      })
      .catch((err: HttpErrorResponse) => {
        this.manageHttpError(err);
      });
  }

  public updateTelecom(): void {
    const updatedTelecom = this.getTelecom();
    this._telecomService.update(updatedTelecom)
      .then(() => {
        this.dialogRef.close();
      })
      .catch((err: HttpErrorResponse) => {
        this.manageHttpError(err);
      });
  }

  /** Method to manage http error when the telecom is bad */
  private manageHttpError(err: HttpErrorResponse): void {
    if (err.error.restError.code === 'business.400.001.703'
      || err.error.restError.code === 'business.400.001.702'
      || err.error.restError.code === 'business.400.001.701') {
      this.telecomDetailsForm.form.setError('phone', { 'invalidTelecom': true });
      this.stepper.selectedIndex = 0;
    }
  }

  /** Get the telecom from the form fields */
  private getTelecom(): Telecom {
    const phoneNumber: string = this.telecomDetailsForm.value.phone;
    const status: Status = Converter.convertToStatus(this.genInfoForm.value.status);
    const type: Type = Converter.convertToType(this.genInfoForm.value.type);
    const terminalType: TerminalType = Converter.convertToTerminalType(this.telecomDetailsForm.value.terminalType);
    const countryCode: string = this.telecomDetailsForm.value.countryCode;
    if (this.data.isUpdate) {
      const id = this.telecom.id;
      const regionCode = this.telecom.regionCode;
      const notNormalizedPhone = this.telecom.phoneNumberNotNormalized;
      const creation = this.telecom.signatureCreation;
      const modification = this.telecom.signatureModification;
      const version = this.telecom.version;
      return new Telecom(id, type, status, terminalType, countryCode, regionCode, phoneNumber, notNormalizedPhone, creation,
        modification, version);
    } else {
      return new Telecom(null, type, status, terminalType, countryCode, null, phoneNumber, null, null, null, null);
    }
  }

}
