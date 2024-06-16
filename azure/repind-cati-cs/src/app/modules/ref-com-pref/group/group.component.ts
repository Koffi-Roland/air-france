import { Component, OnInit, ViewChild, OnDestroy } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { GroupConfig } from './_config/GroupConfig';
import { ArrayDisplayRefTableOption } from '../../../shared/arrayDisplayRefTable/_models/ArrayDisplayRefTableOption';
import { EventActionMessageEnum, EventActionMessage } from './../../../shared/models/EventActionMessage';
import { EventActionService } from '../../../shared/arrayDisplayRefTable/_services/eventAction.service';
import { GroupService } from '../../../core/services/group.service';
import { Subscription } from 'rxjs';
import { ErrorService } from 'src/app/core/services/error.service';

@Component({
  selector: 'app-group',
  templateUrl: './group.component.html',
  styleUrls: ['./group.component.css']
})
export class GroupComponent implements OnInit, OnDestroy {

  list: any;
  option: ArrayDisplayRefTableOption;
  subscription: Subscription;

  constructor(private route: ActivatedRoute, private errorService: ErrorService,
    private eventActionService: EventActionService, private groupService: GroupService) {
    this.subscription = eventActionService.actionSentToBeApply$.subscribe(
      message => {
        if (message.message === EventActionMessageEnum.DELETE) {
          groupService.deleteGroupInfo(message.data.id).then(res => {
            if (res && res.status && !res.ok) {
              if (res.status === 423) {
                this.errorService.displayDefaultError('CANT_REMOVE_GROUP_MESSAGE', { type: 'GROUP' });
              } else {
                this.errorService.displayDefaultError(res);
              }
            } else {
              this.refreshData('EDIT_REMOVED', { type: 'GROUP' });
            }
          });
        }
        if (message.message === EventActionMessageEnum.UPDATE) {
          if (message.data.isAssociation) {
            groupService.updateGroup(message.data.value).then(res => {
              if (res && res.status && !res.ok) {
                this.errorService.displayDefaultError(res);
              } else {
                this.refreshData('EDIT_UPDATED', { type: 'GROUP' });
              }
            });
          } else {
            groupService.putGroupInfo(message.data.id, message.data).then(res => {
              if (res && res.status && !res.ok) {
                this.errorService.displayDefaultError(res);
              } else {
                this.refreshData('EDIT_UPDATED', { type: 'GROUP' });
              }
            });
          }
        }
        if (message.message === EventActionMessageEnum.REFRESH) {
          if (message.data && message.data.status && !message.data.ok) {
            this.errorService.displayDefaultError(message.data);
          } else {
            this.refreshData('EDIT_UPDATED', { type: 'GROUP' });
          }
        }
      });
  }

  /**
   * Set columns displayed by default
  */
  /*displayColumn = {
    id: true,
    name: true,
    questionEN: true,
    question: true,
    dateCreation: false,
    dateModification: false,
    signatureCreation: false,
    signatureModification: false,
    siteCreation: false,
    siteModification: false
  };*/

  /*displayedColumns = ['id', 'name', 'questionEN', 'question', 'dateCreation', 'dateModification',
   'signatureCreation', 'signatureModification', 'siteCreation', 'siteModification', 'action'];*/

  ngOnInit() {
    this.getPermissions();
  }

  ngOnDestroy() {
    this.subscription.unsubscribe();
  }

  /**
   * Retrieve all domains
   */
  getPermissions() {
    this.list = this.route.snapshot.data['group'];
    this.groupService.listComPrefDgt = this.route.snapshot.data['communicationsPreferencesDgt'];

    const config = new GroupConfig(this.groupService.listComPrefDgt, this.groupService.getGroup.bind(this.groupService));
    this.option = config.option;
  }

  refreshData(message?: string, parameters?: any) {
    return this.groupService.getGroupsInfo(true).then(data => {
      this.eventActionService.actionExecuted(new EventActionMessage(EventActionMessageEnum.REFRESH, data));
      if (message) {
        this.errorService.displayDefaultError(message, parameters);
      }
    });
  }
}
