import { TestBed } from '@angular/core/testing';

import { CivilitiesService } from './civilities.service';

describe('CivilitiesService', () => {
  let service: CivilitiesService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CivilitiesService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
