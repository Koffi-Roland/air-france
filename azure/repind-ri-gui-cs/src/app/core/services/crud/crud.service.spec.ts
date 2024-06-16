/* tslint:disable:no-unused-variable */

import { TestBed, inject, waitForAsync } from '@angular/core/testing';
import { CrudService } from './crud.service';

describe('Service: Crud', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [CrudService]
    });
  });

  it('should ...', inject([CrudService], (service: CrudService) => {
    expect(service).toBeTruthy();
  }));
});