import { Component, OnInit, ViewChild, OnDestroy } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { EventActionService } from '../../../shared/arrayDisplayRefTable/_services/eventAction.service';
import { EventActionMessageEnum, EventActionMessage } from '../../../shared/models/EventActionMessage';
import { GroupTypeConfig } from './_config/GroupTypeConfig';
import { GroupTypeService } from '../../../core/services/group-type.service';
import { ValidatorsCustom } from '../../../shared/widgets/validators/validators-custom.component';
import { ArrayDisplayRefTableOption } from '../../../shared/arrayDisplayRefTable/_models/ArrayDisplayRefTableOption';
import { Subscription } from 'rxjs';
import { ErrorService } from 'src/app/core/services/error.service';



@Component({
  selector: 'app-group-type',
  templateUrl: './group-type.component.html',
  styleUrls: ['./group-type.component.css']
})
export class GroupTypeComponent implements OnInit, OnDestroy {

  list: any;
  option: ArrayDisplayRefTableOption;
  customOption: any;
  subscription: Subscription;

  constructor(private route: ActivatedRoute, private eventActionService: EventActionService, private errorService: ErrorService,
    private validatorsCustom: ValidatorsCustom, private groupTypeService: GroupTypeService) {
    /**
     * Watch event actionSentToBeApply
     */
    this.subscription = eventActionService.actionSentToBeApply$.subscribe(
      message => {
        // If Event is type 'CREATE' -> call the service and create data
        if (message.message === EventActionMessageEnum.CREATE) {
          groupTypeService.postGroupType(message.data).then(res => {
            if (res && res.status && !res.ok) {
              this.errorService.displayDefaultError(res);
            } else {
              this.refreshData('EDIT_CREATED', { type: 'GROUP_TYPE' });
            }
          });
        }
        // If Event is type 'UPDATE' -> call the service and update data
        if (message.message === EventActionMessageEnum.UPDATE) {
          groupTypeService.updateGroupType(message.data.codeGType, message.data).then(res => {
            if (res && res.status && !res.ok) {
              this.errorService.displayDefaultError(res);
            } else {
              this.refreshData('EDIT_UPDATED', { type: 'GROUP_TYPE' });
            }
          });
        }
        // If Event is type 'DELETE' -> call the service and delete data
        if (message.message === EventActionMessageEnum.DELETE) {
          groupTypeService.deleteGroupType(message.data.codeGType).then(res => {
            if (res && res.status && !res.ok) {
              if (res.status === 423) {
                this.errorService.displayDefaultError('CANT_REMOVE_COM_PREF_MESSAGE', { type: 'GROUP_TYPE' });
              } else {
                this.errorService.displayDefaultError(res);
              }
            } else {
              this.refreshData('EDIT_REMOVED', { type: 'GROUP_TYPE' });
            }
          });
        }

      });
  }

  /**
   * Get data from route and Option defined in PREFERENCE_DATA.config
   */
  ngOnInit() {
    this.list = this.route.snapshot.data['groupType'];

    this.setValidator();

    this.option = GroupTypeConfig.option;

  }

  ngOnDestroy() {
    this.subscription.unsubscribe();
  }

  setValidator() {
    const listCodeGType = this.list.map(function (a) {
      return a.codeGType;
    });

    ValidatorsCustom.list = listCodeGType;

  }

  /**
   * Send event to Refresh Data
   */
  refreshData(message: string, parameters?: any) {
    return this.groupTypeService.getGroupTypes(true).then(data => {
      this.eventActionService.actionExecuted(new EventActionMessage(EventActionMessageEnum.REFRESH, data));
      this.list = data;
      this.setValidator();
      this.errorService.displayDefaultError(message, parameters);
    });
  }

}
