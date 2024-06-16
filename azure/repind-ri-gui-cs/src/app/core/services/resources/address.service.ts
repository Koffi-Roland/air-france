import { Injectable, Injector } from '@angular/core';
import { ResourceService } from './resource.service';
import { Address } from '../../../shared/models/resources/address';
import { AddressSerializer } from '../../../shared/models/serializer/address-serializer';

@Injectable({
  providedIn: 'root'
})
export class AddressService extends ResourceService<Address> {

  constructor(public injector: Injector) {
    super(
      `individual/:gin/addresses`,
      `individual/:gin/address`,
      `individual/:gin/address/:id`,
      `individual/:gin/address`,
      new AddressSerializer(),
      injector
    );
  }
}
