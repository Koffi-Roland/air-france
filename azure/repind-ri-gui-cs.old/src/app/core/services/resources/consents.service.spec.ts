import { TestBed } from '@angular/core/testing';

import { ConsentsService } from './consents.service';

describe('ConsentsService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: ConsentsService = TestBed.get(ConsentsService);
    expect(service).toBeTruthy();
  });
});
