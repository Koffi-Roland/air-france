/* tslint:disable:no-unused-variable */

import { TestBed, inject, waitForAsync } from '@angular/core/testing';
import { CommonService } from './common.service';
import { TranslateService, TranslateModule } from '@ngx-translate/core';

describe('Service: Common', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [TranslateModule.forRoot()],
      providers: [CommonService, TranslateService]
    });
  });

  it('should create an instance', inject([CommonService], (service: CommonService) => {
    expect(service).toBeTruthy();
  }));

  it('should return API url', inject([CommonService], (service: CommonService) => {
    expect(service.getUrl()).toEqual('/api/rest/resources/');
  }));

});
