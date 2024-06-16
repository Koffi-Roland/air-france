/* tslint:disable:no-unused-variable */

import { TestBed, inject, waitForAsync } from '@angular/core/testing';
import { AutocompleteFilterService } from './autocomplete-filter.service';

describe('Service: AutocompleteFilter', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [AutocompleteFilterService]
    });
  });

  it('should ...', inject([AutocompleteFilterService], (service: AutocompleteFilterService) => {
    expect(service).toBeTruthy();
  }));
});
