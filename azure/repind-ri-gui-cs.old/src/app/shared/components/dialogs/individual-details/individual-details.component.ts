import { Component, OnInit, Input, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Individual } from '../../../models/individual/individual';
import { ReferenceData } from '../../../models/references/ReferenceData';
import { CommonService } from '../../../../core/services/common.service';
import { RefProviderService } from '../../../../core/services/references/ref-provider.service';
import { ReferenceDataType } from '../../../models/references/ReferenceDataType.enum';

@Component({
  selector: 'app-individual-details',
  templateUrl: './individual-details.component.html',
  styleUrls: ['./individual-details.component.scss']
})
export class IndividualDetailsComponent implements OnInit {

  public currentLocal = '';

  public individual: Individual;

  constructor(public dialogRef: MatDialogRef<IndividualDetailsComponent>, private _refProviderService: RefProviderService,
    @Inject(MAT_DIALOG_DATA) public data: any, public common: CommonService) { }

  ngOnInit() {
    this.currentLocal = this.common.getCurrentLocal();
    this.individual = this.data.individual;
  }

  close(): void {
    this.dialogRef.close();
  }

}
