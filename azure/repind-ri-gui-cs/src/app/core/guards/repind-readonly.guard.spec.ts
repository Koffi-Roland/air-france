import { TestBed, inject, waitForAsync } from '@angular/core/testing';

import { RepindReadonlyGuard } from './repind-readonly.guard';

describe('RepindReadonlyGuard', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [RepindReadonlyGuard]
    });
  });

  it('should ...', inject([RepindReadonlyGuard], (guard: RepindReadonlyGuard) => {
    expect(guard).toBeTruthy();
  }));
});
