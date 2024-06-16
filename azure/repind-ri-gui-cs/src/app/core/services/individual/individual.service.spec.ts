/* tslint:disable:no-unused-variable */

import { TestBed, inject, waitForAsync } from '@angular/core/testing';
import { IndividualService } from './individual.service';

describe('Service: Individual', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [IndividualService]
    });
  });

  it('should ...', inject([IndividualService], (service: IndividualService) => {
    expect(service).toBeTruthy();
  }));
});
