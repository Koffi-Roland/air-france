import { TestBed } from '@angular/core/testing';

import { WarningLoaderService } from './warning-loader.service';

describe('WarningLoaderService', () => {
  let service: WarningLoaderService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(WarningLoaderService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
