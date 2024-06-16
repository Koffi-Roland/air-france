/* tslint:disable:no-unused-variable */

import { TestBed, inject, waitForAsync } from '@angular/core/testing';
import { ReferenceApiService } from './reference-api.service';

describe('Service: ReferenceApi', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [ReferenceApiService]
    });
  });

  it('should ...', inject([ReferenceApiService], (service: ReferenceApiService) => {
    expect(service).toBeTruthy();
  }));
});
