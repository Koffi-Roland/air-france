import {
  ArrayDisplayRefTableActionOptionConfig,
  ArrayDisplayRefTableActionOption
} from '../../../../shared/arrayDisplayRefTable/_models/ArrayDisplayRefTableActionOption';
import {
  DeleteActionComponent
} from '../../../../shared/arrayDisplayRefTable/arrayDisplayRefTableAction/genericActions/deleteAction/deleteAction.component';
import { ArrayDisplayRefTableOption } from '../../../../shared/arrayDisplayRefTable/_models/ArrayDisplayRefTableOption';
import { FieldConfig } from '../../../../shared/models/forms/field-config';
import { CommunicationPreferencesEditComponent } from '../communication-preferences-edit/communication-preferences-edit.component';
import { ArrayDisplayRefTableColumn } from 'src/app/shared/arrayDisplayRefTable/_models/ArrayDisplayRefTableColumn';

export class ComPrefConfig {

  private static _configForm: Array<FieldConfig> = [];

  private static _configRemove: Array<ArrayDisplayRefTableActionOptionConfig> = [
    new ArrayDisplayRefTableActionOptionConfig('type', 'COMMUNICATION_PREFERENCE')
  ];

  private static _configCreateUpdate: Array<ArrayDisplayRefTableActionOptionConfig> = [
    new ArrayDisplayRefTableActionOptionConfig('type', 'COMMUNICATION_PREFERENCE'),
    new ArrayDisplayRefTableActionOptionConfig('form', ComPrefConfig._configForm),
    new ArrayDisplayRefTableActionOptionConfig('id', 'refComprefId')
  ];

  private static _optionsAction: Array<ArrayDisplayRefTableActionOption> = [
    new ArrayDisplayRefTableActionOption(
      'UPDATE',
      CommunicationPreferencesEditComponent,
      'edit',
      ComPrefConfig._configCreateUpdate
    ),
    new ArrayDisplayRefTableActionOption(
      'REMOVE',
      DeleteActionComponent,
      'delete_forever',
      ComPrefConfig._configRemove
    )
  ];

  private static _configColumns: Array<ArrayDisplayRefTableColumn> = [
    new ArrayDisplayRefTableColumn('refComprefId', 'ID', false),
    new ArrayDisplayRefTableColumn('domain', 'DOMAIN', true),
    new ArrayDisplayRefTableColumn('comGroupeType', 'GROUP_TYPE', true),
    new ArrayDisplayRefTableColumn('comType', 'TYPE', true),
    new ArrayDisplayRefTableColumn('description', 'DESCRIPTION', true),
    new ArrayDisplayRefTableColumn('market', 'MARKET', true),
    new ArrayDisplayRefTableColumn('defaultLanguage1', 'DEFAULT_LANG', true),
    new ArrayDisplayRefTableColumn('defaultLanguage2', 'LANGUAGE_2', false),
    new ArrayDisplayRefTableColumn('defaultLanguage3', 'LANGUAGE_3', false),
    new ArrayDisplayRefTableColumn('defaultLanguage4', 'LANGUAGE_4', false),
    new ArrayDisplayRefTableColumn('defaultLanguage5', 'LANGUAGE_5', false),
    new ArrayDisplayRefTableColumn('defaultLanguage6', 'LANGUAGE_6', false),
    new ArrayDisplayRefTableColumn('defaultLanguage7', 'LANGUAGE_7', false),
    new ArrayDisplayRefTableColumn('defaultLanguage8', 'LANGUAGE_8', false),
    new ArrayDisplayRefTableColumn('defaultLanguage9', 'LANGUAGE_9', false),
    new ArrayDisplayRefTableColumn('defaultLanguage10', 'LANGUAGE_10', false),
    new ArrayDisplayRefTableColumn('media', 'MEDIA', true),
    new ArrayDisplayRefTableColumn('mandatoryOptin', 'MANDATORY_OPTIN', true),
    new ArrayDisplayRefTableColumn('fieldN', 'N', true),
    new ArrayDisplayRefTableColumn('fieldA', 'A', true),
    new ArrayDisplayRefTableColumn('fieldT', 'T', true),
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
      CommunicationPreferencesEditComponent,
      'add',
      ComPrefConfig._configCreateUpdate
    )
  ];

  private static _option: ArrayDisplayRefTableOption = new ArrayDisplayRefTableOption(
    'refComprefId', 'COMMUNICATION_PREFERENCES', ComPrefConfig._optionsAction,
    ComPrefConfig._globalOtionsAction, ComPrefConfig._configColumns);

  static get option(): ArrayDisplayRefTableOption {
    return ComPrefConfig._option;
  }

}
