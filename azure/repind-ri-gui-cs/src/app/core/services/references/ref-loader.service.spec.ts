/* tslint:disable:no-unused-variable */

import { TestBed, inject, waitForAsync } from '@angular/core/testing';
import { RefLoaderService } from './ref-loader.service';

describe('Service: RefLoader', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [RefLoaderService]
    });
  });

  it('should ...', inject([RefLoaderService], (service: RefLoaderService) => {
    expect(service).toBeTruthy();
  }));
});
