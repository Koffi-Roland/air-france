import { Component, Inject, OnInit, ViewChild } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { DelegationService } from '../../../../../core/services/resources/delegation.service';
import { FieldConfig } from '../../../../models/forms/field-config';
import { Delegation } from '../../../../models/resources/delegation/delegation';
import { DynamicFormComponent } from '../../../forms/dynamic-form/dynamic-form.component';
import { DelegationUpdateFormConfig } from './config/delegation-update-form.config';

@Component({
  selector: 'app-delegation-update-dialog',
  templateUrl: './delegation-update-dialog.component.html',
  styleUrls: ['./delegation-update-dialog.component.scss']
})
export class DelegationUpdateDialogComponent implements OnInit {

  @ViewChild('form', { static: true }) form: DynamicFormComponent;

  public delegation: Delegation;

  public config: FieldConfig[];

  public isChanged: boolean;

  constructor(public dialogRef: MatDialogRef<DelegationUpdateDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    private _delegationService: DelegationService) { }

  ngOnInit(): void {
    this.delegation = this.data.resource;
    this.config = DelegationUpdateFormConfig.config(this.delegation);
  }

  public updateDelegation() {
    this.delegation.status = this.form.value.status;
    this._delegationService.update(this.delegation);
  }

  handleChange($event) {
    this.isChanged = $event.status !== this.delegation.status;
  }

}
