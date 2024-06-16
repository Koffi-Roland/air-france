/* tslint:disable:no-unused-variable */

import { TestBed, inject, waitForAsync } from '@angular/core/testing';
import { TrackingIndividualService } from './trackingIndividual.service';

describe('Service: TrackingIndividual', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [TrackingIndividualService]
    });
  });

  it('should ...', inject([TrackingIndividualService], (service: TrackingIndividualService) => {
    expect(service).toBeTruthy();
  }));
});
