import { Injectable, Injector } from '@angular/core';
import { ResourceService } from './resource.service';
import { Preference } from '../../../shared/models/resources/preference';
import { PreferenceSerializer } from '../../../shared/models/serializer/preference-serializer';

@Injectable({
  providedIn: 'root'
})
export class PreferencesService extends ResourceService<Preference> {

  constructor(public injector: Injector) {
    super(
      `individual/:gin/preferences`,
      `individual/:gin/preference`,
      `individual/:gin/preference/:id`,
      `individual/:gin/preference`,
      new PreferenceSerializer(),
      injector
    );
   }
}
