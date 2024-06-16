import {Injectable, Injector} from '@angular/core';
import {ResourceService} from './resource.service';
import {Address} from '../../../shared/models/resources/address';
import {AddressSerializer} from '../../../shared/models/serializer/address-serializer';
import {AccountSerializer} from '../../../shared/models/serializer/account-serializer';

@Injectable({
  providedIn: 'root'
})
export class AccountService extends ResourceService<Address> {

  constructor(public injector: Injector) {
    super(
      `individual/:gin/account`,
      ``,
      ``,
      ``,
      new AccountSerializer(),
      injector
    );
  }
}
