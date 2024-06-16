import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Component, OnInit, Inject } from '@angular/core';
import { DialogService } from '../../../../core/services/dialogs/dialog.service';
import { ResourceType } from '../../../models/resources/resource-type';

@Component({
  selector: 'app-resources-not-found-dialog',
  templateUrl: './resources-not-found-dialog.component.html',
  styleUrls: ['./resources-not-found-dialog.component.scss']
})
export class ResourcesNotFoundDialogComponent implements OnInit {

  private resourceType: ResourceType;
  private isCreatable: boolean;

  constructor(private dialogRef: MatDialogRef<ResourcesNotFoundDialogComponent>, @Inject(MAT_DIALOG_DATA) private data: any,
    private dialogService: DialogService) { }

  ngOnInit() {
    this.resourceType = this.data.type;
    this.isCreatable = this.dialogService.isCreatableResource(this.resourceType) && !this.data.individualTorW;
  }

  public close(): void {
    this.dialogRef.close();
  }

  public create(): void {
    this.dialogRef.close();
    this.dialogService.openCreateDialog(this.resourceType, '75%', '90%');
  }

}
