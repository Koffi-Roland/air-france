import { TestBed } from '@angular/core/testing';

import { AdhocService } from './adhoc.service';

describe('AdhocService', () => {
  let service: AdhocService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AdhocService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
