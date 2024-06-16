import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { GroupProductConfig } from './_config/GroupProductConfig';
import { EventActionService } from '../../../../shared/arrayDisplayRefTable/_services/eventAction.service';
import { GroupService } from '../../../../core/services/group.service';
import { ArrayDisplayRefTableOption } from '../../../../shared/arrayDisplayRefTable/_models/ArrayDisplayRefTableOption';
import { EventActionMessageEnum, EventActionMessage } from './../../../../shared/models/EventActionMessage';
import { Subscription } from 'rxjs';
import { ErrorService } from 'src/app/core/services/error.service';

@Component({
  selector: 'app-group-product',
  templateUrl: './group-product.component.html',
  styleUrls: ['./group-product.component.css']
})
export class GroupProductComponent implements OnInit, OnDestroy {

  list: any;
  option: ArrayDisplayRefTableOption;
  subscription: Subscription;

  constructor(private route: ActivatedRoute, private errorService: ErrorService,
     private eventActionService: EventActionService, private groupService: GroupService) {
    this.subscription = eventActionService.actionSentToBeApply$.subscribe(
      message => {
        if (message.message === EventActionMessageEnum.DELETE) {
          groupService.deleteGroupProduct(message.data.productId, message.data.idGroup).then(res => {
            if (res && res.status && !res.ok) {
              this.errorService.displayDefaultError(res);
            } else {
              this.refreshData('LINK_REMOVED_MESSAGE',
              {type: 'LINK', productid: message.data.productId, groupinfoid: message.data.idGroup});
            }
          });
        }
        if (message.message === EventActionMessageEnum.CREATE) {
          groupService.postGroupProduct(message.data).then(res => {
            if (res && res.status && !res.ok) {
              this.errorService.displayDefaultError(res);
            } else {
              this.refreshData('LINK_CREATE_MESSAGE',  {type: 'LINK'});
            }
          });
        }
      });
  }

  ngOnInit() {
    this.list = this.route.snapshot.data['groupProduct'];

    this.groupService.groups = this.route.snapshot.data['group'];
    this.groupService.products = this.route.snapshot.data['product'];
    this.option = GroupProductConfig.option;
  }

  ngOnDestroy() {
    this.subscription.unsubscribe();
  }

  refreshData(message?: string, parameters?: any) {
    return this.groupService.getGroupsProducts(true).then(data => {
      this.eventActionService.actionExecuted(new EventActionMessage(EventActionMessageEnum.REFRESH, data));
      if (message) {
        this.errorService.displayDefaultError(message, parameters);
      }
    });
  }

}
