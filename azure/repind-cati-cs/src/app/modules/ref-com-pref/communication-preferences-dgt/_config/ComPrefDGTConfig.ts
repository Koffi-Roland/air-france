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
import { Validator } from '../../../../shared/models/forms/validator';
import { ArrayDisplayRefTableColumn } from 'src/app/shared/arrayDisplayRefTable/_models/ArrayDisplayRefTableColumn';

export class ComPrefDGTConfig {

  private allTypes;
  private allDomains;
  private allGTypes;

  constructor(allTypes, allDomains, allGTypes) {
    this.allTypes = allTypes;
    this.allDomains = allDomains;
    this.allGTypes = allGTypes;
  }

  get option(): ArrayDisplayRefTableOption {

    const _configForm: Array<FieldConfig> = [
      {
        type: 'select',
        name: 'domain',
        label: 'DOMAIN',
        options: this.allDomains,
        validations: [{
          name: 'required',
          validator: Validators.required,
          message: 'FIELD-REQUIRED'
        }]
      },
      {
        type: 'select',
        name: 'type',
        label: 'TYPE',
        options: this.allTypes,
        validations: [{
          name: 'required',
          validator: Validators.required,
          message: 'FIELD-REQUIRED'
        }]
      },
      {
        type: 'select',
        name: 'groupType',
        label: 'GROUP_TYPE',
        options: this.allGTypes,
        validations: [
          {
            name: 'required',
            validator: Validators.required,
            message: 'FIELD-REQUIRED'
          }]
      }];

    const _configRemove: Array<ArrayDisplayRefTableActionOptionConfig> = [
      new ArrayDisplayRefTableActionOptionConfig('type', 'COMBINATION')
    ];

    const _configGlobalValidators: Array<Validator> = [
      {
        name: 'isCombinationUnique',
        validator: ValidatorsCustom.isCombinationUnique(),
        message: 'COMBINATION_ALREADY_EXISTS'
      }];


    const _configFunction: Array<any> = [
      function removeActualElementFromList(param) {
        // For copy object without reference
        const listCombination = JSON.parse(JSON.stringify(ValidatorsCustom.list));
        // Polyfill for IE 11
        if (!Array.prototype.findIndex) {
          let index = -1;
          listCombination.some(function (item, i) {
            if (item.refComPrefDgtId === param.refComPrefDgtId) {
              index = i;
              return true;
            }
          });
          if (index !== -1) {
            listCombination.splice(index, 1);
          }
        } else {
          // For other browsers
          const index = listCombination.findIndex(item => item.refComPrefDgtId === param.refComPrefDgtId);
          if (index !== -1) {
            listCombination.splice(index, 1);
          }
        }
        ValidatorsCustom.listCombination = listCombination;
      }
    ];

    const _configCreateUpdate: Array<ArrayDisplayRefTableActionOptionConfig> = [
      new ArrayDisplayRefTableActionOptionConfig('type', 'COMBINATION'),
      new ArrayDisplayRefTableActionOptionConfig('form', _configForm),
      new ArrayDisplayRefTableActionOptionConfig('id', 'refComPrefDgtId'),
      new ArrayDisplayRefTableActionOptionConfig('globalValidators', _configGlobalValidators),
      new ArrayDisplayRefTableActionOptionConfig('functions', _configFunction)
    ];

    const _optionsAction: Array<ArrayDisplayRefTableActionOption> = [
      new ArrayDisplayRefTableActionOption(
        'UPDATE',
        CreateUpdateActionComponent,
        'edit',
        _configCreateUpdate
      ),
      new ArrayDisplayRefTableActionOption(
        'REMOVE',
        DeleteActionComponent,
        'delete_forever',
        _configRemove
      )
    ];

    const _configColumns: Array<ArrayDisplayRefTableColumn> = [
      new ArrayDisplayRefTableColumn('refComPrefDgtId', 'ID', false),
      new ArrayDisplayRefTableColumn('domain', 'DOMAIN', true),
      new ArrayDisplayRefTableColumn('groupType', 'GROUP_TYPE', true),
      new ArrayDisplayRefTableColumn('type', 'TYPE', true),
      new ArrayDisplayRefTableColumn('description', 'DESCRIPTION', true),
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

    return new ArrayDisplayRefTableOption('code', 'COMBINATION', _optionsAction, _globalOtionsAction, _configColumns);
  }

}
