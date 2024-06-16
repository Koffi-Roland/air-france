import { Component, OnInit, ViewChild, OnDestroy } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { EventActionService } from '../../../shared/arrayDisplayRefTable/_services/eventAction.service';
import { EventActionMessageEnum, EventActionMessage } from '../../../shared/models/EventActionMessage';
import { CountryMarketConfig } from './_config/CountryMarketConfig';
import { CountryMarketService } from '../../../core/services/country-market.service';
import { ValidatorsCustom } from '../../../shared/widgets/validators/validators-custom.component';
import { ArrayDisplayRefTableOption } from '../../../shared/arrayDisplayRefTable/_models/ArrayDisplayRefTableOption';
import { Subscription } from 'rxjs';
import { ErrorService } from 'src/app/core/services/error.service';


@Component({
  selector: 'app-country-market',
  templateUrl: './country-market.component.html',
  styleUrls: ['./country-market.component.css']
})
export class CountryMarketComponent implements OnInit, OnDestroy {

  list: any;
  option: ArrayDisplayRefTableOption;
  customOption: any;
  subscription: Subscription;

  constructor(private route: ActivatedRoute, private eventActionService: EventActionService, private errorService: ErrorService,
    private validatorsCustom: ValidatorsCustom, private countryMarketService: CountryMarketService) {
    /**
     * Watch event actionSentToBeApply
     */
    this.subscription = eventActionService.actionSentToBeApply$.subscribe(
      message => {
        // If Event is type 'CREATE' -> call the service and create data
        if (message.message === EventActionMessageEnum.CREATE) {
          countryMarketService.postCountryMarket(message.data).then(res => {
            if (res && res.status && !res.ok) {
              this.errorService.displayDefaultError(res);
            } else {
              this.refreshData('EDIT_CREATED', { type: 'COUNTRY_MARKET' });
            }
          });
        }
        // If Event is type 'UPDATE' -> call the service and update data
        if (message.message === EventActionMessageEnum.UPDATE) {
          countryMarketService.updateCountryMarket(message.data.codePays, message.data).then(res => {
            if (res && res.status && !res.ok) {
              this.errorService.displayDefaultError(res);
            } else {
              this.refreshData('EDIT_UPDATED', { type: 'COUNTRY_MARKET' });
            }
          });
        }
        // If Event is type 'DELETE' -> call the service and delete data
        if (message.message === EventActionMessageEnum.DELETE) {
          countryMarketService.deleteCountryMarket(message.data.codePays).then(res => {
            if (res && res.status && !res.ok) {
              if (res.status === 423) {
                this.errorService.displayDefaultError('CANT_REMOVE_COM_PREF_MESSAGE', { type: 'COUNTRY_MARKET' });
              } else {
                this.errorService.displayDefaultError(res);
              }
            } else {
              this.refreshData('EDIT_REMOVED', { type: 'COUNTRY_MARKET' });
            }
          });
        }

      });
  }

  /**
   * Get data from route and Option defined in PREFERENCE_DATA.config
   */
  ngOnInit() {
    this.list = this.route.snapshot.data['countryMarket'];

    this.setValidator();

    this.option = CountryMarketConfig.option;

  }

  ngOnDestroy() {
    this.subscription.unsubscribe();
  }

  setValidator() {

    const listCodePays = this.list.map(function (a) {
      return a.codePays;
    });

    ValidatorsCustom.list = listCodePays;

  }


  /**
   * Send event to Refresh Data
   */
  refreshData(message: string, parameters?: any) {
    return this.countryMarketService.getCountryMarkets(true).then(data => {
      this.eventActionService.actionExecuted(new EventActionMessage(EventActionMessageEnum.REFRESH, data));
      this.list = data;
      this.setValidator();
      this.errorService.displayDefaultError(message, parameters);
    });
  }

}
