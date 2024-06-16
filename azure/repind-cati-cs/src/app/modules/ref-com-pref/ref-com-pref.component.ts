import { Component, OnInit } from '@angular/core';
import { Location } from '@angular/common';
import { TranslateService } from '@ngx-translate/core';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-ref-com-pref',
  templateUrl: './ref-com-pref.component.html',
  styleUrls: ['./ref-com-pref.component.scss']
})
export class RefComPrefComponent implements OnInit {

  constructor(private translate: TranslateService) { }

  ngOnInit() {
  }

}
