/* tslint:disable:no-unused-variable */

import { TestBed, inject, waitForAsync } from '@angular/core/testing';
import { RefProviderService } from './ref-provider.service';

describe('Service: RefProvider', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [RefProviderService]
    });
  });

  it('should ...', inject([RefProviderService], (service: RefProviderService) => {
    expect(service).toBeTruthy();
  }));
});
