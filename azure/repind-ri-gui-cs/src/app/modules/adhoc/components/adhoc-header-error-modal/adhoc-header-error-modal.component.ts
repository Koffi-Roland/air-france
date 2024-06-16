import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';

@Component({
  selector: 'app-adhoc-header-error-modal',
  templateUrl: './adhoc-header-error-modal.component.html',
  styleUrls: ['./adhoc-header-error-modal.component.scss']
})
export class AdhocHeaderErrorModalComponent implements OnInit {

  constructor(
    public dialogRef: MatDialogRef<AdhocHeaderErrorModalComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
  ) { }

  ngOnInit() {
  }
}
