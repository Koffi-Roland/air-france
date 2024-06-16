import { Component, OnInit, Input } from '@angular/core';
import { MergeAccordionData } from '../../dataFormat/mergeAccordionData';

@Component({
  selector: 'app-merge-accordion-data',
  templateUrl: './merge-accordion-data.component.html',
  styleUrls: ['./merge-accordion-data.component.scss']
})
export class MergeAccordionDataComponent implements OnInit {
  @Input() public accordionDatas: MergeAccordionData;

  ngOnInit() {
  }

  stopPropagation(event: Event) {
    event.stopPropagation();
  }

  getValue() {
    if (this.accordionDatas) {
      if (this.accordionDatas.options.selected) {
        return this.accordionDatas.id;
      }
    }
  }

}
