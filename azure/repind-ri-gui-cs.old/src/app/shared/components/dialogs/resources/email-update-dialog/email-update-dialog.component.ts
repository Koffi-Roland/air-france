import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit, Inject, ViewChild } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatStepper } from '@angular/material/stepper';
import { AuthorizationMailingUtil } from '../../../../models/common/authorization-mailing';
import { EmailService } from '../../../../../core/services/resources/email.service';
import { Email } from '../../../../models/resources/email';
import { Converter } from '../../../../models/serializer/converter';
import { ResourceGeneralInfoFormComponent } from '../resource-general-info-form/resource-general-info-form.component';
import { EmailDetailsFormComponent } from './email-details-form/email-details-form.component';
import {StringHelper} from '../../../../utils/helpers/string.helper';

@Component({
  selector: 'app-email-update-dialog',
  templateUrl: './email-update-dialog.component.html',
  styleUrls: ['./email-update-dialog.component.scss']
})
export class EmailUpdateDialogComponent implements OnInit {

  @ViewChild('genInfoForm', { static: true }) genInfoForm: ResourceGeneralInfoFormComponent;
  @ViewChild('emailDetailsForm', { static: true }) emailDetailsForm: EmailDetailsFormComponent;

  public email: Email;
  public numberOfSteps = 3;

  @ViewChild('stepper', { static: true }) stepper: MatStepper;

  constructor(public dialogRef: MatDialogRef<EmailUpdateDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any, private _emailService: EmailService) { }

  ngOnInit() {
    this.email = (this.data.isUpdate) ? this.data.resource : null;
  }

  public updateEmail(): void {
    const updatedEmail = this.getEmail();
    this._emailService.update(updatedEmail)
      .then(() => {
        this.dialogRef.close();
      })
      .catch((err: HttpErrorResponse) => {
        this.manageHttpError(err);
      });
  }

  public createEmail(): void {
    const createdEmail = this.getEmail();
    this._emailService.create(createdEmail)
      .then(() => {
        this.dialogRef.close();
      })
      .catch((err: HttpErrorResponse) => {
        this.manageHttpError(err);
      });
  }

  /** Method to manage HTTP error when the problem is about the email address */
  private manageHttpError(err: HttpErrorResponse): void {
    if (err.error.restError.code === 'business.400.001.220') {
      this.emailDetailsForm.form.setError('email', { 'invalidEmail': true });
      this.stepper.selectedIndex = 0;
    }
  }

  /** Get the email from the form */
  private getEmail(): Email {
    let emailAddress = this.emailDetailsForm.value.email;
    const nat = AuthorizationMailingUtil.convertToAuthorizationMailing(this.emailDetailsForm.value.nat);
    const status = Converter.convertToStatus(this.genInfoForm.value.status);
    const type = Converter.convertToType(this.genInfoForm.value.type);
    emailAddress = StringHelper.escapeQuotesForEmails(emailAddress);
    if (this.data.isUpdate) {
      const id = this.email.id;
      const creation = this.email.signatureCreation;
      const modification = this.email.signatureModification;
      const version = this.email.version;
      return new Email(id, type, status, emailAddress, nat, creation, modification, version);
    } else {
      return new Email(null, type, status, emailAddress, nat, null, null, null);
    }
  }

}
