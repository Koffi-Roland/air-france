import { CreateUpdateActionComponent } from "src/app/shared/arrayDisplayRefTable/arrayDisplayRefTableAction/genericActions/createUpdateAction/createUpdateAction.component";
import { ArrayDisplayRefTableActionOption, ArrayDisplayRefTableActionOptionConfig } from "src/app/shared/arrayDisplayRefTable/_models/ArrayDisplayRefTableActionOption";
import { ArrayDisplayRefTableColumn } from "src/app/shared/arrayDisplayRefTable/_models/ArrayDisplayRefTableColumn";
import { ArrayDisplayRefTableOption } from "src/app/shared/arrayDisplayRefTable/_models/ArrayDisplayRefTableOption";

import { FieldConfig } from "src/app/shared/models/forms/field-config";
import {Validator} from "../../../../shared/models/forms/validator";
import {Validators} from "@angular/forms";
import {ValidatorsCustom} from "../../../../shared/widgets/validators/validators-custom.component";
import {DeleteActionComponent} from "../../../../shared/arrayDisplayRefTable/arrayDisplayRefTableAction/genericActions/deleteAction/deleteAction.component";

export class PcsNonContractScoreConfig {

  private static _labelsValidation: Validator[] = [
    {
      name: 'required',
      validator: Validators.required,
      message: 'FIELD-REQUIRED'
    }, {
      name: 'maxlength',
      validator: Validators.maxLength(30),
      message: 'MAX-LENGTH-MSG'
    }];

  private static _numberValidation: Validator[] = [
    {
      name: 'required',
      validator: Validators.required,
      message: 'FIELD-REQUIRED'
    }, {
      name: 'numInput',
      validator: Validators.pattern("^[0-9]*$"),
      message: 'NUMERIC-INPUT-FORMAT'
    }];

  private static _configForm: Array<FieldConfig> = [
    {
      type: 'input',
      name: 'code',
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
          validator: Validators.maxLength(10),
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
      name: 'libelle',
      label: 'LIBELLE',
      validations: PcsNonContractScoreConfig._labelsValidation
    },
    {
      type: 'input',
      name: 'score',
      label: 'SCORE',
      validations: PcsNonContractScoreConfig._numberValidation
    }];


    private static _configColumns: Array<ArrayDisplayRefTableColumn> = [
        new ArrayDisplayRefTableColumn('code', 'CODE', true),
        new ArrayDisplayRefTableColumn('libelle', 'LIBELLE', true),
        new ArrayDisplayRefTableColumn('score', 'SCORE', true),
    ];


  private static _configCreateUpdate: Array<ArrayDisplayRefTableActionOptionConfig> = [
    new ArrayDisplayRefTableActionOptionConfig('type', 'PCS-NON-CONTRACT-SCORE'),
    new ArrayDisplayRefTableActionOptionConfig('form', PcsNonContractScoreConfig._configForm),
    new ArrayDisplayRefTableActionOptionConfig('id', 'code')
  ];

    private static _globalOtionsAction: Array<ArrayDisplayRefTableActionOption> = [
      new ArrayDisplayRefTableActionOption(
        'CREATE',
        CreateUpdateActionComponent,
        'add',
        PcsNonContractScoreConfig._configCreateUpdate
      )
    ];

  private static _configRemove: Array<ArrayDisplayRefTableActionOptionConfig> = [
    new ArrayDisplayRefTableActionOptionConfig('type', 'PCS-NON-CONTRACT-SCORE')
  ];

    private static _optionsAction: Array<ArrayDisplayRefTableActionOption> = [
      new ArrayDisplayRefTableActionOption(
        'REMOVE',
        DeleteActionComponent,
        'delete_forever',
        PcsNonContractScoreConfig._configRemove
      ),
      new ArrayDisplayRefTableActionOption(
            'UPDATE',
            CreateUpdateActionComponent,
            'edit',
            PcsNonContractScoreConfig._configCreateUpdate
        )
    ];

    private static _option: ArrayDisplayRefTableOption = new ArrayDisplayRefTableOption(
        'code', 'PCS-NON-CONTRACT-SCORE', PcsNonContractScoreConfig._optionsAction, PcsNonContractScoreConfig._globalOtionsAction, PcsNonContractScoreConfig._configColumns);

    static get option(): ArrayDisplayRefTableOption {
        return PcsNonContractScoreConfig._option;
    }
}
