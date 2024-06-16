import { Injectable, Injector } from '@angular/core';
import { ResourceService } from './resource.service';
import { CommunicationPreference } from '../../../shared/models/resources/communication-preference';
import { CommunicationPreferenceSerializer } from '../../../shared/models/serializer/communication-preference-serializer';

@Injectable({
  providedIn: 'root'
})
export class CommunicationPreferenceService extends ResourceService<CommunicationPreference> {

  constructor(public injector: Injector) {
    super(
      `individual/:gin/communicationPreferences`,
      `individual/:gin/communicationPreference`,
      '',
      ``,
      new CommunicationPreferenceSerializer(),
      injector
    );
   }
}
