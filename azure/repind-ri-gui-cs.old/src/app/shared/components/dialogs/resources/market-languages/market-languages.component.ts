import { Component, OnInit, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MarketLanguage } from '../../../../../shared/models/common/market-language';
import { CommonService } from '../../../../../core/services/common.service';

@Component({
  selector: 'app-market-languages',
  templateUrl: './market-languages.component.html',
  styleUrls: ['./market-languages.component.scss']
})
export class MarketLanguagesComponent implements OnInit {

  public marketLanguages: MarketLanguage[] = [];

  constructor(public dialogRef: MatDialogRef<MarketLanguagesComponent>, @Inject(MAT_DIALOG_DATA) public data: any,
  public common: CommonService) { }

  ngOnInit() {
    this.marketLanguages = this.data.market;
  }

}
