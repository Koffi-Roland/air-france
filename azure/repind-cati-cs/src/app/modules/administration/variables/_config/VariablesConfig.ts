import { Validators } from '@angular/forms';
import { CreateUpdateActionComponent } from 'src/app/shared/arrayDisplayRefTable/arrayDisplayRefTableAction/genericActions/createUpdateAction/createUpdateAction.component';
import { ArrayDisplayRefTableColumn } from 'src/app/shared/arrayDisplayRefTable/_models/ArrayDisplayRefTableColumn';
import { FieldConfig } from 'src/app/shared/models/forms/field-config';
import {
  ArrayDisplayRefTableActionOptionConfig,
  ArrayDisplayRefTableActionOption
} from '../../../../shared/arrayDisplayRefTable/_models/ArrayDisplayRefTableActionOption';



import { ArrayDisplayRefTableOption } from '../../../../shared/arrayDisplayRefTable/_models/ArrayDisplayRefTableOption';

export class VariablesConfig {
  private static _configForm: Array<FieldConfig> = [
    {
      type: 'input',
      name: 'envKey',
      label: 'ENV_KEY',
      disableForUpdate: true,
    },
    {
      type: 'input',
      name: 'envValue',
      label: 'ENV_VALUE',
      validations: [
        {
          name: 'maxlength',
          validator: Validators.maxLength(255),
          message: 'MAX-LENGTH-MSG'
        }]
    }];

  private static _configCreateUpdate: Array<ArrayDisplayRefTableActionOptionConfig> = [
    new ArrayDisplayRefTableActionOptionConfig('type', 'VARIABLE'),
    new ArrayDisplayRefTableActionOptionConfig('form', VariablesConfig._configForm),
    new ArrayDisplayRefTableActionOptionConfig('id', 'envKey')
  ];

  private static _optionsAction: Array<ArrayDisplayRefTableActionOption> = [
    new ArrayDisplayRefTableActionOption(
      'UPDATE',
      CreateUpdateActionComponent,
      'edit',
      VariablesConfig._configCreateUpdate
    ),
  ];

  private static _configColumns: Array<ArrayDisplayRefTableColumn> = [
    new ArrayDisplayRefTableColumn('envKey', 'ENV_KEY', true),
    new ArrayDisplayRefTableColumn('envValue', 'ENV_VALUE', true)
  ];

  private static _option: ArrayDisplayRefTableOption = new ArrayDisplayRefTableOption('code', 'VARIABLE', VariablesConfig._optionsAction, [], VariablesConfig._configColumns);

  static get option(): ArrayDisplayRefTableOption {
    return VariablesConfig._option;
  }
}
