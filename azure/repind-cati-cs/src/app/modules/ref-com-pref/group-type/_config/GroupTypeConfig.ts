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

export class GroupTypeConfig {

  private static _configForm: Array<FieldConfig> = [
    {
      type: 'input',
      name: 'codeGType',
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
        validator: Validators.maxLength(7),
        message: 'MAX-LENGTH-MSG'
      },
      {
        name: 'isUnique',
        validator: ValidatorsCustom.isUnique(),
        message: 'CODE_UNIQUE'
      }]
    },
    {
      type: 'input',
      name: 'libelleGTypeEN',
      label: 'LIBELLE_EN',
      validations: [{
        name: 'required',
        validator: Validators.required,
        message: 'FIELD-REQUIRED'
      },
      {
        name: 'maxlength',
        validator: Validators.maxLength(25),
        message: 'MAX-LENGTH-MSG'
      }]
    },
    {
      type: 'input',
      name: 'libelleGType',
      label: 'LIBELLE_FR',
      validations: [
        {
          name: 'maxlength',
          validator: Validators.maxLength(25),
          message: 'MAX-LENGTH-MSG'
        }]
    }];

  private static _configRemove: Array<ArrayDisplayRefTableActionOptionConfig> = [
    new ArrayDisplayRefTableActionOptionConfig('type', 'GROUP_TYPE')
  ];

  private static _configCreateUpdate: Array<ArrayDisplayRefTableActionOptionConfig> = [
    new ArrayDisplayRefTableActionOptionConfig('type', 'GROUP_TYPE'),
    new ArrayDisplayRefTableActionOptionConfig('form', GroupTypeConfig._configForm),
    new ArrayDisplayRefTableActionOptionConfig('id', 'codeGType')
  ];

  private static _optionsAction: Array<ArrayDisplayRefTableActionOption> = [
    new ArrayDisplayRefTableActionOption(
      'UPDATE',
      CreateUpdateActionComponent,
      'edit',
      GroupTypeConfig._configCreateUpdate
    ),
    new ArrayDisplayRefTableActionOption(
      'REMOVE',
      DeleteActionComponent,
      'delete_forever',
      GroupTypeConfig._configRemove
    )
  ];


  private static _configColumns: Array<ArrayDisplayRefTableColumn> = [
    new ArrayDisplayRefTableColumn('codeGType', 'CODE', true),
    new ArrayDisplayRefTableColumn('libelleGTypeEN', 'NAME', true),
    new ArrayDisplayRefTableColumn('libelleGType', 'NAME_FR', true),
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
      GroupTypeConfig._configCreateUpdate
    )
  ];

  private static _option: ArrayDisplayRefTableOption = new ArrayDisplayRefTableOption(
    'code', 'GROUP_TYPE',
    GroupTypeConfig._optionsAction, GroupTypeConfig._globalOtionsAction, GroupTypeConfig._configColumns);

  static get option(): ArrayDisplayRefTableOption {
    return GroupTypeConfig._option;
  }

}
