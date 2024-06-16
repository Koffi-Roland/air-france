import {
  ArrayDisplayRefTableActionOptionConfig,
  ArrayDisplayRefTableActionOption
} from '../../../../shared/arrayDisplayRefTable/_models/ArrayDisplayRefTableActionOption';
import {
  DeleteActionComponent
} from '../../../../shared/arrayDisplayRefTable/arrayDisplayRefTableAction/genericActions/deleteAction/deleteAction.component';
import { ArrayDisplayRefTableOption } from '../../../../shared/arrayDisplayRefTable/_models/ArrayDisplayRefTableOption';
import { FieldConfig } from 'src/app/shared/models/forms/field-config';
import { Validators } from '@angular/forms';
import { ValidatorsCustom } from 'src/app/shared/widgets/validators/validators-custom.component';
import { CreateUpdateActionComponent } from 'src/app/shared/arrayDisplayRefTable/arrayDisplayRefTableAction/genericActions/createUpdateAction/createUpdateAction.component';
import { Validator } from 'src/app/shared/models/forms/validator';
import { ArrayDisplayRefTableColumn } from 'src/app/shared/arrayDisplayRefTable/_models/ArrayDisplayRefTableColumn';

export class PreferenceDataConfig {

  private static _configRemove: Array<ArrayDisplayRefTableActionOptionConfig> = [
    new ArrayDisplayRefTableActionOptionConfig('type', 'PREFERENCE-DATA')
  ];

  private static _labelsValidation: Validator[] = [
    {
      name: 'required',
      validator: Validators.required,
      message: 'FIELD-REQUIRED'
    },
    {
      name: 'maxlength',
      validator: Validators.maxLength(125),
      message: 'MAX-LENGTH-MSG'
    }];

  private static _configForm: Array<FieldConfig> = [
    {
      type: 'input',
      name: 'normalizedKey',
      label: 'NORMALIZED_KEY',
      disableForUpdate: true,
      validations: [
        {
          name: 'required',
          validator: Validators.required,
          message: 'FIELD-REQUIRED'
        }, {
          name: 'maxlength',
          validator: Validators.maxLength(100),
          message: 'MAX-LENGTH-MSG'
        },
        {
          name: 'isUnique',
          validator: ValidatorsCustom.isUnique(),
          message: 'CODE_UNIQUE'
        },
        {
          name: 'pattern',
          validator: Validators.pattern(String.raw`\b[a-z0-9A-Z]+\b`),
          message: 'SINGLE_ALPHANUMERIC_WORD_MISMATCH'
        }
      ]
    },
    {
      type: 'input',
      name: 'libelleEn',
      label: 'LIBELLE_EN',
      validations: PreferenceDataConfig._labelsValidation
    },
    {
      type: 'input',
      name: 'libelleFr',
      label: 'LIBELLE_FR',
      validations: PreferenceDataConfig._labelsValidation
    },
  ]

  private static _configCreateUpdate: Array<ArrayDisplayRefTableActionOptionConfig> = [
    new ArrayDisplayRefTableActionOptionConfig('type', 'PREFERENCE-DATA'),
    new ArrayDisplayRefTableActionOptionConfig('form', PreferenceDataConfig._configForm),
    new ArrayDisplayRefTableActionOptionConfig('id', 'code')
  ];

  private static _optionsAction: Array<ArrayDisplayRefTableActionOption> = [
    new ArrayDisplayRefTableActionOption(
      'REMOVE',
      DeleteActionComponent,
      'delete_forever',
      PreferenceDataConfig._configRemove
    ),
    new ArrayDisplayRefTableActionOption(
      'UPDATE',
      CreateUpdateActionComponent,
      'edit',
      PreferenceDataConfig._configCreateUpdate
    )
  ];

  private static _globalOptionsAction: Array<ArrayDisplayRefTableActionOption> = [
    new ArrayDisplayRefTableActionOption(
      'CREATE',
      CreateUpdateActionComponent,
      'add',
      PreferenceDataConfig._configCreateUpdate
    )
  ];

  private static _configColumns: Array<ArrayDisplayRefTableColumn> = [
    new ArrayDisplayRefTableColumn('code', 'CODE', true),
    new ArrayDisplayRefTableColumn('libelleFr', 'LIBELLE_FR', true),
    new ArrayDisplayRefTableColumn('libelleEn', 'LIBELLE_EN', true),
    new ArrayDisplayRefTableColumn('normalizedKey', 'NORMALIZED_KEY', true),
  ]

  private static _option: ArrayDisplayRefTableOption = new ArrayDisplayRefTableOption('code', 'PREFERENCE-DATA',
    PreferenceDataConfig._optionsAction, PreferenceDataConfig._globalOptionsAction, PreferenceDataConfig._configColumns);

  static get option(): ArrayDisplayRefTableOption {
    return PreferenceDataConfig._option;
  }
}
