import { AddrDetailsFormComponent } from './addr-details-form/addr-details-form.component';
import { Component, OnInit, Inject, ViewChild, OnDestroy } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatStepper } from '@angular/material/stepper';
import { AddressService } from '../../../../../core/services/resources/address.service';
import { Address } from '../../../../models/resources/address';
import { ResourceGeneralInfoFormComponent } from '../resource-general-info-form/resource-general-info-form.component';
import { AddrConfirmationStepComponent } from './addr-confirmation-step/addr-confirmation-step.component';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-address-update-dialog',
  templateUrl: './address-update-dialog.component.html',
  styleUrls: ['./address-update-dialog.component.scss']
})
export class AddressUpdateDialogComponent implements OnInit, OnDestroy {

  @ViewChild('detailsForm', { static: true }) detailsForm: AddrDetailsFormComponent;
  @ViewChild('cpltForm', { static: true }) cpltForm: AddrDetailsFormComponent;
  @ViewChild('genInfoForm', { static: true }) genInfoForm: ResourceGeneralInfoFormComponent;
  @ViewChild('confirmationStep', { static: true }) confirmationStep: AddrConfirmationStepComponent;

  @ViewChild('stepper', { static: true }) stepper: MatStepper;

  public address: Address;
  public numberOfSteps = 4;
  public isForced: boolean;

  private onStepChangeSubscription: Subscription;

  constructor(public dialogRef: MatDialogRef<AddressUpdateDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any, private _addressService: AddressService) { }

  ngOnInit() {
    this.address = this.data.resource;
    this.isForced = true;
    this.onStepChangeSubscription = this.stepper.selectionChange.subscribe((data: any) => {
      // When the step has changed from the confirmation step to another step
      if (data.previouslySelectedIndex === this.numberOfSteps - 1) {
        // Set the hasNormalizationFailed boolean to false (i.e. so the confirmation button should be displayed)
        this.confirmationStep.hasNormalizationFailed = false;
      }
    });
  }

  ngOnDestroy(): void {
    this.onStepChangeSubscription.unsubscribe();
  }

  checkAddressData(): void {
    this.stepper.selectedIndex = 0;
  }

  forceChanges(): void {
    if (this.data.isUpdate) {
      this.updateAddress();
    } else {
      this.createAddress();
    }
  }

  createAddress(): void {
    const address = this.getAddress();
    this._addressService.create(address).then((data: any) => {
      if (data) {
        this.dialogRef.close();
      } else {
        this.confirmationStep.hasNormalizationFailed = true;
      }
    });
  }

  updateAddress(): void {
    const address = this.getAddress();
    this._addressService.update(address).then((data: any) => {
      if (data.success) {
        this.dialogRef.close();
      } else {
        this.confirmationStep.hasNormalizationFailed = true;
      }
    });
  }

  /** From the form fields, this method returns an Address that is going to be updated or created */
  private getAddress(): Address {

    const numberAndStreet = this.detailsForm.value.noAndStreet;
    const zipCode = this.detailsForm.value.zipCode;
    const city = this.detailsForm.value.city;
    const country = this.detailsForm.value.countryCode;

    const corpName = this.cpltForm.value.corporate;
    const addrCplm = this.cpltForm.value.complement;
    const locality = this.cpltForm.value.locality;
    const state = this.cpltForm.value.state;

    const status = this.genInfoForm.value.status;
    const type = this.genInfoForm.value.type;

    // Check that the normalizationSlideToggle is defined, if not it means that there was a problem with normalization so force
    // to not normalize (i.e. normalization flag is unchecked)
    const normalizationFlag = (!this.confirmationStep.hasNormalizationFailed) ? this.confirmationStep.isChecked : false;
    const isForced = (normalizationFlag) ? 'O' : 'N';

    const id = (this.address) ? this.address.id : null;
    const creation = (this.address) ? this.address.signatureCreation : null;
    const modification = (this.address) ? this.address.signatureModification : null;
    const usages = (this.address) ? this.address.usages : null;
    const version = (this.address) ? this.address.version : null;

    return new Address(id, type, status, corpName, addrCplm, locality, numberAndStreet, zipCode, city, country,
      isForced, state, creation, modification, usages, version);

  }

}
