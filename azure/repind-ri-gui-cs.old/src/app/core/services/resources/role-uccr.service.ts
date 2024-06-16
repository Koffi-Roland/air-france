import { Injectable, Injector } from '@angular/core';
import { ResourceService } from './resource.service';
import { RoleUccr } from '../../../shared/models/resources/role-uccr';
import { RoleUccrSerializer } from '../../../shared/models/serializer/role-uccr-serializer';

@Injectable({
  providedIn: 'root'
})
export class RoleUccrService extends ResourceService<RoleUccr> {

  constructor(public injector: Injector) {
    super(
      `individual/:gin/rolesUCCR`,
      '',
      '',
      ``,
      new RoleUccrSerializer(),
      injector
    );
   }

}
