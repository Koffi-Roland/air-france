import { Component, ViewChild, HostListener } from '@angular/core';
import { Validators } from '@angular/forms';
import { SearchService } from '../../../../../core/services/search/search.service';
import { EmailSearchRequest } from '../../../../../shared/models/requests/search/email-search-request';
import { DynamicFormComponent } from '../../../../../shared/components/forms/dynamic-form/dynamic-form.component';
import { FieldConfig } from '../../../../../shared/models/forms/field-config';

@Component({
  selector: 'app-email-search-form',
  templateUrl: './email-search-form.component.html',
  styleUrls: ['./email-search-form.component.scss']
})
export class EmailSearchFormComponent {

  @ViewChild(DynamicFormComponent, { static: true }) form: DynamicFormComponent;

  public regConfig: FieldConfig[] = [
    {
      type: 'input',
      label: 'EMAIL',
      inputType: 'email',
      name: 'email',
      autofocus: true,
      validations: [
        {
          name: 'required',
          validator: Validators.required,
          message: 'FIELD-REQUIRED'
        },
        {
          name: 'maxlength',
          validator: Validators.maxLength(60),
          message: '',
          maxLength: 60
        }
      ]
    },
    {
      name: 'merge',
      label: 'MERGE-INCLUDED',
      type: 'checkbox',
      width: '100%',
      value: false
    }
  ];

  constructor(private searchService: SearchService) { }

  public submitEmailSearchForm(): void {
    if (!this.form.valid) { return; }
    const email = this.form.value.email;
    const merge = this.form.value.merge;
    const request = new EmailSearchRequest(email, merge);
    this.searchService.searchIndividuals(request, false);
  }

  /** function to do the search when user clicked on the ENTER key */
  @HostListener('document: keypress', ['$event'])
  public handleKeyBoardEvent(event: KeyboardEvent) {
    if (event.key === 'Enter') {
      this.submitEmailSearchForm();
    }
  }

}
