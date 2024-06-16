import { Component, Input, OnInit, ViewChildren, QueryList } from '@angular/core';
import { MergeAccordionDataComponent } from '../merge-accordeon-data/merge-accordion-data.component';
import { MergeGroupData, MergeGroupDataSelected } from '../../dataFormat/mergeGroupData';
import { PatternMergeAccordionData } from '../../dataFormat/patternMergeAccordionData';
import { MergeTransformService } from '../../../../core/services/transform/mergeTransform.service';

@Component({
  selector: 'app-merge-group-data',
  templateUrl: './merge-group-data.component.html',
  styleUrls: ['./merge-group-data.component.scss']
})
export class MergeGroupDataComponent implements OnInit {

  @Input() mergeGroupDataConfig: MergeGroupData;
  @Input() sourceData?: any;
  // Use targetData input to pass only one bloc of Data
  @Input() targetData: any;
  public genericDataFormatted: any;
  private pattern: PatternMergeAccordionData;
  public group: Array<string> = [];
  public isMonobloc: boolean;

  @ViewChildren(MergeAccordionDataComponent) child: QueryList<MergeAccordionDataComponent>;

  ngOnInit() {
    this.pattern = new PatternMergeAccordionData(this.mergeGroupDataConfig);
    this.isMonobloc = this.isOptionMonobloc();
    if (this.isMonobloc) {
      this.genericDataFormatted = MergeTransformService.transformToGenericDataByBlock(this.targetData, this.pattern);
    } else {
      this.genericDataFormatted = MergeTransformService.transformToGenericDataByBlockOfTwo(this.sourceData,
        this.targetData, this.pattern, this.mergeGroupDataConfig.options.functionSort);
        this.initRadioValue(this.mergeGroupDataConfig.options.type);
    }
  }

  initRadioValue(type) {
    if (type === 'radio') {
      for (let index = 0; index < this.genericDataFormatted.length; index++) {
        const element = this.genericDataFormatted[index];
        if (element.blocTarget && element.blocTarget.options.selected) {
          this.group[index] = element.blocTarget.id;
        } else if (element.blocSource && element.blocSource.options.selected) {
          this.group[index] = element.blocSource.id;
        }
      }
    }
  }

  getValue() {

    if (this.mergeGroupDataConfig.options.type === 'radio') {
      return new MergeGroupDataSelected(this.mergeGroupDataConfig.blocTitle, this.group);
    } else if (this.mergeGroupDataConfig.options.type === 'checkbox') {
      const array = [];
      this.child.forEach((child) => {
        const value = child.getValue();
        if (value) {
          array.push(value);
        }
      });
      return new MergeGroupDataSelected(this.mergeGroupDataConfig.blocTitle, array);
    }
  }

  /**
   * Check if 'monobloc' key is available in template
   */
  private isOptionMonobloc(): boolean {
    if (this.mergeGroupDataConfig.options.template == null) { return false; }
      for (const template of this.mergeGroupDataConfig.options.template) {
        if (template.key === 'monobloc') {
          return true;
        }
      }
      return false;
  }
}
