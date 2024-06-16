import { TestBed } from '@angular/core/testing';

import { CommunicationPreferenceService } from './communication-preference.service';

describe('CommunicationPreferenceService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: CommunicationPreferenceService = TestBed.get(CommunicationPreferenceService);
    expect(service).toBeTruthy();
  });
});
