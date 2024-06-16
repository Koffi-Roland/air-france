import {
  ArrayDisplayRefTableActionOptionConfig,
  ArrayDisplayRefTableActionOption
} from '../../../../shared/arrayDisplayRefTable/_models/ArrayDisplayRefTableActionOption';
import {
  DeleteActionComponent
} from '../../../../shared/arrayDisplayRefTable/arrayDisplayRefTableAction/genericActions/deleteAction/deleteAction.component';
import {
  CreateUpdateActionComponent
} from '../../../../shared/arrayDisplayRefTable/arrayDisplayRefTableAction/genericActions/createUpdateAction/createUpdateAction.component';
import { ArrayDisplayRefTableOption } from '../../../../shared/arrayDisplayRefTable/_models/ArrayDisplayRefTableOption';
import { FieldConfig } from '../../../../shared/models/forms/field-config';
import { Validators } from '@angular/forms';
import { ValidatorsCustom } from 'src/app/shared/widgets/validators/validators-custom.component';
import { Validator } from 'src/app/shared/models/forms/validator';
import { ArrayDisplayRefTableColumn } from 'src/app/shared/arrayDisplayRefTable/_models/ArrayDisplayRefTableColumn';


export class PreferenceConfig {

  private static _labelsValidation: Validator[] = [
    {
      name: 'required',
      validator: Validators.required,
      message: 'FIELD-REQUIRED'
    }, {
      name: 'maxlength',
      validator: Validators.maxLength(125),
      message: 'MAX-LENGTH-MSG'
    }];

  private static _configForm: Array<FieldConfig> = [
    {
      type: 'input',
      name: 'code',
      label: 'CODE',
      autofocus: true,
      disableForUpdate: true,
      uppercase: true,
      validations: [{
        name: 'required',
        validator: Validators.required,
        message: 'FIELD-REQUIRED'
      },
      {
        name: 'maxlength',
        validator: Validators.maxLength(3),
        message: 'MAX-LENGTH-MSG'
      },
      {
        name: 'minlength',
        validator: Validators.minLength(3),
        message: 'MIN-LENGTH-MSG'
      },
      {
        name: 'isUnique',
        validator: ValidatorsCustom.isUnique(),
        message: 'CODE_UNIQUE'
      }]
    },
    {
      type: 'input',
      name: 'libelleEN',
      label: 'LIBELLE_EN',
      validations: PreferenceConfig._labelsValidation
    },
    {
      type: 'input',
      name: 'libelleFR',
      label: 'LIBELLE_FR',
      validations: PreferenceConfig._labelsValidation
    }];

  private static _configRemove: Array<ArrayDisplayRefTableActionOptionConfig> = [
    new ArrayDisplayRefTableActionOptionConfig('type', 'PREFERENCE')
  ];

  private static _configCreateUpdate: Array<ArrayDisplayRefTableActionOptionConfig> = [
    new ArrayDisplayRefTableActionOptionConfig('type', 'PREFERENCE'),
    new ArrayDisplayRefTableActionOptionConfig('form', PreferenceConfig._configForm),
    new ArrayDisplayRefTableActionOptionConfig('id', 'code')
  ];

  private static _optionsAction: Array<ArrayDisplayRefTableActionOption> = [
    new ArrayDisplayRefTableActionOption(
      'REMOVE',
      DeleteActionComponent,
      'delete_forever',
      PreferenceConfig._configRemove
    ),
    new ArrayDisplayRefTableActionOption(
      'UPDATE',
      CreateUpdateActionComponent,
      'edit',
      PreferenceConfig._configCreateUpdate
    )
  ];


  private static _globalOptionsAction: Array<ArrayDisplayRefTableActionOption> = [
    new ArrayDisplayRefTableActionOption(
      'CREATE',
      CreateUpdateActionComponent,
      'add',
      PreferenceConfig._configCreateUpdate
    )
  ];

  private static _configColumns: Array<ArrayDisplayRefTableColumn> = [
    new ArrayDisplayRefTableColumn('code', 'CODE', true),
    new ArrayDisplayRefTableColumn('libelleFR', 'LIBELLE_FR', true),
    new ArrayDisplayRefTableColumn('libelleEN', 'LIBELLE_EN', true),
  ]

  private static _option: ArrayDisplayRefTableOption = new ArrayDisplayRefTableOption(
    'code', 'PREFERENCE', PreferenceConfig._optionsAction, PreferenceConfig._globalOptionsAction, PreferenceConfig._configColumns);

  static get option(): ArrayDisplayRefTableOption {
    return PreferenceConfig._option;
  }
}
