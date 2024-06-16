import { Component, OnInit, ViewChild, OnDestroy } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { EventActionService } from '../../../shared/arrayDisplayRefTable/_services/eventAction.service';
import { EventActionMessageEnum, EventActionMessage } from '../../../shared/models/EventActionMessage';
import { MediaConfig } from './_config/MediaConfig';
import { MediaService } from '../../../core/services/media.service';
import { ValidatorsCustom } from '../../../shared/widgets/validators/validators-custom.component';
import { ArrayDisplayRefTableOption } from '../../../shared/arrayDisplayRefTable/_models/ArrayDisplayRefTableOption';
import { Subscription } from 'rxjs';
import { ErrorService } from 'src/app/core/services/error.service';


@Component({
  selector: 'app-media',
  templateUrl: './media.component.html',
  styleUrls: ['./media.component.css']
})
export class MediaComponent implements OnInit, OnDestroy {

  list: any;
  option: ArrayDisplayRefTableOption;
  customOption: any;
  subscription: Subscription;

  constructor(private route: ActivatedRoute, private eventActionService: EventActionService, private errorService: ErrorService,
    private validatorsCustom: ValidatorsCustom, private mediaService: MediaService) {
    /**
     * Watch event actionSentToBeApply
     */
    this.subscription = eventActionService.actionSentToBeApply$.subscribe(
      message => {
        // If Event is type 'CREATE' -> call the service and create data
        if (message.message === EventActionMessageEnum.CREATE) {
          mediaService.postMedia(message.data).then(res => {
            if (res && res.status && !res.ok) {
              this.errorService.displayDefaultError(res);
            } else {
              this.refreshData('EDIT_CREATED', { type: 'MEDIA' });
            }
          });
        }
        // If Event is type 'UPDATE' -> call the service and update data
        if (message.message === EventActionMessageEnum.UPDATE) {
          mediaService.updateMedia(message.data.codeMedia, message.data).then(res => {
            if (res && res.status && !res.ok) {
              this.errorService.displayDefaultError(res);
            } else {
              this.refreshData('EDIT_UPDATED', { type: 'MEDIA' });
            }
          });
        }
        // If Event is type 'DELETE' -> call the service and delete data
        if (message.message === EventActionMessageEnum.DELETE) {
          mediaService.deleteMedia(message.data.codeMedia).then(res => {
            if (res && res.status && !res.ok) {
              if (res.status === 423) {
                this.errorService.displayDefaultError('CANT_REMOVE_COM_PREF_MESSAGE', { type: 'MEDIA' });
              } else {
                this.errorService.displayDefaultError(res);
              }
            } else {
              this.refreshData('EDIT_REMOVED', { type: 'MEDIA' });
            }
          });
        }

      });
  }

  /**
   * Get data from route and Option defined in PREFERENCE_DATA.config
   */
  ngOnInit() {
    this.list = this.route.snapshot.data['media'];

    this.setValidator();

    this.option = MediaConfig.option;

  }

  ngOnDestroy() {
    this.subscription.unsubscribe();
  }

  setValidator() {

    const listCodeMedia = this.list.map(function (a) {
      return a.codeMedia;
    });

    ValidatorsCustom.list = listCodeMedia;
  }

  /**
   * Send event to Refresh Data
   */
  refreshData(message: string, parameters?: any) {
    return this.mediaService.getMedias(true).then(data => {
      this.eventActionService.actionExecuted(new EventActionMessage(EventActionMessageEnum.REFRESH, data));
      this.list = data;
      this.setValidator();
      this.errorService.displayDefaultError(message, parameters);
    });
  }

}
