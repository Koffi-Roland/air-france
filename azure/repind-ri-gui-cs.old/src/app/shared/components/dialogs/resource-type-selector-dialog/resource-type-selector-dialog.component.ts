import { UntypedFormControl, UntypedFormGroup, Validators } from '@angular/forms';
import { Component, OnInit } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';
import { DialogService } from '../../../../core/services/dialogs/dialog.service';
import { ResourceTypeUtil, ResourceType } from '../../../models/resources/resource-type';

@Component({
  selector: 'app-resource-type-selector-dialog',
  templateUrl: './resource-type-selector-dialog.component.html',
  styleUrls: ['./resource-type-selector-dialog.component.scss']
})
export class ResourceTypeSelectorDialogComponent implements OnInit {

  public resources = [];

  public formGroup: UntypedFormGroup;
  public typeCtrl: UntypedFormControl;

  constructor(public dialogRef: MatDialogRef<ResourceTypeSelectorDialogComponent>, private dialogService: DialogService) { }

  ngOnInit() {
    this.initializeForm();
    this.fillResourceLabels();
  }

  public cancel(): void {
    this.dialogRef.close();
  }

  public openCreateDialog(): void {
    this.dialogRef.close();
    this.dialogService.openCreateDialog(this.typeCtrl.value, '75%', '90%');
  }

  private fillResourceLabels(): void {
    Object.keys(ResourceType).map(key => {
      const label = ResourceTypeUtil.labelOf(ResourceType[key]);
      if (label) {
        const resource = {
          type: ResourceType[key],
          label: label,
          isCreatable: this.dialogService.isCreatableResource(ResourceType[key])
        };
        this.resources.push(resource);
      }
    });
  }

  private initializeForm(): void {
    this.typeCtrl = new UntypedFormControl('', Validators.required);
    this.formGroup = new UntypedFormGroup({
      type: this.typeCtrl
    });
  }

}
