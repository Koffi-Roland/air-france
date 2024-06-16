/* tslint:disable:no-unused-variable */

import { TestBed, inject, waitForAsync } from '@angular/core/testing';
import { SearchApiService } from './search-api.service';

describe('Service: SearchApi', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [SearchApiService]
    });
  });

  it('should ...', inject([SearchApiService], (service: SearchApiService) => {
    expect(service).toBeTruthy();
  }));
});
