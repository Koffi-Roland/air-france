import {Component, HostListener, ViewChild} from '@angular/core';
import {SearchService} from '../../../../../core/services/search/search.service';
import {EmailSearchRequest} from '../../../../../shared/models/requests/search/email-search-request';
import {Validators} from '@angular/forms';
import {DynamicFormComponent} from '../../../../../shared/components/forms/dynamic-form/dynamic-form.component';
import {FieldConfig} from '../../../../../shared/models/forms/field-config';
import {RadioButtonOption} from '../../../../../shared/models/contents/radio-button-option';
import {AccountSearchRequest} from '../../../../../shared/models/requests/search/account-search-request';
import {PATTERNS} from '../../../../../shared/constant/partern.const';

@Component({
  selector: 'app-account-search-form',
  templateUrl: './account-search-form.component.html',
  styleUrls: ['./account-search-form.component.scss']
})
export class AccountSearchFormComponent {

  @ViewChild(DynamicFormComponent, {static: true}) form: DynamicFormComponent;

  public regConfig: FieldConfig[] = [
    {
      type: 'input',
      label: 'EMAIL',
      inputType: 'email',
      name: 'email',
      isDisplayed: () => this.isDisplayed('E'),
      autofocus: true,
      width: '75%',
      validations: [
        {
          name: 'required',
          validator: Validators.required,
          message: 'FIELD-REQUIRED'
        },
        {
          name: 'email',
          validator: Validators.email,
          message: 'WRONG-EMAIL-MESSAGE'
        }
      ]
    },
    {
      type: 'input',
      label: 'CIN',
      inputType: 'text',
      name: 'cin',
      isDisplayed: () => this.isDisplayed('C'),
      autofocus: true,
      width: '75%',
      validations: [
        {
          name: 'required',
          validator: Validators.required,
          message: 'FIELD-REQUIRED'
        },
        {
          name: 'pattern',
          validator: Validators.pattern(PATTERNS.cin),
          message: 'CORRECT-FORMAT-CIN'
        }
      ]
    },
    {
      type: 'radiobutton',
      name: 'type',
      radioBtnOptions: [new RadioButtonOption('E', 'EMAIL-SEARCH-TYPE'), new RadioButtonOption('C', 'CIN-SEARCH-TYPE')],
      value: 'E',
      width: '20%',
    }
  ];

  constructor(private searchService: SearchService) {
  }

  public submitAccountSearchForm(): void {
    if (!this.isFormValid()) {
      return;
    }
    const value = this.getFormValue();
    const request = new AccountSearchRequest(value.email, value.cin);
    this.searchService.searchIndividuals(request, false);
  }

  /** function to do the search when user clicked on the ENTER key */
  @HostListener('document: keypress', ['$event'])
  public handleKeyBoardEvent(event: KeyboardEvent) {
    if (event.key === 'Enter') {
      this.submitAccountSearchForm();
    }
  }

  private isDisplayed(type: 'E' | 'C'): boolean {
    return !!this.form && this.form.value.type === type;
  }

  public isFormValid(): boolean {
    return !!this.form ?
      this.form.value.type === 'E' ?
        this.form.getControl('email').valid : this.form.getControl('cin').valid
      : false;
  }

  private getFormValue(): { cin: string | null, email: string | null} {
    const type = this.form.value.type;
    return {
      email: type === 'E' ? this.form.value.email : null,
      cin: type === 'C' ? this.form.value.cin : null
    };
  }
}
