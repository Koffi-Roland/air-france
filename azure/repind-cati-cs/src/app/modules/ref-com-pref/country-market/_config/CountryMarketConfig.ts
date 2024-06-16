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
import { OptionItem } from '../../../../shared/models/contents/option-item';
import { ValidatorsCustom } from '../../../../shared/widgets/validators/validators-custom.component';
import { Validators } from '@angular/forms';
import { ArrayDisplayRefTableColumn } from 'src/app/shared/arrayDisplayRefTable/_models/ArrayDisplayRefTableColumn';

export class CountryMarketConfig {

  private static _configForm: Array<FieldConfig> = [
    {
      type: 'input',
      name: 'codePays',
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
        validator: Validators.maxLength(2),
        message: 'MAX-LENGTH-MSG'
      },
      {
        name: 'isUnique',
        validator: ValidatorsCustom.isUnique(),
        message: 'COUNTRY_CODE_UNIQUE'
      }]
    },
    {
      type: 'input',
      name: 'market',
      label: 'MARKET',
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
      }]
    }];

  private static _configRemove: Array<ArrayDisplayRefTableActionOptionConfig> = [
    new ArrayDisplayRefTableActionOptionConfig('type', 'COUNTRY_MARKET')
  ];

  private static _configCreateUpdate: Array<ArrayDisplayRefTableActionOptionConfig> = [
    new ArrayDisplayRefTableActionOptionConfig('type', 'COUNTRY_MARKET'),
    new ArrayDisplayRefTableActionOptionConfig('form', CountryMarketConfig._configForm),
    new ArrayDisplayRefTableActionOptionConfig('id', 'codePays')
  ];

  private static _optionsAction: Array<ArrayDisplayRefTableActionOption> = [
    new ArrayDisplayRefTableActionOption(
      'UPDATE',
      CreateUpdateActionComponent,
      'edit',
      CountryMarketConfig._configCreateUpdate
    ),
    new ArrayDisplayRefTableActionOption(
      'REMOVE',
      DeleteActionComponent,
      'delete_forever',
      CountryMarketConfig._configRemove
    )
  ];

  private static _configColumns: Array<ArrayDisplayRefTableColumn> = [
    new ArrayDisplayRefTableColumn('codePays', 'CODE', true),
    new ArrayDisplayRefTableColumn('market', 'NAME', true),
    new ArrayDisplayRefTableColumn('dateCreation', 'CREATION_DATE', false),
    new ArrayDisplayRefTableColumn('dateModification', 'MODIFICATION_DATE', false),
    new ArrayDisplayRefTableColumn('signatureCreation', 'CREATION_SIGNATURE', false),
    new ArrayDisplayRefTableColumn('signatureModification', 'MODIFICATION_SIGNATURE', false),
    new ArrayDisplayRefTableColumn('siteCreation', 'CREATION_SITE', false),
    new ArrayDisplayRefTableColumn('siteModification', 'MODIFICATION_SITE', false)
  ];

  private static _globalOtionsAction: Array<ArrayDisplayRefTableActionOption> = [
    new ArrayDisplayRefTableActionOption(
      'CREATE',
      CreateUpdateActionComponent,
      'add',
      CountryMarketConfig._configCreateUpdate
    )
  ];

  private static _option: ArrayDisplayRefTableOption = new ArrayDisplayRefTableOption(
    'code', 'COUNTRY_MARKET', CountryMarketConfig._optionsAction,
    CountryMarketConfig._globalOtionsAction, CountryMarketConfig._configColumns);

  static get option(): ArrayDisplayRefTableOption {
    return CountryMarketConfig._option;
  }

}
