import { Injectable, Injector } from '@angular/core';
import { Consent } from '../../../shared/models/resources/consent';
import { ConsentSerializer } from '../../../shared/models/serializer/consent-serializer';
import { ResourceService } from './resource.service';

@Injectable({
  providedIn: 'root'
})
export class ConsentsService extends ResourceService<Consent> {

  constructor(public injector: Injector) {
    super(
      'individual/:gin/consents',
      'individual/:gin/consents',
      '',
      '',
      new ConsentSerializer(),
      injector
    );
  }
}
