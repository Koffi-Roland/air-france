import { Injectable, Injector } from '@angular/core';
import { ResourceService } from './resource.service';
import { Alert } from '../../../shared/models/resources/alert';
import { AlertSerializer } from '../../../shared/models/serializer/alert-serializer';

@Injectable({
  providedIn: 'root'
})
export class AlertService extends ResourceService<Alert> {

  constructor(public injector: Injector) {
    super(
      `individual/:gin/alerts`,
      'individual/:gin/alerts',
      '',
      ``,
      new AlertSerializer(),
      injector
    );
  }

}
