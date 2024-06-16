import { Component, OnDestroy, OnInit } from '@angular/core';
import { ArrayDisplayRefTableOption } from '../../../shared/arrayDisplayRefTable/_models/ArrayDisplayRefTableOption';
import { ActivatedRoute } from '@angular/router';
import { EventActionService } from '../../../shared/arrayDisplayRefTable/_services/eventAction.service';
import { PreferenceService } from '../../../core/services/preference.service';
import { EventActionMessageEnum, EventActionMessage } from '../../../shared/models/EventActionMessage';
import { PreferenceDataConfig } from './_config/PreferenceDataConfig';
import { ErrorService } from 'src/app/core/services/error.service';
import { Subscription } from 'rxjs';
import { ValidatorsCustom } from 'src/app/shared/widgets/validators/validators-custom.component';

@Component({
  selector: 'app-preference-data',
  templateUrl: './preference-data.component.html',
  styleUrls: ['./preference-data.component.scss']
})
export class PreferenceDataComponent implements OnInit, OnDestroy {

  list: any;
  option: ArrayDisplayRefTableOption;
  subscription: Subscription;

  constructor(private errorService: ErrorService, private route: ActivatedRoute, private eventActionService: EventActionService, private preferenceService: PreferenceService) {
    /**
     * Watch event actionSentToBeApply
     */
    this.subscription = eventActionService.actionSentToBeApply$.subscribe(
      message => {
        // If Event is type 'CREATE' -> call the service and create data
        if (message.message === EventActionMessageEnum.CREATE) {
          preferenceService.postPreferenceData(message.data).then(res => {
            if (res && res.status && !res.ok) {
              this.errorService.displayDefaultError(res);
            } else {
              this.refreshData('EDIT_CREATED', { type: 'PREFERENCE-DATA' });
            }
          });
        }

        // If Event is type 'UPDATE' -> call the service and update data
        if (message.message === EventActionMessageEnum.UPDATE) {
          preferenceService.updatePreferenceData(message.data.code, message.data).then(res => {
            if (res && res.status && !res.ok) {
              this.errorService.displayDefaultError(res);
            } else {
              this.refreshData('EDIT_UPDATED', { type: 'PREFERENCE-DATA' });
            }
          });
        }

        // If Event is type 'DELETE' -> call the service and delete data
        if (message.message === EventActionMessageEnum.DELETE) {
          preferenceService.deletePreferenceData(message.data.code).then(res => {
            if (res && res.status && !res.ok) {
              if (res.status === 423) {
                this.errorService.displayDefaultError('CANT_REMOVE_COM_PREF_MESSAGE', { type: 'PREFERENCE-DATA' });
              } else {
                this.errorService.displayDefaultError(res);
              }
            } else {
              this.refreshData('EDIT_REMOVED', { type: 'PREFERENCE-DATA' });
            }
          });
        }

      });
  }

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }

  setValidator() {
    ValidatorsCustom.list = this.list.map((a) => a.code);
  }


  /**
   * Get data from route and Option defined in PREFERENCE_DATA.config
   */
  ngOnInit() {
    this.list = this.route.snapshot.data['preferenceData'];
    this.setValidator();
    this.option = PreferenceDataConfig.option;
  }

  /**
   * Send event to Refresh Data
   */
  refreshData(message: string, parameters?: any) {
    return this.preferenceService.getPreferenceDatas(true).then(data => {
      this.eventActionService.actionExecuted(new EventActionMessage(EventActionMessageEnum.REFRESH, data));
      this.list = data;
      this.setValidator();
      this.errorService.displayDefaultError(message, parameters);
    });
  }

}
