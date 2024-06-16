import { Validators } from '@angular/forms';
import { Component, OnInit, Input, ViewChild } from '@angular/core';
import * as moment from 'moment';
import { Individual } from '../../../../models/individual/individual';
import { CommonService } from '../../../../../core/services/common.service';
import { RefProviderService } from '../../../../../core/services/references/ref-provider.service';
import { ReferenceDataType } from '../../../../models/references/ReferenceDataType.enum';
import { IndividualIdentificationRequest } from '../../../../models/requests/w000442/IndividualIdentificationRequest';
import { DynamicFormComponent } from '../../../forms/dynamic-form/dynamic-form.component';
import { FieldConfig } from '../../../../models/forms/field-config';
import { OptionItem } from '../../../../models/contents/option-item';
import { TranslateService } from '@ngx-translate/core';
import { ReferenceData } from '../../../../models/references/ReferenceData';

@Component({
  selector: 'app-individual-update-form',
  templateUrl: './individual-update-form.component.html',
  styleUrls: ['./individual-update-form.component.scss']
})
export class IndividualUpdateFormComponent implements OnInit {

  @ViewChild(DynamicFormComponent, { static: true }) private _form: DynamicFormComponent;

  @Input() public individual: Individual;

  public config: FieldConfig[];

  private allCivilities: Array<OptionItem> = [];
  private allStatus: Array<OptionItem> = [];
  private allGenders: Array<OptionItem> = [];
  private allTitles: Array<OptionItem> = [];

  constructor(private common: CommonService, private refProviderService: RefProviderService, private translateService: TranslateService) { }

  ngOnInit() {
    this.initializeOptions();
    this.initializeConfiguration();
  }

  get individualIdentificationRequest(): IndividualIdentificationRequest {
    const firstname = this._form.value.firstname;
    const lastname = this._form.value.lastname;
    const dob: any = this._form.value.birthdate;
    const birthdate = (dob) ? moment(dob).format('YYYY-MM-DD HH:mm:ss') : '';
    const civility = this._form.value.civility;
    const middlename = this._form.value.middlename;
    const aliasFirstname = this._form.value.aliasFirstname;
    const aliasLastname = this._form.value.aliasLastname;
    const title = this._form.value.title;
    const status = this._form.value.status;
    const gender = this._form.value.gender;
    return new IndividualIdentificationRequest(this.individual.gin, firstname, lastname, birthdate, civility,
      middlename, aliasFirstname, aliasLastname, title, status, gender);
  }

  private initializeOptions(): void {

    const currentLocale = this.common.getCurrentLocal();

    // Load options for civilities
    this.allCivilities.push(new OptionItem('', this.translateService.instant('NONE'), ''));
    this.refProviderService.findReferenceData(ReferenceDataType.CIVILITY).map((ref: ReferenceData) => {
      const label = (currentLocale === 'fr') ? ref.labelFR : ref.labelEN;
      this.allCivilities.push(new OptionItem(ref.code, label, ref.code));
    });

    // Load options for genders
    this.allGenders.push(new OptionItem('', this.translateService.instant('NONE'), ''));
    this.refProviderService.findReferenceData(ReferenceDataType.GENDER).map((ref: ReferenceData) => {
      const label = (currentLocale === 'fr') ? ref.labelFR : ref.labelEN;
      this.allGenders.push(new OptionItem(ref.code, label, ref.code));
    });

    // Load options for status
    this.allStatus.push(new OptionItem('', this.translateService.instant('NONE'), ''));
    this.refProviderService.findReferenceData(ReferenceDataType.INDIVIDUAL_STATUS).map((ref: ReferenceData) => {
      const label = (currentLocale === 'fr') ? ref.labelFR : ref.labelEN;
      this.allStatus.push(new OptionItem(ref.code, label, ref.code));
    });

     // Load options for titles
     this.allTitles.push(new OptionItem('', this.translateService.instant('NONE'), ''));
     this.refProviderService.findReferenceData(ReferenceDataType.TITLES).map((ref: ReferenceData) => {
       const label = (currentLocale === 'fr') ? ref.labelFR : ref.labelEN;
       this.allTitles.push(new OptionItem(ref.code, label, ref.code));
     });

  }

  private initializeConfiguration(): void {

    this.config = [
      {
        type: 'input',
        name: 'lastname',
        label: 'LAST-NAME',
        value: this.individual.lastname,
        width: '45%',
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
        name: 'firstname',
        label: 'FIRST-NAME',
        value: this.individual.firstname,
        width: '45%',
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
        name: 'middlename',
        label: 'MIDDLE-NAME',
        width: '45%',
        value: this.individual.secondFirstName
      },
      {
        type: 'input',
        name: 'aliasLastname',
        label: 'ALIAS-LAST-NAME',
        width: '45%',
        value: this.individual.lastNameAlias
      },
      {
        type: 'input',
        name: 'aliasFirstname',
        label: 'ALIAS-FIRST-NAME',
        width: '45%',
        value: this.individual.firstNameAlias
      },
      {
        type: 'select',
        name: 'title',
        label: 'TITLE',
        width: '45%',
        value: this.individual.title,
        options: this.allTitles
      },
      {
        type: 'select',
        name: 'status',
        label: 'STATUS',
        width: '45%',
        value: this.individual.status,
        options: this.allStatus
      },
      {
        type: 'select',
        name: 'gender',
        label: 'GENDER',
        width: '45%',
        value: this.individual.sexe,
        options: this.allGenders
      },
      {
        type: 'select',
        name: 'civility',
        label: 'CIVILITY',
        width: '45%',
        value: this.individual.civility,
        options: this.allCivilities
      },
      {
        type: 'date',
        label: 'BIRTH-DATE',
        name: 'birthdate',
        width: '45%',
        placeholder: 'DATE-PLACEHOLDER',
        value: this.individual.birthdate
      }
    ];

  }

}
