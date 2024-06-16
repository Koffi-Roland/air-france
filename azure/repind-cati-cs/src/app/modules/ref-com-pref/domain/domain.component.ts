import { Component, OnInit, ViewChild, OnDestroy } from '@angular/core';
import { ArrayDisplayRefTableOption } from '../../../shared/arrayDisplayRefTable/_models/ArrayDisplayRefTableOption';
import { Router, ActivatedRoute } from '@angular/router';
import { EventActionService } from '../../../shared/arrayDisplayRefTable/_services/eventAction.service';
import { EventActionMessageEnum, EventActionMessage } from '../../../shared/models/EventActionMessage';
import { DomainConfig } from './_config/DomainConfig';
import { DomainService } from '../../../core/services/domain.service';
import { ValidatorsCustom } from '../../../shared/widgets/validators/validators-custom.component';
import { Subscription } from 'rxjs';
import { ErrorService } from 'src/app/core/services/error.service';

@Component({
  selector: 'app-domain',
  templateUrl: './domain.component.html',
  styleUrls: ['./domain.component.scss']
})
export class DomainComponent implements OnInit, OnDestroy {

  list: any;
  option: ArrayDisplayRefTableOption;
  customOption: any;
  subscription: Subscription;

  constructor(private route: ActivatedRoute, private eventActionService: EventActionService, private errorService: ErrorService,
    private validatorsCustom: ValidatorsCustom, private domainService: DomainService) {
    /**
     * Watch event actionSentToBeApply
     */
    this.subscription = eventActionService.actionSentToBeApply$.subscribe(
      message => {
        // If Event is type 'CREATE' -> call the service and create data
        if (message.message === EventActionMessageEnum.CREATE) {
          domainService.postDomain(message.data).then(res => {
            if (res && res.status && !res.ok) {
              this.errorService.displayDefaultError(res);
            } else {
              this.refreshData('EDIT_CREATED', { type: 'DOMAIN' });
            }
          });
        }
        // If Event is type 'UPDATE' -> call the service and update data
        if (message.message === EventActionMessageEnum.UPDATE) {
          domainService.updateDomain(message.data.codeDomain, message.data).then(res => {
            if (res && res.status && !res.ok) {
              this.errorService.displayDefaultError(res);
            } else {
              this.refreshData('EDIT_UPDATED', { type: 'DOMAIN' });
            }
          });
        }
        // If Event is type 'DELETE' -> call the service and delete data
        if (message.message === EventActionMessageEnum.DELETE) {
          domainService.deleteDomain(message.data.codeDomain).then(res => {
            if (res && res.status && !res.ok) {
              if (res.status === 423) {
                this.errorService.displayDefaultError('CANT_REMOVE_COM_PREF_MESSAGE', { type: 'DOMAIN' });
              } else {
                this.errorService.displayDefaultError(res);
              }
            } else {
              this.refreshData('EDIT_REMOVED', { type: 'DOMAIN' });
            }
          });
        }

      });
  }

  /**
   * Get data from route and Option defined in PREFERENCE_DATA.config
   */
  ngOnInit() {
    this.list = this.route.snapshot.data['domain'];

    this.setValidator();

    this.option = DomainConfig.option;

  }

  setValidator() {

    const listCodeDomain = this.list.map(function (a) {
      return a.codeDomain;
    });

    ValidatorsCustom.list = listCodeDomain;

  }

  ngOnDestroy() {
    this.subscription.unsubscribe();
  }


  /**
   * Send event to Refresh Data
   */
  refreshData(message: string, parameters?: any) {
    return this.domainService.getDomains(true).then(data => {
      this.eventActionService.actionExecuted(new EventActionMessage(EventActionMessageEnum.REFRESH, data));
      this.list = data;
      this.setValidator();
      this.errorService.displayDefaultError(message, parameters);
    });
  }

}
