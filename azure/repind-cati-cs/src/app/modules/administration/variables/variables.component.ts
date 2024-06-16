import { Component, OnDestroy, OnInit } from '@angular/core';
import { ArrayDisplayRefTableOption } from '../../../shared/arrayDisplayRefTable/_models/ArrayDisplayRefTableOption';
import { ActivatedRoute } from '@angular/router';
import { EventActionService } from '../../../shared/arrayDisplayRefTable/_services/eventAction.service';
import { EventActionMessageEnum, EventActionMessage } from '../../../shared/models/EventActionMessage';
import { VariablesConfig } from './_config/VariablesConfig';
import { VariablesService } from '../../../core/services/variables.service';
import { Subscription } from 'rxjs';
import { ErrorService } from 'src/app/core/services/error.service';

@Component({
  selector: 'app-variables',
  templateUrl: './variables.component.html',
  styleUrls: ['./variables.component.scss']
})
export class VariablesComponent implements OnInit, OnDestroy {

  list: any;
  option: ArrayDisplayRefTableOption;
  subscription: Subscription;

  constructor(private errorService: ErrorService, private route: ActivatedRoute, private eventActionService: EventActionService, private variablesService: VariablesService) {
    this.subscription = eventActionService.actionSentToBeApply$.subscribe(
      message => {
        if (message.message === EventActionMessageEnum.UPDATE) {
          variablesService.updateVariables(message.data.envKey, message.data).then(res => {
            if (res && res.status && !res.ok) {
              this.errorService.displayDefaultError(res);
            } else {
              this.refreshData('EDIT_UPDATED', { type: 'VARIABLE' });
            }
          });
        }
      });
  }

  ngOnDestroy() {
    this.subscription.unsubscribe();
  }

  /**
   * Get data from route and Option defined in PREFERENCE_DATA.config
   */
  ngOnInit() {
    this.list = this.route.snapshot.data['variables'];
    this.option = VariablesConfig.option;
  }

  /**
   * Send event to Refresh Data
   */
  refreshData(message: string, parameters?: any) {
    return this.variablesService.getVariables(true).then(data => {
      this.eventActionService.actionExecuted(new EventActionMessage(EventActionMessageEnum.REFRESH, data));
      this.list = data;
      this.errorService.displayDefaultError(message, parameters);
    });
  }

}
