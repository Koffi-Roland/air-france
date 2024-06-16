import {
  ArrayDisplayRefTableActionOption,
  ArrayDisplayRefTableActionOptionConfig
} from '../../../../shared/arrayDisplayRefTable/_models/ArrayDisplayRefTableActionOption';
import { ArrayDisplayRefTableOption } from '../../../../shared/arrayDisplayRefTable/_models/ArrayDisplayRefTableOption';
import { GroupInfoEditComponent } from '../group-info-edit/group-info-edit.component';
import {
  DeleteActionComponent
} from '../../../../shared/arrayDisplayRefTable/arrayDisplayRefTableAction/genericActions/deleteAction/deleteAction.component';
import { WarningIcon } from 'src/app/shared/models/WarningIcon';
import { Condition } from 'src/app/shared/models/Condition';
import { ArrayDisplayRefTableColumn } from 'src/app/shared/arrayDisplayRefTable/_models/ArrayDisplayRefTableColumn';
import { FieldConfig } from 'src/app/shared/models/forms/field-config';
import { AddRemoveManyActionComponent } from 'src/app/shared/arrayDisplayRefTable/arrayDisplayRefTableAction/genericActions/addRemoveManyAction/addRemoveManyAction.component';
import { TableLinkedOptionConfig } from 'src/app/shared/models/TableLinkedOptionConfig';

export class GroupConfig {

  private _tableLinkData;
  private _retrieveFunction;

  constructor(tableLinkData, retrieveFunction) {
    this._tableLinkData = tableLinkData;
    this._retrieveFunction = retrieveFunction;
  }

  get option(): ArrayDisplayRefTableOption {
    const _configRemove: Array<ArrayDisplayRefTableActionOptionConfig> = [
      new ArrayDisplayRefTableActionOptionConfig('type', 'GROUP')
    ];

    const _configForm: Array<FieldConfig> = [];

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
      'refGroupsInfoId'
    );

    const _commonConfig: Array<ArrayDisplayRefTableActionOptionConfig> = [
      new ArrayDisplayRefTableActionOptionConfig('type', 'GROUP'),
      new ArrayDisplayRefTableActionOptionConfig('id', 'id')
    ];

    const _configCreateUpdate: Array<ArrayDisplayRefTableActionOptionConfig> = [
      new ArrayDisplayRefTableActionOptionConfig('form', _configForm),
    ].concat(_commonConfig);

    const _configAddRemove: Array<ArrayDisplayRefTableActionOptionConfig> = [
      new ArrayDisplayRefTableActionOptionConfig('tableLink', _configTableLink),
      new ArrayDisplayRefTableActionOptionConfig('retrieveFunction', this._retrieveFunction)
    ].concat(_commonConfig);

    const _optionsAction: Array<ArrayDisplayRefTableActionOption> = [
      new ArrayDisplayRefTableActionOption(
        'UPDATE_ASSOCIATION',
        AddRemoveManyActionComponent,
        'settings',
        _configAddRemove
      ),
      new ArrayDisplayRefTableActionOption('UPDATE', GroupInfoEditComponent, 'edit'),
      new ArrayDisplayRefTableActionOption('REMOVE', DeleteActionComponent, 'delete_forever', _configRemove)
    ];

    const _configColumns: Array<ArrayDisplayRefTableColumn> = [
      new ArrayDisplayRefTableColumn('id', 'ID', true),
      new ArrayDisplayRefTableColumn('code', 'CODE', true),
      new ArrayDisplayRefTableColumn('libelleEN', 'LIBELLE_EN', true),
      new ArrayDisplayRefTableColumn('libelleFR', 'LIBELLE_FR', true),
      new ArrayDisplayRefTableColumn('mandatoryOption', 'MANDATORY_OPTION', true),
      new ArrayDisplayRefTableColumn('defaultOption', 'DEFAULT-OPTION', true),
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
        GroupInfoEditComponent,
        'add',
        _configCreateUpdate
      )
    ];

    const _configWarningIcon: Array<WarningIcon> = [
      new WarningIcon('NO_COMPREF_LINKED', new Condition('nbCompref', 'EQUALS', 0)),
      new WarningIcon('NO_PRODUCT_LINKED', new Condition('nbProduct', 'EQUALS', 0))
    ];

    const _option: ArrayDisplayRefTableOption = new ArrayDisplayRefTableOption('id', 'GROUP', _optionsAction,
      _globalOtionsAction, _configColumns, _configWarningIcon);

    return _option;
  }


}
