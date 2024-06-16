/* tslint:disable:no-unused-variable */

import { TestBed, inject, waitForAsync } from '@angular/core/testing';
import {  CustomMatPaginatorIntl } from './custom-mat-paginator-int';

describe('Service:  customMatPaginatorInt', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [ CustomMatPaginatorIntl ]
    });
  });

  it('should ...', inject([ CustomMatPaginatorIntl ], (service:  CustomMatPaginatorIntl) => {
    expect(service).toBeTruthy();
  }));
});
