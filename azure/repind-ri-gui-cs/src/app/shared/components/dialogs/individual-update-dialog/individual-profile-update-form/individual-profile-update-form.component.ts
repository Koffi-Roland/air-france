import { Component, OnInit, Input, ViewChild } from '@angular/core';
import { Individual } from '../../../../models/individual/individual';
import { CommonService } from '../../../../../core/services/common.service';
import { RefProviderService } from '../../../../../core/services/references/ref-provider.service';
import { ReferenceDataType } from '../../../../models/references/ReferenceDataType.enum';
import { DynamicFormComponent } from '../../../forms/dynamic-form/dynamic-form.component';
import { FieldConfig } from '../../../../models/forms/field-config';
import { OptionItem } from '../../../../models/contents/option-item';
import { ReferenceData } from '../../../../models/references/ReferenceData';
import { TranslateService } from '@ngx-translate/core';
import { IndividualProfileRequest } from '../../../../models/requests/w000442/IndividualProfileRequest';

@Component({
  selector: 'app-individual-profile-update-form',
  templateUrl: './individual-profile-update-form.component.html',
  styleUrls: ['./individual-profile-update-form.component.scss']
})
export class IndividualProfileUpdateFormComponent implements OnInit {

  @ViewChild(DynamicFormComponent, { static: true }) private _form: DynamicFormComponent;

  @Input() public individual: Individual;

  public config: FieldConfig[];

  private allAuthorizationMailing: Array<OptionItem> = [];
  private allBranches: Array<OptionItem> = [];
  private allLanguages: Array<OptionItem> = [];

  constructor(private common: CommonService, private refProviderService: RefProviderService, private translateService: TranslateService) { }

  ngOnInit() {
    this.initializeOptions();
    this.initializeConfigurations();
  }

  get profileRequest(): IndividualProfileRequest {
    const mailing = this._form.value.nat;
    const branch = this._form.value.branch;
    const motherLanguage = this._form.value.language;
    return new IndividualProfileRequest(mailing, branch, motherLanguage);
  }

  private initializeConfigurations(): void {

    this.config = [
      {
        type: 'select',
        name: 'nat',
        label: 'MAILING',
        value: this.individual.profile.mailing,
        options: this.allAuthorizationMailing,
        autofocus: true
      },
      {
        type: 'select',
        name: 'branch',
        label: 'BRANCH',
        value: this.individual.profile.branch,
        options: this.allBranches
      },
      {
        type: 'select',
        name: 'language',
        label: 'MOTHER-LANGUAGE',
        value: this.individual.profile.communicationLanguage,
        options: this.allLanguages
      }
    ];

  }

  private initializeOptions(): void {

    const currentLocale = this.common.getCurrentLocal();

    // Load options for authorization mailing
    this.allAuthorizationMailing.push(new OptionItem('', this.translateService.instant('NONE'), ''));
    this.refProviderService.findReferenceData(ReferenceDataType.NAT).map((ref: ReferenceData) => {
      const label = (currentLocale === 'fr') ? ref.labelFR : ref.labelEN;
      this.allAuthorizationMailing.push(new OptionItem(ref.code, label, ref.code));
    });

    // Load options for branches
    this.allBranches.push(new OptionItem('', this.translateService.instant('NONE'), ''));
    this.refProviderService.findReferenceData(ReferenceDataType.BRANCHES).map((ref: ReferenceData) => {
      const label = (currentLocale === 'fr') ? ref.labelFR : ref.labelEN;
      this.allBranches.push(new OptionItem(ref.code, label, ref.code));
    });

    // Load options for languages
    this.allLanguages.push(new OptionItem('', this.translateService.instant('NONE'), ''));
    this.refProviderService.findReferenceData(ReferenceDataType.LANGUAGES).map((ref: ReferenceData) => {
      const label = (currentLocale === 'fr') ? ref.labelFR : ref.labelEN;
      this.allLanguages.push(new OptionItem(ref.code, label, ref.code));
    });

  }

}
