import { Component, Inject, OnInit, ViewChild } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { IndividualService } from '../../../../../core/services/individual/individual.service';
import { ConsentsService } from '../../../../../core/services/resources/consents.service';
import { FieldConfig } from '../../../../models/forms/field-config';
import { Consent } from '../../../../models/resources/consent';
import { DynamicFormComponent } from '../../../forms/dynamic-form/dynamic-form.component';
import { ConsentUpdateConfig } from './config/consent-update.config';

@Component({
  selector: 'app-consent-update-dialog',
  templateUrl: './consent-update-dialog.component.html',
  styleUrls: ['./consent-update-dialog.component.scss']
})
export class ConsentUpdateDialogComponent implements OnInit {

  @ViewChild('form', { static: true }) consentForm: DynamicFormComponent;

  public consent: Consent;

  public config: FieldConfig[];

  public isReadyToUpdate = false;

  constructor(public dialogRef: MatDialogRef<ConsentUpdateDialogComponent>, @Inject(MAT_DIALOG_DATA) public data: any,
    public consentService: ConsentsService, public individualService: IndividualService
  ) {
  }

  ngOnInit(): void {
    this.consent = this.data.resource;
    this.config = ConsentUpdateConfig.config(this.consent);
  }

  public updateConsent() {
    const val = this.consentForm.value;
    this.consent.consentDataIsConsent = val.consent;
    this.consentService.update(this.consent);
  }

  handleChange($event) {
    this.isReadyToUpdate = $event.consent !== this.consent.consentData.isConsent;
  }

}
