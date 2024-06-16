import { Component, OnInit, ViewChild, OnDestroy } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { ArrayDisplayRefTableOption } from '../../../shared/arrayDisplayRefTable/_models/ArrayDisplayRefTableOption';
import { EventActionService } from '../../../shared/arrayDisplayRefTable/_services/eventAction.service';
import { EventActionMessageEnum, EventActionMessage } from '../../../shared/models/EventActionMessage';
import { ComPrefDGTConfig } from './_config/ComPrefDGTConfig';
import { CommunicationPreferencesDgtService } from '../../../core/services/communication-preferences-dgt.service';
import { ValidatorsCustom } from '../../../shared/widgets/validators/validators-custom.component';
import { OptionItem } from '../../../shared/models/contents/option-item';
import { Subscription } from 'rxjs';
import { ErrorService } from 'src/app/core/services/error.service';

@Component({
  selector: 'app-communication-preferences-dgt',
  templateUrl: './communication-preferences-dgt.component.html',
  styleUrls: ['./communication-preferences-dgt.component.css']
})
export class CommunicationPreferencesDgtComponent implements OnInit, OnDestroy {

  list: any;
  option: ArrayDisplayRefTableOption;
  customOption: any;
  subscription: Subscription;

  constructor(private route: ActivatedRoute, private eventActionService: EventActionService, private errorService: ErrorService,
    private validatorsCustom: ValidatorsCustom, private comPrefDGTService: CommunicationPreferencesDgtService) {
    /**
     * Watch event actionSentToBeApply
     */
    this.subscription = eventActionService.actionSentToBeApply$.subscribe(
      message => {
        // If Event is type 'CREATE' -> call the service and create data
        if (message.message === EventActionMessageEnum.CREATE) {
          comPrefDGTService.postCommunicationPreferencesDgt(message.data).then(res => {
            if (res && res.status && !res.ok) {
              this.errorService.displayDefaultError(res);
            } else {
              this.refreshData('EDIT_CREATED', { type: 'COMBINATION' });
            }
          });
        }
        // If Event is type 'UPDATE' -> call the service and update data
        if (message.message === EventActionMessageEnum.UPDATE) {
          comPrefDGTService.updateCommunicationPreferencesDgt(message.data.refComPrefDgtId, message.data).then(res => {
            if (res && res.status && !res.ok) {
              this.errorService.displayDefaultError(res);
            } else {
              this.refreshData('EDIT_UPDATED', { type: 'COMBINATION' });
            }
          });
        }
        // If Event is type 'DELETE' -> call the service and delete data
        if (message.message === EventActionMessageEnum.DELETE) {
          comPrefDGTService.deleteCommunicationPreferencesDgt(message.data.refComPrefDgtId).then(res => {
            if (res && res.status && !res.ok) {
              if (res.status === 423) {
                this.errorService.displayDefaultError('CANT_REMOVE_COMBINATION_MESSAGE', { type: 'COMBINATION' });
              } else {
                this.errorService.displayDefaultError(res);
              }
            } else {
              this.refreshData('EDIT_REMOVED', { type: 'COMBINATION' });
            }
          });
        }

      });
  }

  /**
   * Get data from route and Option defined in ComPrefDGTConfig.config
   */
  ngOnInit() {
    this.list = this.route.snapshot.data['communicationsPreferencesDgt'];

    ValidatorsCustom.list = this.list;

    const allTypes: Array<OptionItem> = this.getType();

    const allDomains: Array<OptionItem> = this.getDomain();

    const allGTypes: Array<OptionItem> = this.getGroupTypes();

    const compref = new ComPrefDGTConfig(allTypes, allDomains, allGTypes);

    this.option = compref.option;

  }

  ngOnDestroy() {
    this.subscription.unsubscribe();
  }

  /**
   * Send event to Refresh Data
   */
  refreshData(message: string, parameters?: any) {
    return this.comPrefDGTService.getCommunicationPreferencesDgts(true).then(data => {
      this.eventActionService.actionExecuted(new EventActionMessage(EventActionMessageEnum.REFRESH, data));
      ValidatorsCustom.list = data;
      this.errorService.displayDefaultError(message, parameters);
    });
  }

  /**
   * Get types from router
   */
  getType(): Array<OptionItem> {

    const listType = this.route.snapshot.data['communicationsType'];

    const allTypes: Array<OptionItem> = new Array<OptionItem>();

    listType.map(function (a) {
      const optItem = new OptionItem(a.codeType);
      allTypes.push(optItem);
    });

    return allTypes;
  }

  /**
   * Get domains from router
   */
  getDomain(): Array<OptionItem> {

    const listDomain = this.route.snapshot.data['domains'];

    const allDomains: Array<OptionItem> = new Array<OptionItem>();

    listDomain.map(function (a) {
      const optItem = new OptionItem(a.codeDomain);
      allDomains.push(optItem);
    });

    return allDomains;
  }

  /**
   * Get group types from router
   */
  getGroupTypes(): Array<OptionItem> {

    const listGType = this.route.snapshot.data['groupTypes'];

    const allGTypes: Array<OptionItem> = new Array<OptionItem>();

    listGType.map(function (a) {
      const optItem = new OptionItem(a.codeGType);
      allGTypes.push(optItem);
    });

    return allGTypes;
  }

}
