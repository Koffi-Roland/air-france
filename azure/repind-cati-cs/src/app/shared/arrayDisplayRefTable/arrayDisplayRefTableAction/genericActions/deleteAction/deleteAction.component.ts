import { Component, OnInit, Inject, Output, EventEmitter } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { EventActionService } from '../../../_services/eventAction.service';
import { CommonArrayDisplayRefTableService } from '../../../_services/commonArrayDisplayRefTable.service';
import { EventActionMessage, EventActionMessageEnum } from '../../../../models/EventActionMessage';
import { ArrayDisplayRefTableColumn } from '../../../_models/ArrayDisplayRefTableColumn';

@Component({
  selector: 'app-delete-action',
  templateUrl: './deleteAction.component.html',
  styleUrls: ['./deleteAction.component.scss']
})
export class DeleteActionComponent implements OnInit {

  /**
   * Type of data display (and which action will be used)
   * Exemple: PREFERENCE
   */
  public type: string;

  /**
   * Data to display into the modal (similar as the one of tableRef, use same service)
   */
  public dataToDisplay: Array<ArrayDisplayRefTableColumn>;

  /**
   * Construct the modal, check if in options pass some Key are available to use the datas
   * @param data Data to pass to modal
   * @param dialogRef Type of modal to open
   * @param eventActionService Service to send event
   */
  constructor(@Inject(MAT_DIALOG_DATA) public data: any,
    public dialogRef: MatDialogRef<DeleteActionComponent>, private eventActionService: EventActionService) {

    this.type = CommonArrayDisplayRefTableService.getValueByKey('type', data.options);
    this.dataToDisplay = CommonArrayDisplayRefTableService.getNotNullKeyForObject(this.data.element, false);

  }

  ngOnInit() {
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

  /**
   * Send the Event 'actionSentToBeApply' to execute the action!
   */
  submit() {
    this.eventActionService.actionSentToBeApply(new EventActionMessage(EventActionMessageEnum.DELETE, this.data.element));
  }

}
