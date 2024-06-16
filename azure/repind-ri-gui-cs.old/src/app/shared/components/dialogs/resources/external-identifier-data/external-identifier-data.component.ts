import { Component, OnInit, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { ExternalIdentifierData } from '../../../../../shared/models/common/external-identifier-data';

@Component({
  selector: 'app-external-identifier-data',
  templateUrl: './external-identifier-data.component.html',
  styleUrls: ['./external-identifier-data.component.scss']
})
export class ExternalIdentifierDataComponent implements OnInit {

  public externalIdData: ExternalIdentifierData[] = [];

  constructor(public dialogRef: MatDialogRef<ExternalIdentifierDataComponent>, @Inject(MAT_DIALOG_DATA) public data: any) { }

  ngOnInit() {
    this.externalIdData = this.data.extIdData;
  }

}
