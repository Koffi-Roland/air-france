import { TestBed } from '@angular/core/testing';

import { InferredsService } from './inferreds.service';

describe('InferredsService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: InferredsService = TestBed.get(InferredsService);
    expect(service).toBeTruthy();
  });
});
