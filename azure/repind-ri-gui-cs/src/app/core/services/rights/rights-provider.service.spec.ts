/* tslint:disable:no-unused-variable */

import { TestBed, inject, waitForAsync } from '@angular/core/testing';
import { RightsProviderService } from './rights-provider.service';

describe('Service: RightsProvider', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [RightsProviderService]
    });
  });

  it('should ...', inject([RightsProviderService], (service: RightsProviderService) => {
    expect(service).toBeTruthy();
  }));
});
