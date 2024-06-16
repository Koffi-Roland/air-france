import { Component, OnInit, ViewChild, OnDestroy } from '@angular/core';
import { PermissionService } from '../../../core/services/permission.service';
import { PermissionQuestionService } from '../../../core/services/permission-question.service';
import { Router, ActivatedRoute } from '@angular/router';
import { ArrayDisplayRefTableOption } from 'src/app/shared/arrayDisplayRefTable/_models/ArrayDisplayRefTableOption';
import { Subscription } from 'rxjs';
import { ValidatorsCustom } from 'src/app/shared/widgets/validators/validators-custom.component';
import { EventActionService } from 'src/app/shared/arrayDisplayRefTable/_services/eventAction.service';
import { EventActionMessageEnum, EventActionMessage } from 'src/app/shared/models/EventActionMessage';
import { PermissionConfig } from './_config/PermissionConfig';
import { ErrorService } from 'src/app/core/services/error.service';

@Component({
  selector: 'app-permission',
  templateUrl: './permission.component.html',
  styleUrls: ['./permission.component.css']
})
export class PermissionComponent implements OnInit, OnDestroy {

  list: any;
  option: ArrayDisplayRefTableOption;
  customOption: any;
  subscription: Subscription;

  constructor(private route: ActivatedRoute, private eventActionService: EventActionService, private errorService: ErrorService,
    private validatorsCustom: ValidatorsCustom, private permissionQuestionService: PermissionQuestionService,
    private permissionService: PermissionService) {
    /**
     * Watch event actionSentToBeApply
     */
    this.subscription = eventActionService.actionSentToBeApply$.subscribe(
      message => {
        // If Event is type 'CREATE' -> call the service and create data
        if (message.message === EventActionMessageEnum.CREATE) {
          permissionQuestionService.postPermissionQuestion(message.data).then(res => {
            if (res && res.status && !res.ok) {
              this.errorService.displayDefaultError(res);
            } else {
              this.refreshData('EDIT_CREATED', { type: 'QUESTION' });
            }
          });
        }
        // If Event is type 'UPDATE' -> call the service and update data
        if (message.message === EventActionMessageEnum.UPDATE) {
          if (message.data.isAssociation) {
            permissionService.postPermission(message.data.value).then(res => {
              if (res && res.status && !res.ok) {
                this.errorService.displayDefaultError(res);
              } else {
                this.refreshData('EDIT_UPDATED', { type: 'QUESTION' });
              }
            });
          } else {
            permissionQuestionService.updatePermissionQuestion(message.data.id, message.data).then(res => {
              if (res && res.status && !res.ok) {
                this.errorService.displayDefaultError(res);
              } else {
                this.refreshData('EDIT_UPDATED', { type: 'QUESTION' });
              }
            });
          }
        }
        // If Event is type 'DELETE' -> call the service and delete data
        if (message.message === EventActionMessageEnum.DELETE) {
          permissionQuestionService.deletePermissionQuestion(message.data.id).then(res => {
            if (res && res.status && !res.ok) {
              if (res.status === 423) {
                this.errorService.displayDefaultError('CANT_REMOVE_COM_PREF_MESSAGE', { type: 'QUESTION' });
              } else {
                this.errorService.displayDefaultError(res);
              }
            } else {
              this.refreshData('EDIT_REMOVED', { type: 'QUESTION' });
            }
          });
        }
        // If Event is type 'REFRESH' -> refresh data
        if (message.message === EventActionMessageEnum.REFRESH) {
          if (message.data && message.data.status && !message.data.ok) {
            this.errorService.displayDefaultError(message.data);
          } else {
            this.refreshData('PERMISSION_UPDATED_MESSAGE');
          }
        }

      });
  }

  /**
   * Get data from route and Option defined in PREFERENCE_DATA.config
   */
  ngOnInit() {
    this.list = this.route.snapshot.data['permission'];

    this.permissionService.listComPrefDgt = this.route.snapshot.data['communicationsPreferencesDgt'];

    const config = new PermissionConfig(this.permissionService.listComPrefDgt, this.permissionService.getPermission.bind(this.permissionService));
    this.option = config.option;
  }

  ngOnDestroy() {
    this.subscription.unsubscribe();
  }

  /**
   * Send event to Refresh Data
   */
  refreshData(message?: string, parameters?: any) {
    return this.permissionQuestionService.getPermissionsQuestion(true).then(data => {
      this.eventActionService.actionExecuted(new EventActionMessage(EventActionMessageEnum.REFRESH, data));
      if (message) {
        this.errorService.displayDefaultError(message, parameters);
      }
    });
  }


}
