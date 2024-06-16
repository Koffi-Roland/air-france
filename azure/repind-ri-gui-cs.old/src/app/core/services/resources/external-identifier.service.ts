import { Injectable, Injector } from '@angular/core';
import { ResourceService } from './resource.service';
import { ExternalIdentifier } from '../../../shared/models/resources/external-identifier';
import { ExternalIdentifierSerializer } from '../../../shared/models/serializer/external-identifier-serializer';

@Injectable({
  providedIn: 'root'
})
export class ExternalIdentifierService extends ResourceService<ExternalIdentifier> {

  constructor(public injector: Injector) {
    super(
      `individual/:gin/externalIdentifiers`,
      '',
      '',
      ``,
      new ExternalIdentifierSerializer(),
      injector
    );
   }
}
