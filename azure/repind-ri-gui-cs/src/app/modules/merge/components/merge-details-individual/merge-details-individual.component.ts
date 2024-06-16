import { Component, Input, Output, EventEmitter } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';
import { CommonService } from '../../../../core/services/common.service';

@Component({
  selector: 'app-individual-merge-details-individual',
  templateUrl: './merge-details-individual.component.html',
  styleUrls: ['./merge-details-individual.component.scss']
})
export class MergeDetailsIndividualComponent {

  @Input() individual: any;
  @Input() isSelected: boolean;
  @Input() sourceOrTarget: string;
  @Output() clickOnSelectIndividual = new EventEmitter();

  constructor(private translate: TranslateService, public common: CommonService) { }

  selectIndividual() {
    this.clickOnSelectIndividual.emit(this.individual.sgin);
  }

}
