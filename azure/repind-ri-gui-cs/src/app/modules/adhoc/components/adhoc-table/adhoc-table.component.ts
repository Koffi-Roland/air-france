import {Component, Input, OnInit} from '@angular/core';
import {ArrayDisplayRefTableOption} from '../../../../shared/arrayDisplayRefTable/_models/ArrayDisplayRefTableOption';
import {AdhocConfig} from './config/adhoc.config';
import {Observable} from 'rxjs-compat';
import {OptionItem} from '../../../../shared/models/contents/option-item';

@Component({
  selector: 'app-adhoc-table',
  templateUrl: './adhoc-table.component.html',
  styleUrls: ['./adhoc-table.component.scss']
})
export class AdhocTableComponent implements OnInit {

  public option: ArrayDisplayRefTableOption;

  @Input() airline!: 'AF' | 'KL';
  @Input() dataSource!: any;
  @Input() refreshAction$: Observable<any>;
  @Input() adhocReferences!: any;
  @Input() isInvalidTable = false;

  ngOnInit() {
    const config = new AdhocConfig(
      this.adhocReferences.subscriptionTypes.filter((item: OptionItem) => (item.value as string).includes(this.airline)),
      this.adhocReferences.civilities,
      this.adhocReferences.domains,
      this.adhocReferences.groupTypes,
      this.adhocReferences.status
    );
    this.option = config.option;
    this.option.headerClass = this.isInvalidTable ? 'table-error-header-bg' : '';
  }


}



