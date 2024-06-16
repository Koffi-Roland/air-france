import { Component, OnInit, Input } from '@angular/core';
import * as moment from 'moment';
import { MergeAccordionDataArray } from '../../dataFormat/mergeAccordionData';

@Component({
  selector: 'app-merge-accordion-data-content',
  templateUrl: './merge-accordion-data-content.component.html',
  styleUrls: ['./merge-accordion-data-content.component.scss']
})
export class MergeAccordionDataContentComponent implements OnInit {
  @Input() public content: any;

  ngOnInit() {
  }

  isArray(content) {
    return content instanceof MergeAccordionDataArray;
  }

  isDate(content) {
    return moment(content, 'YYYY-MM-DD HH:mm:ss', true).isValid();
  }

}
