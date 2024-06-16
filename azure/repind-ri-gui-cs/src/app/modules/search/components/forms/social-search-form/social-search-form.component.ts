import {Component, HostListener, ViewChild} from '@angular/core';
import {DynamicFormComponent} from '../../../../../shared/components/forms/dynamic-form/dynamic-form.component';
import {FieldConfig} from '../../../../../shared/models/forms/field-config';
import {Validators} from '@angular/forms';
import {SearchService} from '../../../../../core/services/search/search.service';
import {SocialSearchRequest} from '../../../../../shared/models/requests/search/social-search-request';
import {TranslateService} from '@ngx-translate/core';
import {OptionItem} from '../../../../../shared/models/contents/option-item';
import {PATTERNS} from '../../../../../shared/constant/partern.const';

@Component({
  selector: 'app-social-search-form',
  templateUrl: './social-search-form.component.html',
  styleUrls: ['./social-search-form.component.scss']
})
export class SocialSearchFormComponent {

  @ViewChild(DynamicFormComponent, {static: true}) form: DynamicFormComponent;

  private socialTypes: OptionItem[];
  public regConfig: FieldConfig[];

  constructor(private searchService: SearchService, private translate: TranslateService) {
    this.socialTypes = [
      new OptionItem('GID', 'SOCIAL-GID', 'SOCIAL-GID'),
      new OptionItem('', 'SOCIAL-FB', 'SOCIAL-FB'),
      new OptionItem('', 'SOCIAL-TWT', 'SOCIAL-TWT'),
      new OptionItem('', 'SOCIAL-PNM', 'SOCIAL-PNM'),
      new OptionItem('', 'SOCIAL-LKN', 'SOCIAL-LKN'),
      new OptionItem('', 'SOCIAL-SWB', 'SOCIAL-SWB'),
      new OptionItem('', 'SOCIAL-WCT', 'SOCIAL-WCT'),
      new OptionItem('', 'SOCIAL-WAP', 'SOCIAL-WAP'),
      new OptionItem('', 'SOCIAL-IST', 'SOCIAL-IST'),
      new OptionItem('', 'SOCIAL-KKO', 'SOCIAL-KKO'),
      new OptionItem('', 'SOCIAL-KLT', 'SOCIAL-KLT'),
      new OptionItem('', 'SOCIAL-HYV', 'SOCIAL-HYV'),
      new OptionItem('', 'SOCIAL-LA', 'SOCIAL-LA'),
      new OptionItem('', 'SOCIAL-TA', 'SOCIAL-TA'),
      new OptionItem('', 'SOCIAL-CA', 'SOCIAL-CA'),
      new OptionItem('', 'SOCIAL-APP', 'SOCIAL-APP'),
      new OptionItem('', 'SOCIAL-LIN', 'SOCIAL-LIN'),
      new OptionItem('', 'SOCIAL-FP', 'SOCIAL-FP'),
    ];
    this.regConfig = [
      {
        name: 'socialType',
        label: 'SOCIAL-TYPE',
        type: 'select',
        width: '45%',
        value: this.socialTypes[0].value,
        options: this.socialTypes
      },
      {
        type: 'input',
        label: 'SOCIAL-ID',
        inputType: 'text',
        name: 'socialID',
        autofocus: true,
        width: '50%',
        validations: [
          {
            name: 'required',
            validator: Validators.required,
            message: 'FIELD-REQUIRED'
          },
          {
            name: 'pattern',
            validator: Validators.pattern(PATTERNS.socialID),
            message: 'CORRECT-FORMAT-SOCIAL-ID'
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
  }

  public submitSocialSearchForm(): void {
    if (!this.form.valid) {
      return;
    }
    const socialID = this.form.value.socialID;
    const socialType = this.form.value.socialType;
    const merge = this.form.value.merge;
    const request = new SocialSearchRequest(socialID, socialType);
    this.searchService.searchIndividuals(request, false);
  }

  /** function to do the search when user clicked on the ENTER key */
  @HostListener('document: keypress', ['$event'])
  public handleKeyBoardEvent(event: KeyboardEvent) {
    if (event.key === 'Enter') {
      this.submitSocialSearchForm();
    }
  }
}
