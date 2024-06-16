import { TestBed } from '@angular/core/testing';

import { TelecomService } from './telecom.service';

describe('TelecomService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: TelecomService = TestBed.get(TelecomService);
    expect(service).toBeTruthy();
  });
});
