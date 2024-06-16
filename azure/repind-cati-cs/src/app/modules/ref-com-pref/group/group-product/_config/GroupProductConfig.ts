import {
  ArrayDisplayRefTableActionOptionConfig,
  ArrayDisplayRefTableActionOption
} from '../../../../../shared/arrayDisplayRefTable/_models/ArrayDisplayRefTableActionOption';
import {
  DeleteActionComponent
} from '../../../../../shared/arrayDisplayRefTable/arrayDisplayRefTableAction/genericActions/deleteAction/deleteAction.component';
import { ArrayDisplayRefTableOption } from '../../../../../shared/arrayDisplayRefTable/_models/ArrayDisplayRefTableOption';
import { GroupProductEditComponent } from '../group-product-edit/group-product-edit.component';
import { ArrayDisplayRefTableColumn } from 'src/app/shared/arrayDisplayRefTable/_models/ArrayDisplayRefTableColumn';

export class GroupProductConfig {

  private static _configRemove: Array<ArrayDisplayRefTableActionOptionConfig> = [
    new ArrayDisplayRefTableActionOptionConfig('type', 'GROUP-PRODUCT')
  ];

  private static _optionsAction: Array<ArrayDisplayRefTableActionOption> = [
    new ArrayDisplayRefTableActionOption(
      'REMOVE',
      DeleteActionComponent,
      'delete_forever',
      GroupProductConfig._configRemove
    )
  ];

  private static _configCreateUpdate: Array<ArrayDisplayRefTableActionOptionConfig> = [
    new ArrayDisplayRefTableActionOptionConfig('type', 'GROUP-PRODUCT'),
    new ArrayDisplayRefTableActionOptionConfig('id', 'id')
  ];

  private static _configColumns: Array<ArrayDisplayRefTableColumn> = [
    new ArrayDisplayRefTableColumn('productId', 'PRODUCT_ID', true),
    new ArrayDisplayRefTableColumn('productType', 'PRODUCT_TYPE', true),
    new ArrayDisplayRefTableColumn('subProductType', 'SUBPRODUCT_TYPE', true),
    new ArrayDisplayRefTableColumn('productName', 'PRODUCT_NAME', true),
    new ArrayDisplayRefTableColumn('groupId', 'GROUP_ID', true),
    new ArrayDisplayRefTableColumn('code', 'CODE', true),
    new ArrayDisplayRefTableColumn('libelleFR', 'LIBELLE_FR', true),
    new ArrayDisplayRefTableColumn('libelleEN', 'LIBELLE_EN', true),
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
      GroupProductEditComponent,
      'add',
      GroupProductConfig._configCreateUpdate
    )
  ];

  private static _option: ArrayDisplayRefTableOption = new ArrayDisplayRefTableOption('productId', 'GROUP-PRODUCT',
    GroupProductConfig._optionsAction, GroupProductConfig._globalOtionsAction, GroupProductConfig._configColumns);

  static get option(): ArrayDisplayRefTableOption {
    return GroupProductConfig._option;
  }
}
