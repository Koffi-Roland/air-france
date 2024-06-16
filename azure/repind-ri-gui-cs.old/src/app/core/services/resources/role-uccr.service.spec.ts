import { TestBed } from '@angular/core/testing';

import { RoleUccrService } from './role-uccr.service';

describe('RoleUccrService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: RoleUccrService = TestBed.get(RoleUccrService);
    expect(service).toBeTruthy();
  });
});
