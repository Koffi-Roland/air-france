/* tslint:disable:no-unused-variable */

import { TestBed, inject, waitForAsync } from '@angular/core/testing';
import { EventActionService } from './eventAction.service';

describe('Service: EventActionService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [EventActionService]
    });
  });

  it('should ...', inject([EventActionService], (service: EventActionService) => {
    expect(service).toBeTruthy();
  }));
});
