/* tslint:disable:no-unused-variable */

import { TestBed, inject, waitForAsync } from '@angular/core/testing';
import { AdministratorService } from './administrator.service';

describe('Service: Administrator', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [AdministratorService]
    });
  });

  it('should ...', inject([AdministratorService], (service: AdministratorService) => {
    expect(service).toBeTruthy();
  }));
});
