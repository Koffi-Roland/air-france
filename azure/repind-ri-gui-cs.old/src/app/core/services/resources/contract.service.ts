import { Injectable, Injector } from '@angular/core';
import { ResourceService } from './resource.service';
import { Contract } from '../../../shared/models/resources/contract';
import { ContractSerializer } from '../../../shared/models/serializer/contract-serializer';

@Injectable({
  providedIn: 'root'
})
export class ContractService extends ResourceService<Contract> {

  constructor(public injector: Injector) {
    super(
      `individual/:gin/contracts`,
      ``,
      ``,
      ``,
      new ContractSerializer(),
      injector
    );
  }
}
