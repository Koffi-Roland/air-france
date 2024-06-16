import { Component, OnDestroy, OnInit } from '@angular/core';
import { ArrayDisplayRefTableOption } from '../../../shared/arrayDisplayRefTable/_models/ArrayDisplayRefTableOption';
import { ActivatedRoute } from '@angular/router';
import { EventActionService } from '../../../shared/arrayDisplayRefTable/_services/eventAction.service';
import { EventActionMessageEnum, EventActionMessage } from '../../../shared/models/EventActionMessage';
import { PreferenceConfig } from './_config/PreferenceConfig';
import { PreferenceService } from '../../../core/services/preference.service';
import { Subscription } from 'rxjs';
import { ValidatorsCustom } from 'src/app/shared/widgets/validators/validators-custom.component';
import { ErrorService } from 'src/app/core/services/error.service';

@Component({
  selector: 'app-preference',
  templateUrl: './preference.component.html',
  styleUrls: ['./preference.component.scss']
})
export class PreferenceComponent implements OnInit, OnDestroy {

  list: any;
  option: ArrayDisplayRefTableOption;
  subscription: Subscription;

  constructor(private validatorsCustom: ValidatorsCustom, private route: ActivatedRoute, private errorService: ErrorService,
    private eventActionService: EventActionService, private preferenceService: PreferenceService) {
    /**
     * Watch event actionSentToBeApply
     */
    this.subscription = eventActionService.actionSentToBeApply$.subscribe(
      message => {
        // If Event is type 'CREATE' -> call the service and create data
        if (message.message === EventActionMessageEnum.CREATE) {
          preferenceService.postPreference(message.data).then(res => {
            if (res && res.status && !res.ok) {
              this.errorService.displayDefaultError(res);
            } else {
              this.refreshData('EDIT_CREATED', { type: 'PREFERENCE' });
            }
          });
        }

        // If Event is type 'UPDATE' -> call the service and update data
        if (message.message === EventActionMessageEnum.UPDATE) {
          preferenceService.updatePreference(message.data.code, message.data).then(res => {
            if (res && res.status && !res.ok) {
              this.errorService.displayDefaultError(res);
            } else {
              this.refreshData('EDIT_UPDATED', { type: 'PREFERENCE' });
            }
          });
        }

        // If Event is type 'DELETE' -> call the service and delete data
        if (message.message === EventActionMessageEnum.DELETE) {
          preferenceService.deletePreference(message.data.code).then(res => {
            if (res && res.status && !res.ok) {
              if (res.status === 423) {
                this.errorService.displayDefaultError('CANT_REMOVE_COM_PREF_MESSAGE', { type: 'PREFERENCE' });
              } else {
                this.errorService.displayDefaultError(res);
              }
            } else {
              this.refreshData('EDIT_REMOVED', { type: 'PREFERENCE' });
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
    this.list = this.route.snapshot.data['preference'];
    this.setValidator();
    this.option = PreferenceConfig.option;
  }

  /**
   * Send event to Refresh Data
   */
  refreshData(message: string, parameters?: any) {
    return this.preferenceService.getPreferences(true).then(data => {
      this.eventActionService.actionExecuted(new EventActionMessage(EventActionMessageEnum.REFRESH, data));
      this.list = data;
      this.setValidator();
      this.errorService.displayDefaultError(message, parameters);
    });
  }

}
