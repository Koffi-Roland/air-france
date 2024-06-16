import { Injectable, Injector } from '@angular/core';
import { ResourceService } from './resource.service';
import { Delegation } from '../../../shared/models/resources/delegation/delegation';
import { DelegationSerializer } from '../../../shared/models/serializer/delegation-serializer';

@Injectable({
  providedIn: 'root'
})
export class DelegationService extends ResourceService<Delegation> {

  constructor(public injector: Injector) {
    super(
      `individual/:gin/delegations`,
      'individual/:gin/delegations',
      'individual/:gin/delegations/:id',
      ``,
      new DelegationSerializer(),
      injector
    );
  }
}
