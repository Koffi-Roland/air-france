import { Component, OnInit, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { WarningModalData } from './_models/warningModalData';
import { UntypedFormGroup, UntypedFormBuilder } from '@angular/forms';

@Component({
  selector: 'app-warning-modal',
  templateUrl: './warning-modal.component.html',
  styleUrls: ['./warning-modal.component.scss']
})
export class WarningModalComponent implements OnInit {
  title: any;
  body: any;

  checkboxValidation: UntypedFormGroup = null;

  constructor(
    public dialogRef: MatDialogRef<WarningModalComponent>, @Inject(MAT_DIALOG_DATA) public data: WarningModalData, fb: UntypedFormBuilder) {
    this.title = this.data.title;
    this.body = this.data.body;
    if (this.body.checkboxValidationText) {
      this.checkboxValidation = fb.group({
        enableButton: false,
        floatLabel: 'auto',
      });
      }
    }

  ngOnInit() {
  }

}
