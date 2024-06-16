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

export class DomainConfig {

  private static _configForm: Array<FieldConfig> = [
    {
      type: 'input',
      name: 'codeDomain',
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
      name: 'libelleDomainEN',
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
      name: 'libelleDomain',
      label: 'LIBELLE_FR',
      validations: [
        {
          name: 'maxlength',
          validator: Validators.maxLength(25),
          message: 'MAX-LENGTH-MSG'
        }]
    }];

  private static _configRemove: Array<ArrayDisplayRefTableActionOptionConfig> = [
    new ArrayDisplayRefTableActionOptionConfig('type', 'DOMAIN')
  ];

  private static _configCreateUpdate: Array<ArrayDisplayRefTableActionOptionConfig> = [
    new ArrayDisplayRefTableActionOptionConfig('type', 'DOMAIN'),
    new ArrayDisplayRefTableActionOptionConfig('form', DomainConfig._configForm),
    new ArrayDisplayRefTableActionOptionConfig('id', 'codeDomain')
  ];

  private static _optionsAction: Array<ArrayDisplayRefTableActionOption> = [
    new ArrayDisplayRefTableActionOption(
      'UPDATE',
      CreateUpdateActionComponent,
      'edit',
      DomainConfig._configCreateUpdate
    ),
    new ArrayDisplayRefTableActionOption(
      'REMOVE',
      DeleteActionComponent,
      'delete_forever',
      DomainConfig._configRemove
    )
  ];

  private static _configColumns: Array<ArrayDisplayRefTableColumn> = [
    new ArrayDisplayRefTableColumn('codeDomain', 'CODE', true),
    new ArrayDisplayRefTableColumn('libelleDomainEN', 'NAME', true),
    new ArrayDisplayRefTableColumn('libelleDomain', 'NAME_FR', true),
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
      DomainConfig._configCreateUpdate
    )
  ];

  private static _option: ArrayDisplayRefTableOption = new ArrayDisplayRefTableOption(
    'code', 'DOMAIN', DomainConfig._optionsAction, DomainConfig._globalOtionsAction, DomainConfig._configColumns);

  static get option(): ArrayDisplayRefTableOption {
    return DomainConfig._option;
  }

}
