import { Component, Inject, OnInit, ViewChild } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { TranslateService } from '@ngx-translate/core';
import { SelectTableComponent } from 'src/app/shared/components/select-table/select-table.component';
import { EventActionMessage, EventActionMessageEnum } from 'src/app/shared/models/EventActionMessage';
import { TableLinkedOptionConfig } from 'src/app/shared/models/TableLinkedOptionConfig';
import { CommonArrayDisplayRefTableService } from '../../../_services/commonArrayDisplayRefTable.service';
import { EventActionService } from '../../../_services/eventAction.service';
import { CreateUpdateActionComponent } from '../createUpdateAction/createUpdateAction.component';

@Component({
  selector: 'app-add-remove-many-action',
  templateUrl: './addRemoveManyAction.component.html',
  styleUrls: ['./addRemoveManyAction.component.scss']
})
export class AddRemoveManyActionComponent implements OnInit {
  /**
   * Type of data display 
   * Exemple: PREFERENCE
   */
  public type: string;

  /**
   * Label to display and to translate using i18n
   * Exemple: UPDATE
   */
  public label: string;

  public idForUpdate: string;

  public columnId: string;

  public idsAssociatedToRowId: any[];

  public config: TableLinkedOptionConfig;

  @ViewChild('associatedTable')
  associatedTable: SelectTableComponent;

  /**
   * Construct the modal, check if in options pass some Key are available to use the datas
   * @param data Data to pass to modal
   * @param dialogRef Type of modal to open
   * @param eventActionService Service to send event
   */
  constructor(private translateService: TranslateService,
    @Inject(MAT_DIALOG_DATA) public data: any,
    public dialogRef: MatDialogRef<CreateUpdateActionComponent>,
    private eventActionService: EventActionService) { }

  ngOnInit() {

    this.type = CommonArrayDisplayRefTableService.getValueByKey('type', this.data.options);
    this.columnId = CommonArrayDisplayRefTableService.getValueByKey('id', this.data.options);
    this.config = CommonArrayDisplayRefTableService.getValueByKey('tableLink', this.data.options);

    this.label = this.data.label;
    this.idForUpdate = this.data.element[this.columnId];

    this.idsAssociatedToRowId = JSON.parse(JSON.stringify(this.data.element.idList));
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

  /**
   * Send the Event 'actionSentToBeApply' to execute the action!
   */
  submit() {
    let dataToUpdate = {}
    dataToUpdate[this.config.listNameForRequestBody] = this.associatedTable.selection.selected.map(s => s[this.config.tableLinkedIdName]);
    dataToUpdate[this.config.callingTableIdName] = this.idForUpdate;
    this.eventActionService.actionSentToBeApply(new EventActionMessage(EventActionMessageEnum.UPDATE, { isAssociation: true, value: dataToUpdate }));
    this.dialogRef.close(true);
  }

}
