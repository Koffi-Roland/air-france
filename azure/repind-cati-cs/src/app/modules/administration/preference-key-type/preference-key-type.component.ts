import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { ErrorService } from 'src/app/core/services/error.service';
import { PreferenceService } from 'src/app/core/services/preference.service';
import { ArrayDisplayRefTableOption } from 'src/app/shared/arrayDisplayRefTable/_models/ArrayDisplayRefTableOption';
import { EventActionService } from 'src/app/shared/arrayDisplayRefTable/_services/eventAction.service';
import { OptionItem } from 'src/app/shared/models/contents/option-item';
import { ToggleSliderOption } from 'src/app/shared/models/contents/toggle-slider-option';
import { EventActionMessage, EventActionMessageEnum } from 'src/app/shared/models/EventActionMessage';
import { ValidatorsCustom } from 'src/app/shared/widgets/validators/validators-custom.component';
import { PreferenceKeyTypeConfig } from './_config/PreferenceKeyTypeConfig';

@Component({
  selector: 'app-preference-key-type',
  templateUrl: './preference-key-type.component.html',
  styleUrls: ['./preference-key-type.component.scss']
})
export class PreferenceKeyTypeComponent implements OnInit, OnDestroy {

  list: any;
  option: ArrayDisplayRefTableOption;
  subscription: Subscription;

  preferenceKeys: OptionItem[] = [];
  preferenceTypes: OptionItem[] = [];

  _dataTypes: OptionItem[] = [
    new OptionItem('String'),
    new OptionItem('Date'),
    new OptionItem('Boolean')
  ];

  _conditions = [new ToggleSliderOption("M", "O")];

  constructor(private errorService: ErrorService, private route: ActivatedRoute, private eventActionService: EventActionService, private preferenceService: PreferenceService) {
    this.subscription = eventActionService.actionSentToBeApply$.subscribe(
      message => {
        // If Event is type 'CREATE' -> call the service and create data
        if (message.message === EventActionMessageEnum.CREATE) {
          preferenceService.postPreferenceKeyType(message.data).then(res => {
            if (res && res.status && !res.ok) {
              this.errorService.displayDefaultError(res);
            } else {
              this.refreshData('EDIT_CREATED', { type: 'PREFERENCE_KEY_TYPE' });
            }
          });
        }

        // If Event is type 'UPDATE' -> call the service and update data
        if (message.message === EventActionMessageEnum.UPDATE) {
          preferenceService.updatePreferenceKeyType(message.data.refId, message.data).then(res => {
            if (res && res.status && !res.ok) {
              this.errorService.displayDefaultError(res);
            } else {
              this.refreshData('EDIT_UPDATED', { type: 'PREFERENCE_KEY_TYPE' });
            }
          });
        }

        // If Event is type 'DELETE' -> call the service and delete data
        if (message.message === EventActionMessageEnum.DELETE) {
          preferenceService.deletePreferenceKeyType(message.data.refId).then(res => {
            if (res && res.status && !res.ok) {
              if (res.status === 423) {
                this.errorService.displayDefaultError('CANT_REMOVE_COM_PREF_MESSAGE', { type: 'PREFERENCE_KEY_TYPE' });
              } else {
                this.errorService.displayDefaultError(res);
              }
            } else {
              this.refreshData('EDIT_REMOVED', { type: 'PREFERENCE_KEY_TYPE' });
            }
          });
        }
      }
    );

  }

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }

  setValidator() {
    ValidatorsCustom.list = this.list.map((a) => a.refId);
  }

  ngOnInit(): void {
    this.list = this.route.snapshot.data['preferenceKeyType'];

    this.setValidator();

    const preferenceTypesForTable = [];
    this.preferenceService.getPreferences(true).then(res => {
      res.forEach(element => {
        this.preferenceTypes.push(new OptionItem(element.code));
        preferenceTypesForTable.push(element);
      });
    });

    this.preferenceService.getPreferenceDatas(true).then(res => {
      res.forEach(element => {
        this.preferenceKeys.push(new OptionItem(element.code, element.code, element.normalizedKey))
      });
    });

    const config = new PreferenceKeyTypeConfig(this._dataTypes, this._conditions, this.preferenceKeys, this.preferenceTypes, preferenceTypesForTable);
    this.option = config.option;
  }

  refreshData(message: string, parameters?: any) {
    return this.preferenceService.getPreferenceKeyType(true).then(data => {
      this.eventActionService.actionExecuted(new EventActionMessage(EventActionMessageEnum.REFRESH, data));
      this.list = data;
      this.setValidator();
      this.errorService.displayDefaultError(message, parameters);
    });
  }

}
