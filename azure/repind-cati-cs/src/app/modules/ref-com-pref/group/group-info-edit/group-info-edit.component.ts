import { Component, OnInit, Inject } from '@angular/core';
import { UntypedFormGroup, UntypedFormBuilder, Validators } from '@angular/forms';
import { Group } from '../group-product/_model/Group';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { GroupService } from 'src/app/core/services/group.service';
import { EventActionService } from 'src/app/shared/arrayDisplayRefTable/_services/eventAction.service';
import { EventActionMessage, EventActionMessageEnum } from 'src/app/shared/models/EventActionMessage';

@Component({
  selector: 'app-group-info-edit',
  templateUrl: './group-info-edit.component.html',
  styleUrls: ['./group-info-edit.component.scss']
})
export class GroupInfoEditComponent implements OnInit {

 /**
   * Type of data display (and which action will be used)
   * Exemple: PREFERENCE
   */
  public type: string;

  /**
   * Data to display into the modal (similar as the one of tableRef, use same service)
   */
  public groupForm: UntypedFormGroup;

  /**
   * Group to edit
   */
  public group: Group;

  action: string;

  /**
   * Construct the modal, check if in options pass some Key are available to use the datas
   * @param data Data to pass to modal
   * @param dialogRef Type of modal to open
   * @param eventActionService Service to send event
   */
  constructor(@Inject(MAT_DIALOG_DATA) public data: any,
    public dialogRef: MatDialogRef<GroupInfoEditComponent>, private eventActionService: EventActionService,
    private groupService: GroupService, private fb: UntypedFormBuilder) {

      this.group = new Group();

      this.action = this.data.label;

      this.groupForm = this.fb.group({
        code: ['', Validators.required],
        libelleEN: ['', Validators.required],
        libelleFR: ['']
      });

      if (this.action === 'UPDATE') {
        this.group = JSON.parse(JSON.stringify(this.data.element));
        this.groupForm.patchValue(this.group);
      }

   }

  ngOnInit() {
  }

  setValue(e, field) {
    if (e.checked) {
      this.group[field] = 'Y';
      if (field === 'mandatoryOption') {
        this.group.defaultOption = 'Y';
      }
    } else {
      this.group[field] = 'N';
    }
  }


  /**
   * Send the Event 'actionSentToBeApply' to execute the action!
   */
  submit() {
    const values = this.groupForm.value;
    this.group.code = values.code;
    this.group.libelleEN = values.libelleEN;
    this.group.libelleFR = values.libelleFR;

    if (!this.group.mandatoryOption) {
      this.group.mandatoryOption = 'N';
    }

    if (!this.group.defaultOption) {
      this.group.defaultOption = 'N';
    }

    delete this.group['idList'];

    if (this.action === 'UPDATE') {
      this.groupService.putGroupInfo(this.group.id, this.group).then(res => {
        this.eventActionService.actionSentToBeApply(new EventActionMessage(EventActionMessageEnum.REFRESH, res));
      });
    } else {
      this.groupService.postGroupInfo(this.group).then(res => {
        this.eventActionService.actionSentToBeApply(new EventActionMessage(EventActionMessageEnum.REFRESH, res));
      });
    }
  }
}
