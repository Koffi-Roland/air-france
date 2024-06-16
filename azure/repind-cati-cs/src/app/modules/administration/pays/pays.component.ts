import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscriber, Subscription } from 'rxjs';
import { ErrorService } from 'src/app/core/services/error.service';
import { PaysService } from 'src/app/core/services/pays.service';
import { ArrayDisplayRefTableOption } from 'src/app/shared/arrayDisplayRefTable/_models/ArrayDisplayRefTableOption';
import { EventActionService } from 'src/app/shared/arrayDisplayRefTable/_services/eventAction.service';
import { EventActionMessage, EventActionMessageEnum } from 'src/app/shared/models/EventActionMessage';
import { PaysConfig } from './_config/PaysConfig';

@Component({
  selector: 'app-pays',
  templateUrl: './pays.component.html',
  styleUrls: ['./pays.component.scss']
})
export class PaysComponent implements OnInit {

  list: any;
  option: ArrayDisplayRefTableOption;
  subscription: Subscription;

  constructor(private route: ActivatedRoute, private eventActionService: EventActionService, private paysService: PaysService, private errorService: ErrorService) {
    this.subscription = eventActionService.actionSentToBeApply$.subscribe(
      message => {
        // If Event is type 'UPDATE' -> call the service and update data
        if (message.message === EventActionMessageEnum.UPDATE) {
          paysService.updatePays(message.data.codePays, message.data).then(res => {
            if (res && res.status && !res.ok) {
              this.errorService.displayDefaultError(res);
            } else {
              this.refreshData('EDIT_UPDATED', { type: 'PAYS' });
            }
          });
        }
      });
  }

  ngOnDestroy() {
    this.subscription.unsubscribe();
  }

  ngOnInit(): void {
    this.list = this.route.snapshot.data['pays'];
    this.option = PaysConfig.option;
  }

  /**
   * Send event to Refresh Data
   */
  refreshData(message: string, parameters?: any) {
    return this.paysService.getPays().then(data => {
      this.eventActionService.actionExecuted(new EventActionMessage(EventActionMessageEnum.REFRESH, data));
      this.list = data;
      this.errorService.displayDefaultError(message, parameters);
    });
  }

}
