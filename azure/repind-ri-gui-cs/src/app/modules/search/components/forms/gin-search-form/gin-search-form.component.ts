import { AutocompleteFilterService } from './../../../../../core/services/filters/autocomplete-filter.service';
import { Component, ViewChild, HostListener } from '@angular/core';
import { SearchService } from '../../../../../core/services/search/search.service';
import { GinSearchRequest } from '../../../../../shared/models/requests/search/gin-search-request';
import { DynamicFormComponent } from '../../../../../shared/components/forms/dynamic-form/dynamic-form.component';
import { FieldConfig } from '../../../../../shared/models/forms/field-config';
import { Validators } from '@angular/forms';
import { SearchHistoryService } from '../../../../../core/services/search/search-history.service';
@Component({
  selector: 'app-gin-search-form',
  templateUrl: './gin-search-form.component.html',
  styleUrls: ['./gin-search-form.component.scss']
})
export class GinSearchFormComponent {

  @ViewChild(DynamicFormComponent, { static: true }) form: DynamicFormComponent;

  public config: FieldConfig[] = [
    {
      type: 'autocomplete',
      label: 'GIN-CONTRACT',
      inputType: 'text',
      name: 'gin',
      placeholder: 'GIN-CONTRACT-OR-INDIVIDUAL',
      options: this.searchHistoryService.getLastIndividualsFound(),
      autofocus: true,
      // value: '400368644313',
      filterFct: (val: string, arr: any) => this.autocompleteFilterService.filterIndividualFullname(val, arr),
      validations: [
        {
          name: 'required',
          validator: Validators.required,
          message: 'FIELD-REQUIRED'
        },
        {
          name: 'maxlength',
          validator: Validators.maxLength(20),
          message: '',
          maxLength: 20
        }
      ]
    }
  ];

  constructor(
    private searchService: SearchService,
    private searchHistoryService: SearchHistoryService,
    private autocompleteFilterService: AutocompleteFilterService
  ) { }

  public search(): void {
    if (!this.form.valid) { return; }
    const gin = (this.form.value.gin).trim();
    const request = new GinSearchRequest(gin);
    this.searchService.searchIndividuals(request, false);
  }

  /** function to do the search when user clicked on the ENTER key */
  @HostListener('document: keypress', ['$event'])
  public handleKeyBoardEvent(event: KeyboardEvent) {
    if (event.key === 'Enter') {
      this.search();
    }
  }

}
