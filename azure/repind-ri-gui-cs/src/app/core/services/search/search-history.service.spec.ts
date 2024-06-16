/* tslint:disable:no-unused-variable */

import { TestBed, inject, waitForAsync } from '@angular/core/testing';
import { SearchHistoryService } from './search-history.service';

describe('Service: SearchHistory', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [SearchHistoryService]
    });
  });

  it('should ...', inject([SearchHistoryService], (service: SearchHistoryService) => {
    expect(service).toBeTruthy();
  }));
});
