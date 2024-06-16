import { Component, OnInit, Inject, ViewChild } from '@angular/core';
import { Individual } from '../../../models/individual/individual';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatStepper } from '@angular/material/stepper';
import { IndividualUpdateFormComponent } from './individual-update-form/individual-update-form.component';
import { IndividualProfileUpdateFormComponent } from './individual-profile-update-form/individual-profile-update-form.component';
import { IndividualService } from '../../../../core/services/individual/individual.service';
import { IndividualRequest } from '../../../models/requests/w000442/IndividualRequest';

@Component({
  selector: 'app-individual-update-dialog',
  templateUrl: './individual-update-dialog.component.html',
  styleUrls: ['./individual-update-dialog.component.scss']
})
export class IndividualUpdateDialogComponent implements OnInit {

  @ViewChild('identification') public identificationForm: IndividualUpdateFormComponent;
  @ViewChild('profile') public profileForm: IndividualProfileUpdateFormComponent;
  @ViewChild('stepper', { static: true }) public stepper: MatStepper;

  public individual: Individual;

  public nbOfSteps = 3;

  constructor(public dialogRef: MatDialogRef<IndividualUpdateDialogComponent>, @Inject(MAT_DIALOG_DATA) public data: any,
    private _individualService: IndividualService) { }

  ngOnInit() {
    this.individual = this.data.individual;
  }

  public confirmUpdate(): void {
    const request = this.getIndividualRequest();
    this._individualService.updateIndividual(request)
      .then((data: boolean) => {
        if (data) { this.dialogRef.close(); } else { this.stepper.selectedIndex = 0; }
      });
  }

  public goLastStep(): void {
    this.stepper.selectedIndex = this.nbOfSteps - 1;
  }

  private getIndividualRequest(): IndividualRequest {
    const identificationRequest = this.identificationForm.individualIdentificationRequest;
    const profileRequest = this.profileForm.profileRequest;
    return new IndividualRequest(identificationRequest, profileRequest);
  }

}
