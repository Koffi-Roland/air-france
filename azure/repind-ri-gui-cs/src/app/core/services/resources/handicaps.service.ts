import { Injectable, Injector } from '@angular/core';
import { ResourceService } from './resource.service';
import { Handicap } from '../../../shared/models/resources/handicap';
import { HandicapSerializer } from '../../../shared/models/serializer/handicap-serializer';

@Injectable({
  providedIn: 'root'
})
export class HandicapsService extends ResourceService<Handicap> {

  constructor(public injector: Injector) {
    super(
      'individual/:gin/handicaps',
      '',
      '',
      '',
      new HandicapSerializer(),
      injector
    );
   }
}
