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

export class CommunicationTypeConfig {

  private static _configForm: Array<FieldConfig> = [
    {
      type: 'input',
      name: 'codeType',
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
      name: 'libelleTypeEN',
      label: 'LIBELLE_EN',
      validations: [{
        name: 'required',
        validator: Validators.required,
        message: 'FIELD-REQUIRED'
      },
      {
        name: 'maxlength',
        validator: Validators.maxLength(50),
        message: 'MAX-LENGTH-MSG'
      }]
    },
    {
      type: 'input',
      name: 'libelleType',
      label: 'LIBELLE_FR',
      validations: [
        {
          name: 'maxlength',
          validator: Validators.maxLength(50),
          message: 'MAX-LENGTH-MSG'
        }]
    }];

  private static _configRemove: Array<ArrayDisplayRefTableActionOptionConfig> = [
    new ArrayDisplayRefTableActionOptionConfig('type', 'TYPE')
  ];

  private static _configCreateUpdate: Array<ArrayDisplayRefTableActionOptionConfig> = [
    new ArrayDisplayRefTableActionOptionConfig('type', 'TYPE'),
    new ArrayDisplayRefTableActionOptionConfig('form', CommunicationTypeConfig._configForm),
    new ArrayDisplayRefTableActionOptionConfig('id', 'codeType')
  ];

  private static _optionsAction: Array<ArrayDisplayRefTableActionOption> = [
    new ArrayDisplayRefTableActionOption(
      'UPDATE',
      CreateUpdateActionComponent,
      'edit',
      CommunicationTypeConfig._configCreateUpdate
    ),
    new ArrayDisplayRefTableActionOption(
      'REMOVE',
      DeleteActionComponent,
      'delete_forever',
      CommunicationTypeConfig._configRemove
    )
  ];

  private static _configColumns: Array<ArrayDisplayRefTableColumn> = [
    new ArrayDisplayRefTableColumn('codeType', 'CODE', true),
    new ArrayDisplayRefTableColumn('libelleTypeEN', 'NAME', true),
    new ArrayDisplayRefTableColumn('libelleType', 'NAME_FR', true),
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
      CommunicationTypeConfig._configCreateUpdate
    )
  ];

  private static _option: ArrayDisplayRefTableOption = new ArrayDisplayRefTableOption(
    'code', 'COMMUNICATION_TYPE', CommunicationTypeConfig._optionsAction,
    CommunicationTypeConfig._globalOtionsAction, CommunicationTypeConfig._configColumns);

  static get option(): ArrayDisplayRefTableOption {
    return CommunicationTypeConfig._option;
  }

}
