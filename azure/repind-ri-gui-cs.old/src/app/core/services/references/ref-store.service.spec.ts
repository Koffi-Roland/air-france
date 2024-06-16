/* tslint:disable:no-unused-variable */

import { TestBed, inject, waitForAsync } from '@angular/core/testing';
import { RefStoreService } from './ref-store.service';

describe('Service: RefStore', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [RefStoreService]
    });
  });

  it('should ...', inject([RefStoreService], (service: RefStoreService) => {
    expect(service).toBeTruthy();
  }));
});
