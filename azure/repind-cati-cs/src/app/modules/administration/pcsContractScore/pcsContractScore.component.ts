import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { ErrorService } from 'src/app/core/services/error.service';
import { ArrayDisplayRefTableOption } from 'src/app/shared/arrayDisplayRefTable/_models/ArrayDisplayRefTableOption';
import { EventActionService } from 'src/app/shared/arrayDisplayRefTable/_services/eventAction.service';
import { EventActionMessage, EventActionMessageEnum } from 'src/app/shared/models/EventActionMessage';
import { PcsContractScoreConfig } from './_config/pcsContractScoreConfig';
import {PcsContractScoreService} from "../../../core/services/pcsContractScore.service";


@Component({
  selector: 'app-pcs-contract-score',
  templateUrl: './pcsContractScore.component.html',
  styleUrls: ['./pcsContractScore.component.scss']
})
export class PcsContractScoreComponent implements OnInit {

  list: any;
  option: ArrayDisplayRefTableOption;
  subscription: Subscription;

  constructor(private route: ActivatedRoute, private eventActionService: EventActionService, private pcsContractScoreService: PcsContractScoreService, private errorService: ErrorService) {
    this.subscription = eventActionService.actionSentToBeApply$.subscribe(
      message => {
        // If Event is type 'CREATE' -> call the service and create data
        if (message.message === EventActionMessageEnum.CREATE) {
          pcsContractScoreService.postPcsContractScore(message.data).then(res => {
            if (res && res.status && !res.ok) {
              this.errorService.displayDefaultError(res);
            } else {
              this.refreshData('EDIT_CREATED', { type: 'PCS-CONTRACT-SCORE' });
            }
          });
        }

        // If Event is type 'UPDATE' -> call the service and update data
        if (message.message === EventActionMessageEnum.UPDATE) {
          pcsContractScoreService.updatePcsContractScore(message.data.code, message.data).then(res => {
            if (res && res.status && !res.ok) {
              this.errorService.displayDefaultError(res);
            } else {
              this.refreshData('EDIT_UPDATED', { type: 'PCS-CONTRACT-SCORE' });
            }
          });
        }
        // If Event is type 'DELETE' -> call the service and delete data
        if (message.message === EventActionMessageEnum.DELETE) {
          pcsContractScoreService.deletePcsContractScore(message.data.code).then(res => {
            if (res && res.status && !res.ok) {
              if (res.status === 423) {
                this.errorService.displayDefaultError('CANT_REMOVE_PCS_CONTRACT_SCORE', { type: 'PCS-CONTRACT-SCORE' });
              } else {
                this.errorService.displayDefaultError(res);
              }
            } else {
              this.refreshData('EDIT_REMOVED', { type: 'PCS-CONTRACT-SCORE' });
            }
          });
        }
      });
  }

  ngOnDestroy() {
    this.subscription.unsubscribe();
  }

  ngOnInit(): void {
    this.list = this.route.snapshot.data['PcsContractScore'];
    this.option = PcsContractScoreConfig.option;
  }

  /**
   * Send event to Refresh Data
   */
  refreshData(message: string, parameters?: any) {
    return this.pcsContractScoreService.getPcsContractScore().then(data => {
      this.eventActionService.actionExecuted(new EventActionMessage(EventActionMessageEnum.REFRESH, data));
      this.list = data;
      this.errorService.displayDefaultError(message, parameters);
    });
  }

}
