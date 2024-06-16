import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { ErrorService } from 'src/app/core/services/error.service';
import { ArrayDisplayRefTableOption } from 'src/app/shared/arrayDisplayRefTable/_models/ArrayDisplayRefTableOption';
import { EventActionService } from 'src/app/shared/arrayDisplayRefTable/_services/eventAction.service';
import { EventActionMessage, EventActionMessageEnum } from 'src/app/shared/models/EventActionMessage';
import { PcsNonContractScoreConfig } from './_config/pcsNonContractScoreConfig';
import {PcsNonContractScoreService} from "../../../core/services/pcsNonContractScore.service";


@Component({
  selector: 'app-pcs-non-contract-score',
  templateUrl: './pcsNonContractScore.component.html',
  styleUrls: ['./pcsNonContractScore.component.scss']
})
export class PcsNonContractScoreComponent implements OnInit {

  list: any;
  option: ArrayDisplayRefTableOption;
  subscription: Subscription;

  constructor(private route: ActivatedRoute, private eventActionService: EventActionService, private pcsNonContractScoreService: PcsNonContractScoreService, private errorService: ErrorService) {
    this.subscription = eventActionService.actionSentToBeApply$.subscribe(
      message => {
        // If Event is type 'CREATE' -> call the service and create data
        if (message.message === EventActionMessageEnum.CREATE) {
          pcsNonContractScoreService.postPcsNonContractScore(message.data).then(res => {
            if (res && res.status && !res.ok) {
              this.errorService.displayDefaultError(res);
            } else {
              this.refreshData('EDIT_CREATED', { type: 'PCS-NON-CONTRACT-SCORE' });
            }
          });
        }

        // If Event is type 'UPDATE' -> call the service and update data
        if (message.message === EventActionMessageEnum.UPDATE) {
          pcsNonContractScoreService.updatePcsNonContractScore(message.data.code, message.data).then(res => {
            if (res && res.status && !res.ok) {
              this.errorService.displayDefaultError(res);
            } else {
              this.refreshData('EDIT_UPDATED', { type: 'PCS-NON-CONTRACT-SCORE' });
            }
          });
        }
        // If Event is type 'DELETE' -> call the service and delete data
        if (message.message === EventActionMessageEnum.DELETE) {
          pcsNonContractScoreService.deletePcsNonContractScore(message.data.code).then(res => {
            if (res && res.status && !res.ok) {
              if (res.status === 423) {
                this.errorService.displayDefaultError('CANT_REMOVE_PCS_NON_CONTRACT_SCORE', { type: 'PCS-NON-CONTRACT-SCORE' });
              } else {
                this.errorService.displayDefaultError(res);
              }
            } else {
              this.refreshData('EDIT_REMOVED', { type: 'PCS-NON-CONTRACT-SCORE' });
            }
          });
        }
      });
  }

  ngOnDestroy() {
    this.subscription.unsubscribe();
  }

  ngOnInit(): void {
    this.list = this.route.snapshot.data['PcsNonContractScore'];
    this.option = PcsNonContractScoreConfig.option;
  }

  /**
   * Send event to Refresh Data
   */
  refreshData(message: string, parameters?: any) {
    return this.pcsNonContractScoreService.getPcsNonContractScore().then(data => {
      this.eventActionService.actionExecuted(new EventActionMessage(EventActionMessageEnum.REFRESH, data));
      this.list = data;
      this.errorService.displayDefaultError(message, parameters);
    });
  }

}
