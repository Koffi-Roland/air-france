import { Component, OnInit, ViewChild, OnDestroy } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { EventActionService } from '../../../shared/arrayDisplayRefTable/_services/eventAction.service';
import { EventActionMessageEnum, EventActionMessage } from '../../../shared/models/EventActionMessage';
import { CommunicationTypeConfig } from './_config/CommunicationTypeConfig';
import { CommunicationTypeService } from '../../../core/services/communication-type.service';
import { ValidatorsCustom } from '../../../shared/widgets/validators/validators-custom.component';
import { ArrayDisplayRefTableOption } from '../../../shared/arrayDisplayRefTable/_models/ArrayDisplayRefTableOption';
import { Subscription } from 'rxjs';
import { ErrorService } from 'src/app/core/services/error.service';


@Component({
  selector: 'app-communication-type',
  templateUrl: './communication-type.component.html',
  styleUrls: ['./communication-type.component.css']
})
export class CommunicationTypeComponent implements OnInit, OnDestroy {

  list: any;
  option: ArrayDisplayRefTableOption;
  customOption: any;
  subscription: Subscription;

  constructor(private route: ActivatedRoute, private eventActionService: EventActionService, private errorService: ErrorService,
    private validatorsCustom: ValidatorsCustom, private cTypeService: CommunicationTypeService) {
    /**
     * Watch event actionSentToBeApply
     */
    this.subscription = eventActionService.actionSentToBeApply$.subscribe(
      message => {
        // If Event is type 'CREATE' -> call the service and create data
        if (message.message === EventActionMessageEnum.CREATE) {
          cTypeService.postCommunicationType(message.data).then(res => {
            if (res && res.status && !res.ok) {
              this.errorService.displayDefaultError(res);
            } else {
              this.refreshData('EDIT_CREATED', { type: 'TYPE' });
            }
          });
        }
        // If Event is type 'UPDATE' -> call the service and update data
        if (message.message === EventActionMessageEnum.UPDATE) {
          cTypeService.updateCommunicationType(message.data.codeType, message.data).then(res => {
            if (res && res.status && !res.ok) {
              this.errorService.displayDefaultError(res);
            } else {
              this.refreshData('EDIT_UPDATED', { type: 'TYPE' });
            }
          });
        }
        // If Event is type 'DELETE' -> call the service and delete data
        if (message.message === EventActionMessageEnum.DELETE) {
          cTypeService.deleteCommunicationType(message.data.codeType).then(res => {
            if (res && res.status && !res.ok) {
              if (res.status === 423) {
                this.errorService.displayDefaultError('CANT_REMOVE_COM_PREF_MESSAGE', { type: 'TYPE' });
              } else {
                this.errorService.displayDefaultError(res);
              }
            } else {
              this.refreshData('EDIT_REMOVED', { type: 'TYPE' });
            }
          });
        }

      });
  }

  /**
   * Get data from route and Option defined in PREFERENCE_DATA.config
   */
  ngOnInit() {
    this.list = this.route.snapshot.data['communicationsType'];

    this.setValidator();

    this.option = CommunicationTypeConfig.option;

  }

  ngOnDestroy() {
    this.subscription.unsubscribe();
  }

  setValidator() {

    const listCodeType = this.list.map(function (a) {
      return a.codeType;
    });

    ValidatorsCustom.list = listCodeType;

  }

  /**
   * Send event to Refresh Data
   */
  refreshData(message: string, parameters?: any) {
    return this.cTypeService.getCommunicationTypes(true).then(data => {
      this.eventActionService.actionExecuted(new EventActionMessage(EventActionMessageEnum.REFRESH, data));
      this.list = data;
      this.setValidator();
      this.errorService.displayDefaultError(message, parameters);
    });
  }

}
