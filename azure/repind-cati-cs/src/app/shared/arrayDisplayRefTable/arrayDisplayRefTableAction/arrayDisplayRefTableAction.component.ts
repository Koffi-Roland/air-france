import { Component, OnInit, Input, TemplateRef } from '@angular/core';
import { ComponentType } from '@angular/cdk/portal';
import { ArrayDisplayRefTableActionOption, ArrayDisplayRefTableActionOptionConfig } from '../_models/ArrayDisplayRefTableActionOption';
import { MatDialog } from '@angular/material/dialog';
import { TooltipPosition } from '@angular/material/tooltip';
import { WarningIcon } from '../../models/WarningIcon';
import { CommonArrayDisplayRefTableService } from '../_services/commonArrayDisplayRefTable.service';

@Component({
  selector: 'app-array-display-ref-table-action',
  templateUrl: './arrayDisplayRefTableAction.component.html',
  styleUrls: ['./arrayDisplayRefTableAction.component.scss']
})
export class ArrayDisplayRefTableActionComponent implements OnInit {

  /**
   * All options for each action (each icon could be an action and have option)
   */
  @Input()
  public options: Array<ArrayDisplayRefTableActionOption>;

  /**
   * Data of the row linked to these actions
   */
  @Input()
  public data: any;

  /**
   * Value of the ID of the data
   */
  @Input()
  public id: string;

  /**
   * Data of the row linked to these actions
   */
  @Input()
  public warning: WarningIcon[];

  constructor(public dialog: MatDialog) { }

  ngOnInit() {
  }

  /**
   * Method to execute the action, here open a modal and pass mandatory datas
   * @param option Option to provide to the action
   */
  public async exec(option: ArrayDisplayRefTableActionOption) {

    const retrieveFunction = CommonArrayDisplayRefTableService.getValueByKey('retrieveFunction', option.configs);

    if (retrieveFunction) {
      await this.addIdListToData(retrieveFunction);
    }

    if (option.modal) {
      const dialogRef = this.dialog.open(option.modal, {
        width: '75vw',
        data: { element: this.data, options: option.configs, label: option.label },
        autoFocus: false
      });
    }
  }

  /**
   * add an attribute 'idList' which contains a list of id, to data object
   * @param retriever function to get all id linked to data
   */
  private async addIdListToData(retriever: Function) {
    this.data.idList = await retriever(this.id);
  }
}
