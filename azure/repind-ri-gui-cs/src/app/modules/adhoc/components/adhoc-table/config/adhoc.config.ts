import {Validators} from '@angular/forms';
import {
  ArrayDisplayRefTableActionOption,
  ArrayDisplayRefTableActionOptionConfig
} from '../../../../../shared/arrayDisplayRefTable/_models/ArrayDisplayRefTableActionOption';
import {ArrayDisplayRefTableColumn} from '../../../../../shared/arrayDisplayRefTable/_models/ArrayDisplayRefTableColumn';
import {ArrayDisplayRefTableOption} from '../../../../../shared/arrayDisplayRefTable/_models/ArrayDisplayRefTableOption';
import {
  CreateUpdateActionComponent
} from '../../../../../shared/arrayDisplayRefTable/arrayDisplayRefTableAction/genericActions/createUpdateAction/createUpdateAction.component';
import {
  DeleteActionComponent
} from '../../../../../shared/arrayDisplayRefTable/arrayDisplayRefTableAction/genericActions/deleteAction/deleteAction.component';
import {FieldConfig} from '../../../../../shared/models/forms/field-config';
import {OptionItem} from '../../../../../shared/models/contents/option-item';
import {MAPPER_COLUMNS} from '../../../adhoc.const';
import {PATTERNS} from '../../../../../shared/constant/partern.const';

/**
 * Configuration to generate dynamic form
 * Based on all paramters provided
 * Adhoc Config class
 */
export class AdhocConfig {

  private allSubscriptionType: OptionItem[] = [];
  private allCivility: OptionItem[] = [];
  private allDomain: OptionItem[] = [];
  private allGroupType: OptionItem[] = [];
  private allStatus: OptionItem[] = [];

  constructor(allSubscriptionType, allCivility, allDomain, allGroupType, allStatus) {
    this.allSubscriptionType = allSubscriptionType;
    this.allCivility = allCivility;
    this.allDomain = allDomain;
    this.allGroupType = allGroupType;
    this.allStatus = allStatus;
  }


  get option(): ArrayDisplayRefTableOption {

    const _configForm: Array<FieldConfig> = [
      {
        type: 'input',
        name: 'emailAddress',
        inputType: 'email',
        label: 'EMAIL',
        width: '49%',
        autofocus: true,
        validations: [{
          name: 'required',
          validator: Validators.required,
          message: 'FIELD-REQUIRED'
        },
          {
            name: 'minlength',
            validator: Validators.minLength(6),
            message: 'MIN-LENGTH-MSG'
          },
          {
            name: 'maxlength',
            validator: Validators.maxLength(60),
            message: 'MAX-LENGTH-MSG',
            maxLength: 60
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
        name: 'gin',
        label: 'GIN',
        width: '49%',
        validations: [
          {
            name: 'minlength',
            validator: Validators.minLength(12),
            message: 'MIN-LENGTH-MSG'
          },
          {
            name: 'maxlength',
            validator: Validators.maxLength(12),
            message: 'MAX-LENGTH-MSG',
            maxLength: 12
          }
        ]

      },
      {
        type: 'input',
        name: 'cin',
        label: 'CIN',
        width: '49%',
        validations: [
          {
            name: 'minlength',
            validator: Validators.minLength(10),
            message: 'MIN-LENGTH-MSG'
          },
          {
            name: 'maxlength',
            validator: Validators.maxLength(10),
            message: 'MAX-LENGTH-MSG',
            maxLength: 10
          }
        ]
      }
      ,
      {
        type: 'input',
        name: 'firstname',
        label: 'FIRST-NAME',
        width: '49%',
        validations: [
          {
            name: 'minlength',
            validator: Validators.minLength(1),
            message: 'MIN-LENGTH-MSG'
          },
          {
            name: 'maxlength',
            validator: Validators.maxLength(25),
            message: 'MAX-LENGTH-MSG',
            maxLength: 25
          }
        ]
      }
      ,
      {
        type: 'input',
        name: 'surname',
        label: 'LAST-NAME',
        width: '49%',
        validations: [
          {
            name: 'minlength',
            validator: Validators.minLength(1),
            message: 'MIN-LENGTH-MSG'
          },
          {
            name: 'maxlength',
            validator: Validators.maxLength(35),
            message: 'MAX-LENGTH-MSG',
            maxLength: 35
          }
        ]
      }
      ,
      {
        type: 'select',
        name: 'civility',
        label: 'CIVILITY',
        options: this.allCivility,
        width: '49%',
      }
      ,
      {
        type: 'input',
        name: 'birthdate',
        label: 'BIRTH-DATE',
        width: '49%',
        validations: [
          {
            name: 'pattern',
            validator: Validators.pattern(PATTERNS.dateFormatAdhoc),
            message: 'DATE-ADHOC-MSG'
          }
        ]
      }
      ,
      {
        type: 'input',
        name: 'countryCode',
        label: 'COUNTRYCODE',
        width: '49%',
        validations: [{
          name: 'required',
          validator: Validators.required,
          message: 'FIELD-REQUIRED'
        },
          {
            name: 'minlength',
            validator: Validators.minLength(2),
            message: 'MIN-LENGTH-MSG'
          },
          {
            name: 'maxlength',
            validator: Validators.maxLength(2),
            message: 'MAX-LENGTH-MSG',
            maxLength: 2
          },
          {
            name: 'pattern',
            validator: Validators.pattern(PATTERNS.upperCaseLetters),
            message: 'UPPERCASE-LETTERS-MSG'
          }
        ]
      }
      ,
      {
        type: 'input',
        name: 'languageCode',
        label: 'LANGUAGE_CODE',
        width: '49%',
        validations: [{
          name: 'required',
          validator: Validators.required,
          message: 'FIELD-REQUIRED'
        },
          {
            name: 'minlength',
            validator: Validators.minLength(2),
            message: 'MIN-LENGTH-MSG'
          },
          {
            name: 'maxlength',
            validator: Validators.maxLength(2),
            message: 'MAX-LENGTH-MSG',
            maxLength: 2
          },
          {
            name: 'pattern',
            validator: Validators.pattern(PATTERNS.upperCaseLetters),
            message: 'UPPERCASE-LETTERS-MSG'
          }
        ]
      }
      ,
      {
        type: 'select',
        name: 'subscriptionType',
        label: 'SUBSCRIPTION-TYPE',
        options: this.allSubscriptionType,
        width: '49%',
        validations: [{
          name: 'required',
          validator: Validators.required,
          message: 'FIELD-REQUIRED'
        }
        ]
      }
      ,
      {
        type: 'select',
        name: 'domain',
        label: 'DOMAIN',
        options: this.allDomain,
        width: '49%',
        validations: [{
          name: 'required',
          validator: Validators.required,
          message: 'FIELD-REQUIRED'
        }]
      }
      ,
      {
        type: 'select',
        name: 'groupType',
        label: 'GROUP-TYPE',
        options: this.allGroupType,
        width: '49%',
        validations: [{
          name: 'required',
          validator: Validators.required,
          message: 'FIELD-REQUIRED'
        }]
      }
      ,
      {
        type: 'select',
        name: 'status',
        label: 'STATUS',
        options: this.allStatus,
        width: '49%',
        validations: [{
          name: 'required',
          validator: Validators.required,
          message: 'FIELD-REQUIRED'
        }]
      }
      ,
      {
        type: 'input',
        name: 'source',
        label: 'SOURCE',
        width: '49%',
        validations: [
          {
            name: 'minlength',
            validator: Validators.minLength(1),
            message: 'MIN-LENGTH-MSG'
          },
          {
            name: 'maxlength',
            validator: Validators.maxLength(16),
            message: 'MAX-LENGTH-MSG',
            maxLength: 16
          }
        ]
      }
      ,
      {
        type: 'input',
        name: 'dateOfConsent',
        label: 'DATE-OF-CONSENT',
        width: '49%',
        validations: [
          {
            name: 'pattern',
            validator: Validators.pattern(PATTERNS.dateFormatAdhoc),
            message: 'DATE-ADHOC-MSG'
          }
        ]
      }
      ,
      {
        type: 'input',
        name: 'preferredDepartureAirport',
        label: 'PREFERERED-DEPARTURE-AIRPORT',
        width: '49%',
        validations: [{
          name: 'required',
          validator: Validators.required,
          message: 'FIELD-REQUIRED'
        },
          {
            name: 'minlength',
            validator: Validators.minLength(3),
            message: 'MIN-LENGTH-MSG'
          },
          {
            name: 'maxlength',
            validator: Validators.maxLength(3),
            message: 'MAX-LENGTH-MSG',
            maxLength: 3
          }
        ]
      }
    ];

    const _configRemove: Array<ArrayDisplayRefTableActionOptionConfig> = [
      new ArrayDisplayRefTableActionOptionConfig('type', 'Adhoc')
    ];

    const _configCreateUpdate: Array<ArrayDisplayRefTableActionOptionConfig> = [
      new ArrayDisplayRefTableActionOptionConfig('type', ''),
      new ArrayDisplayRefTableActionOptionConfig('form', _configForm),
      new ArrayDisplayRefTableActionOptionConfig('id', 'id')
    ];

    const _optionsAction: Array<ArrayDisplayRefTableActionOption> = [
      new ArrayDisplayRefTableActionOption(
        'UPDATE',
        CreateUpdateActionComponent,
        'edit',
        _configCreateUpdate
      ),
      new ArrayDisplayRefTableActionOption(
        'REMOVE',
        DeleteActionComponent,
        'delete_forever',
        _configRemove
      )
    ];
    const isElementInError = (column: any) => {
      return (element: any) => element.errors?.includes(column);
    };
    const BG_COLOR = 'table-error-cell-bg';
    const LABELS = {
      EMAIL_ADDRESS: 'Email',
      GIN: 'GIN',
      CIN: 'CIN',
      FIRSTNAME: 'Firstname',
      SURNAME: 'Surname',
      CIVILITY: 'Civility',
      BIRTHDATE: 'Birthdate',
      COUNTRY_CODE: 'Country Code',
      LANGUAGE_CODE: 'Language Code',
      SUBSCRIPTION_TYPE: 'Subscription Type',
      DOMAIN: 'Domain',
      GROUP_TYPE: 'Group Type',
      STATUS: 'Status',
      SOURCE: 'Source',
      DATE_OF_CONSENT: 'Date Of Consent',
      PREFERRED_DEPARTURE_AIRPORT: 'Departure Airport'
    };
    const _configColumns: Array<ArrayDisplayRefTableColumn> = [
      new ArrayDisplayRefTableColumn('id', 'Line', true),
      ...Object.keys(MAPPER_COLUMNS).map((key) => {
        return new ArrayDisplayRefTableColumn(
          MAPPER_COLUMNS[key],
          LABELS[key],
          true,
          BG_COLOR,
          isElementInError(key),
          'TOOLTIP-ADHOC-' + key,
          isElementInError(key)
        );
      })
    ];

    const _globalOtionsAction: Array<ArrayDisplayRefTableActionOption> = [
      new ArrayDisplayRefTableActionOption(
        'CREATE',
        CreateUpdateActionComponent,
        'add',
        _configCreateUpdate
      )
    ];

    return new ArrayDisplayRefTableOption(
      'line', '', _optionsAction,
      _globalOtionsAction, _configColumns);

  }

}
