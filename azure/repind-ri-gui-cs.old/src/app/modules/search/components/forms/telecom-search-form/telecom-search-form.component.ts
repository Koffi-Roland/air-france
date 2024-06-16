import { AutocompleteFilterService } from './../../../../../core/services/filters/autocomplete-filter.service';
import { Validators } from '@angular/forms';
import { Component, OnInit, ViewChild, HostListener } from '@angular/core';
import { SearchService } from '../../../../../core/services/search/search.service';
import { TelecomSearchRequest } from '../../../../../shared/models/requests/search/telecom-search-request';
import { DynamicFormComponent } from '../../../../../shared/components/forms/dynamic-form/dynamic-form.component';
import { FieldConfig } from '../../../../../shared/models/forms/field-config';
import { RefProviderService } from '../../../../../core/services/references/ref-provider.service';
import { ReferenceDataType } from '../../../../../shared/models/references/ReferenceDataType.enum';
import {PATTERNS} from '../../../../../shared/utils/helpers/partern.helper';

@Component({
  selector: 'app-telecom-search-form',
  templateUrl: './telecom-search-form.component.html',
  styleUrls: ['./telecom-search-form.component.scss']
})
export class TelecomSearchFormComponent implements OnInit {

  @ViewChild(DynamicFormComponent, { static: true }) form: DynamicFormComponent;

  public regConfig: FieldConfig[] = [
    {
      type: 'input',
      label: 'PHONENUMBER-INTERNATIONAL',
      inputType: 'text',
      name: 'phone',
      width: '60%',
      placeholder: 'PLACEHOLDER-PHONE-NUMBER-FIELD',
      validations: [
        {
          name: 'required',
          validator: Validators.required,
          message: 'FIELD-REQUIRED'
        },
        {
          name: 'pattern',
          validator: Validators.pattern(PATTERNS.internationPhone),
          message: 'CORRECT-FORMAT-INTERNATIONAL-PHONE-NUMBER'
        }
      ]
    }
  ];

  constructor(
    private searchService: SearchService,
    private refProvider: RefProviderService,
    private autocompleteFilterService: AutocompleteFilterService
  ) { }

  ngOnInit() {
  }

  public submitSearchForm(): void {
    if (!this.form.valid) { return; }
    const num = this.form.value.phone;
    const request = new TelecomSearchRequest(num);
    this.searchService.searchIndividuals(request, false);
  }

  /** function to do the search when user clicked on the ENTER key */
  @HostListener('document: keypress', ['$event'])
  public handleKeyBoardEvent(event: KeyboardEvent) {
    if (event.key === 'Enter') {
      this.submitSearchForm();
    }
  }

}
