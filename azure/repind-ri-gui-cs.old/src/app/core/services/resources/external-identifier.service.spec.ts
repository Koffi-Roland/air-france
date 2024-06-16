import { TestBed } from '@angular/core/testing';

import { ExternalIdentifierService } from './external-identifier.service';

describe('ExternalIdentifierService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: ExternalIdentifierService = TestBed.get(ExternalIdentifierService);
    expect(service).toBeTruthy();
  });
});
