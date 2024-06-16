import { Injectable, Injector } from '@angular/core';
import { ResourceService } from './resource.service';
import { Telecom } from '../../../shared/models/resources/telecom';
import { TelecomSerializer } from '../../../shared/models/serializer/telecom-serializer';

@Injectable({
  providedIn: 'root'
})
export class TelecomService extends ResourceService<Telecom> {

  constructor(public injector: Injector) {
    super(
      `individual/:gin/telecoms`,
      `individual/:gin/telecom`,
      `individual/:gin/telecom/:id`,
      `individual/:gin/telecom`,
      new TelecomSerializer(),
      injector
    );
  }
}
