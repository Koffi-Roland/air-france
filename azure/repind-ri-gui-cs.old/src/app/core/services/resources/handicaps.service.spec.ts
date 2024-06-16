import { TestBed } from '@angular/core/testing';

import { HandicapsService } from './handicaps.service';

describe('HandicapsService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: HandicapsService = TestBed.get(HandicapsService);
    expect(service).toBeTruthy();
  });
});
