import { Component, OnInit, ViewChild, OnDestroy } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { ArrayDisplayRefTableOption } from '../../../shared/arrayDisplayRefTable/_models/ArrayDisplayRefTableOption';
import { EventActionService } from '../../../shared/arrayDisplayRefTable/_services/eventAction.service';
import { EventActionMessageEnum, EventActionMessage } from '../../../shared/models/EventActionMessage';
import { ComPrefConfig } from './_config/ComPrefConfig';
import { CommunicationPreferencesService } from '../../../core/services/communication-preferences.service';
import { ValidatorsCustom } from '../../../shared/widgets/validators/validators-custom.component';
import { OptionItem } from '../../../shared/models/contents/option-item';
import { Subscription } from 'rxjs';
import { ErrorService } from 'src/app/core/services/error.service';


@Component({
  selector: 'app-communication-preferences',
  templateUrl: './communication-preferences.component.html',
  styleUrls: ['./communication-preferences.component.css']
})
export class CommunicationPreferencesComponent implements OnInit, OnDestroy {

  list: any;
  option: ArrayDisplayRefTableOption;
  customOption: any;
  subscription: Subscription;

  constructor(private route: ActivatedRoute, private eventActionService: EventActionService, private errorService: ErrorService,
    private validatorsCustom: ValidatorsCustom, private comPrefService: CommunicationPreferencesService) {
    /**
     * Watch event actionSentToBeApply
     */
    this.subscription = eventActionService.actionSentToBeApply$.subscribe(
      message => {
        // If Event is type 'CREATE' -> call the service and create data
        if (message.message === EventActionMessageEnum.CREATE) {
          comPrefService.postCommunicationPreferences(message.data).then(res => {
            if (res && res.status && !res.ok) {
              this.errorService.displayDefaultError(res);
            } else {
              this.refreshData('EDIT_CREATED', { type: 'COMMUNICATION_PREFERENCE' });
            }
          });
        }
        // If Event is type 'UPDATE' -> call the service and update data
        if (message.message === EventActionMessageEnum.UPDATE) {
          comPrefService.updateCommunicationPreferences(message.data.refComprefId, message.data).then(res => {
            if (res && res.status && !res.ok) {
              this.errorService.displayDefaultError(res);
            } else {
              this.refreshData('EDIT_UPDATED', { type: 'COMMUNICATION_PREFERENCE' });
            }
          });
        }
        // If Event is type 'DELETE' -> call the service and delete data
        if (message.message === EventActionMessageEnum.DELETE) {
          comPrefService.deleteCommunicationPreferences(message.data.refComprefId).then(res => {
            if (res && res.status && !res.ok) {
              this.errorService.displayDefaultError(res);
            } else {
              this.refreshData('EDIT_REMOVED', { type: 'COMMUNICATION_PREFERENCE' });
            }
          });
        }

      });
  }

  /**
   * Get data from route and Option defined in ComPrefDGTConfig.config
   */
  ngOnInit() {
    this.list = this.route.snapshot.data['communicationsPreferences'];

    ValidatorsCustom.list = this.list;

    this.comPrefService.listDomains = this.getDomain();

    this.comPrefService.listTypes = this.getType();

    this.comPrefService.listGTypes = this.getGroupTypes();

    this.comPrefService.listCountryMarkets = this.getCountryMarkets();

    this.comPrefService.listMedias = this.getMedias();

    this.comPrefService.listComPref = this.list;

    this.option = ComPrefConfig.option;

  }

  ngOnDestroy() {
    this.subscription.unsubscribe();
  }

  /**
   * Send event to Refresh Data
   */
  refreshData(message: string, parameters?: any) {
    return this.comPrefService.getCommunicationPreferences(true).then(data => {
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

  /**
   * Get country markets from router
   */
  getCountryMarkets(): Array<OptionItem> {

    const listCountryMarket = this.route.snapshot.data['countryMarket'];

    const allCountryMarkets: Array<OptionItem> = new Array<OptionItem>();

    listCountryMarket.map(function (a) {
      const optItem = new OptionItem(a.codePays);
      allCountryMarkets.push(optItem);
    });

    return allCountryMarkets;
  }


  /**
   * Get medias from router
   */
  getMedias(): Array<OptionItem> {

    const listMedia = this.route.snapshot.data['media'];

    const allMedias: Array<OptionItem> = new Array<OptionItem>();

    listMedia.map(function (a) {
      const optItem = new OptionItem(a.codeMedia);
      allMedias.push(optItem);
    });

    return allMedias;
  }
}
