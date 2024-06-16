/* tslint:disable:no-unused-variable */

import { TestBed, inject, waitForAsync } from '@angular/core/testing';
import { CommonArrayDisplayRefTableService } from './commonArrayDisplayRefTable.service';

describe('Service: CommonArrayDisplayRefTable', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [CommonArrayDisplayRefTableService]
    });
  });

  it('should ...', inject([CommonArrayDisplayRefTableService], (service: CommonArrayDisplayRefTableService) => {
    expect(service).toBeTruthy();
  }));
});
