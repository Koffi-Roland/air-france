import {AutocompleteFilterService} from './../../../../../core/services/filters/autocomplete-filter.service';
import {Component, HostListener, OnInit, ViewChild} from '@angular/core';
import {Validators} from '@angular/forms';
import {SearchService} from '../../../../../core/services/search/search.service';
import {MulticriteriaSearchRequest} from '../../../../../shared/models/requests/search/multicriteria-search-request';
import {DynamicFormComponent} from '../../../../../shared/components/forms/dynamic-form/dynamic-form.component';
import {FieldConfig} from '../../../../../shared/models/forms/field-config';
import {RefProviderService} from '../../../../../core/services/references/ref-provider.service';

@Component({
  selector: 'app-multicriteria-search-form',
  templateUrl: './multicriteria-search-form.component.html',
  styleUrls: ['./multicriteria-search-form.component.scss']
})
export class MulticriteriaSearchFormComponent implements OnInit {

  @ViewChild(DynamicFormComponent, { static: true }) form: DynamicFormComponent;

  public regConfig: FieldConfig[] = [
    {
      type: 'input',
      label: 'LAST-NAME',
      inputType: 'text',
      name: 'lastname',
      width: '75%',
      autofocus: true,
      validations: [
        {
          name: 'required',
          validator: Validators.required,
          message: 'FIELD-REQUIRED'
        }
      ]
    },
    {
      type: 'input',
      label: 'FIRST-NAME',
      inputType: 'text',
      name: 'firstname',
      width: '75%',
      validations: [
        {
          name: 'required',
          validator: Validators.required,
          message: 'FIELD-REQUIRED'
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

  public submitMulticriteriaSearchForm(): void {
    if (!this.form.valid) { return; }
    const firstname = this.form.value.firstname;
    const lastname = this.form.value.lastname;
    const request = new MulticriteriaSearchRequest(firstname, lastname);
    this.searchService.searchIndividuals(request, false);
  }

  /** function to do the search when user clicked on the ENTER key */
  @HostListener('document: keypress', ['$event'])
  public handleKeyBoardEvent(event: KeyboardEvent) {
    if (event.key === 'Enter') {
      this.submitMulticriteriaSearchForm();
    }
  }

}
