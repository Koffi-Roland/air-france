import { Component, OnInit, Input } from '@angular/core';
import { MergeAccordionDataArray } from '../../dataFormat/mergeAccordionData';

@Component({
  selector: 'app-merge-accordion-data-subtitles',
  templateUrl: './merge-accordion-data-subtitles.component.html',
  styleUrls: ['./merge-accordion-data-subtitles.component.scss']
})
export class MergeAccordionDataSubtitlesComponent implements OnInit {
  @Input() public subtitles: any;

  ngOnInit() {
  }

  isArray(content) {
    return content instanceof MergeAccordionDataArray;
  }
}
