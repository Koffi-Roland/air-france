/* tslint:disable:no-unused-variable */

import { TestBed, inject, waitForAsync } from '@angular/core/testing';

import { SearchService } from '../search/search.service';

describe('Service: SearchData', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [SearchService]
    });
  });

  it('should ...', inject([SearchService], (service: SearchService) => {
    expect(service).toBeTruthy();
  }));
});
