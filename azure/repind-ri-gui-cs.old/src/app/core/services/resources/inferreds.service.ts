import { Injectable, Injector } from '@angular/core';
import { ResourceService } from './resource.service';
import { Inferred } from '../../../shared/models/resources/inferred';
import { InferredSerializer } from '../../../shared/models/serializer/inferred-serializer';

@Injectable({
  providedIn: 'root'
})
export class InferredsService extends ResourceService<Inferred> {

  constructor(public injector: Injector) {
    super(
      `individual/:gin/inferreds`,
      '',
      '',
      ``,
      new InferredSerializer(),
      injector
    );
   }
}
