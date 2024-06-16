import {
  ArrayDisplayRefTableActionOptionConfig,
  ArrayDisplayRefTableActionOption
} from '../../../../shared/arrayDisplayRefTable/_models/ArrayDisplayRefTableActionOption';
import {
  CreateUpdateActionComponent
} from '../../../../shared/arrayDisplayRefTable/arrayDisplayRefTableAction/genericActions/createUpdateAction/createUpdateAction.component';
import { ArrayDisplayRefTableOption } from '../../../../shared/arrayDisplayRefTable/_models/ArrayDisplayRefTableOption';
import { FieldConfig } from '../../../../shared/models/forms/field-config';
import { Validators } from '@angular/forms';
import { WarningIcon } from 'src/app/shared/models/WarningIcon';
import { Condition } from 'src/app/shared/models/Condition';
import { ArrayDisplayRefTableColumn } from 'src/app/shared/arrayDisplayRefTable/_models/ArrayDisplayRefTableColumn';
import { AddRemoveManyActionComponent } from 'src/app/shared/arrayDisplayRefTable/arrayDisplayRefTableAction/genericActions/addRemoveManyAction/addRemoveManyAction.component';
import { TableLinkedOptionConfig } from 'src/app/shared/models/TableLinkedOptionConfig';

export class PermissionConfig {

  private _tableLinkData;
  private _retrieveFunction;

  constructor(tableLinkData, retrieveFunction) {
    this._tableLinkData = tableLinkData;
    this._retrieveFunction = retrieveFunction;
  }

  get option(): ArrayDisplayRefTableOption {

    const _configForm: Array<FieldConfig> = [
      {
        type: 'input',
        name: 'name',
        label: 'NAME',
        autofocus: true,
        uppercase: true,
        validations: [{
          name: 'required',
          validator: Validators.required,
          message: 'FIELD-REQUIRED'
        },
        {
          name: 'maxlength',
          validator: Validators.maxLength(30),
          message: 'MAX-LENGTH-MSG'
        }]
      },
      {
        type: 'input',
        name: 'questionEN',
        label: 'QUESTION',
        validations: [{
          name: 'required',
          validator: Validators.required,
          message: 'FIELD-REQUIRED'
        },
        {
          name: 'maxlength',
          validator: Validators.maxLength(255),
          message: 'MAX-LENGTH-MSG'
        }]
      },
      {
        type: 'input',
        name: 'question',
        label: 'QUESTION_FR',
        validations: [
          {
            name: 'maxlength',
            validator: Validators.maxLength(255),
            message: 'MAX-LENGTH-MSG'
          }]
      }];

    const _configRemove: Array<ArrayDisplayRefTableActionOptionConfig> = [
      new ArrayDisplayRefTableActionOptionConfig('type', 'QUESTION')
    ];

    const _configTableLink: TableLinkedOptionConfig = new TableLinkedOptionConfig(
      'COMMUNICATION_PREFERENCE',
      'refComPrefDgtId',
      this._tableLinkData,
      [
        new ArrayDisplayRefTableColumn('domain', 'DOMAIN'),
        new ArrayDisplayRefTableColumn('groupType', 'GROUP_TYPE'),
        new ArrayDisplayRefTableColumn('type', 'TYPE'),
        new ArrayDisplayRefTableColumn('description', 'DESCRIPTION')
      ],
      'listComPrefDgt',
      'refPermissionsQuestionId'
    );

    const _commonConfig: Array<ArrayDisplayRefTableActionOptionConfig> = [
      new ArrayDisplayRefTableActionOptionConfig('type', 'QUESTION'),
      new ArrayDisplayRefTableActionOptionConfig('id', 'id')
    ]

    const _configCreateUpdate: Array<ArrayDisplayRefTableActionOptionConfig> = [
      new ArrayDisplayRefTableActionOptionConfig('form', _configForm)
    ].concat(_commonConfig);

    const _configAddRemove: Array<ArrayDisplayRefTableActionOptionConfig> = [
      new ArrayDisplayRefTableActionOptionConfig('tableLink', _configTableLink),
      new ArrayDisplayRefTableActionOptionConfig('retrieveFunction', this._retrieveFunction)
    ].concat(_commonConfig);

    const _configWarningIcon: Array<WarningIcon> = [
      new WarningIcon('NO_COMPREF_LINKED', new Condition('nbCompref', 'EQUALS', 0))];


    const _optionsAction: Array<ArrayDisplayRefTableActionOption> = [
      new ArrayDisplayRefTableActionOption(
        'UPDATE_ASSOCIATION',
        AddRemoveManyActionComponent,
        'settings',
        _configAddRemove
      ),
      new ArrayDisplayRefTableActionOption(
        'UPDATE',
        CreateUpdateActionComponent,
        'edit',
        _configCreateUpdate
      )
    ];

    const _configColumns: Array<ArrayDisplayRefTableColumn> = [
      new ArrayDisplayRefTableColumn('id', 'ID', true),
      new ArrayDisplayRefTableColumn('name', 'NAME', true),
      new ArrayDisplayRefTableColumn('questionEN', 'QUESTION', true),
      new ArrayDisplayRefTableColumn('question', 'QUESTION_FR', true),
      new ArrayDisplayRefTableColumn('dateCreation', 'CREATION_DATE', false),
      new ArrayDisplayRefTableColumn('dateModification', 'MODIFICATION_DATE', false),
      new ArrayDisplayRefTableColumn('signatureCreation', 'CREATION_SIGNATURE', false),
      new ArrayDisplayRefTableColumn('signatureModification', 'MODIFICATION_SIGNATURE', false),
      new ArrayDisplayRefTableColumn('siteCreation', 'CREATION_SITE', false),
      new ArrayDisplayRefTableColumn('siteModification', 'MODIFICATION_SITE', false)
    ];

    const _globalOtionsAction: Array<ArrayDisplayRefTableActionOption> = [
      new ArrayDisplayRefTableActionOption(
        'CREATE',
        CreateUpdateActionComponent,
        'add',
        _configCreateUpdate
      )
    ];

    return new ArrayDisplayRefTableOption('id', 'PERMISSION', _optionsAction, _globalOtionsAction, _configColumns, _configWarningIcon);

  }
}
