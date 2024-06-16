/* tslint:disable:no-unused-variable */

import { TestBed, inject, waitForAsync } from '@angular/core/testing';
import { MergeTransformService } from './mergeTransform.service';

describe('Service: MergeTransform', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [MergeTransformService]
    });
  });

  it('should ...', inject([MergeTransformService], (service: MergeTransformService) => {
    expect(service).toBeTruthy();
  }));
});
