import { TestBed } from '@angular/core/testing';

import { ResourceUtilityService } from './resource-utility.service';

describe('ResourceUtilityService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: ResourceUtilityService = TestBed.get(ResourceUtilityService);
    expect(service).toBeTruthy();
  });
});
