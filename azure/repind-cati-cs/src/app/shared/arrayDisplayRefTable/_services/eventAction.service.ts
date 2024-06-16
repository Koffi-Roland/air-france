import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';
import { EventActionMessage } from '../../models/EventActionMessage';

@Injectable({
  providedIn: 'root'
})
export class EventActionService {

  // Observable string sources
  private actionSentToBeApplySource = new Subject<EventActionMessage>();
  private actionExecutedSource = new Subject<EventActionMessage>();

  // Observable string streams
  actionSentToBeApply$ = this.actionSentToBeApplySource.asObservable();
  actionExecuted$ = this.actionExecutedSource.asObservable();

  /**
   * Send event to apply the action
   * @param datas, to provided to the one which read the event
   */
  actionSentToBeApply(datas: EventActionMessage) {
    this.actionSentToBeApplySource.next(datas);
  }

  /**
   * Send event when action is well performed
   * @param datas, to provided to the one which read the event
   */
  actionExecuted(datas: EventActionMessage) {
    this.actionExecutedSource.next(datas);
  }


constructor() { }

}
