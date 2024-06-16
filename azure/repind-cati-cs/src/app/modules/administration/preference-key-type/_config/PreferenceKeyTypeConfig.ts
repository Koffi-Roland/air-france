import { Validators } from "@angular/forms";
import { CreateUpdateActionComponent } from "src/app/shared/arrayDisplayRefTable/arrayDisplayRefTableAction/genericActions/createUpdateAction/createUpdateAction.component";
import { DeleteActionComponent } from "src/app/shared/arrayDisplayRefTable/arrayDisplayRefTableAction/genericActions/deleteAction/deleteAction.component";
import { ArrayDisplayRefTableActionOption, ArrayDisplayRefTableActionOptionConfig } from "src/app/shared/arrayDisplayRefTable/_models/ArrayDisplayRefTableActionOption";
import { ArrayDisplayRefTableColumn } from "src/app/shared/arrayDisplayRefTable/_models/ArrayDisplayRefTableColumn";
import { ArrayDisplayRefTableOption } from "src/app/shared/arrayDisplayRefTable/_models/ArrayDisplayRefTableOption";
import { AutoFill } from "src/app/shared/models/contents/autofill";
import { FieldConfig } from "src/app/shared/models/forms/field-config";
import { FieldValueTuple } from "src/app/shared/models/forms/field-value-tuple";
import { StepperFormConfig } from "src/app/shared/models/forms/stepper-form/stepper-form-config";

export class PreferenceKeyTypeConfig {

    private _allDataTypes;
    private _allConditions;
    private _allKeys;
    private _allTypes;
    private _allTypesForTable;

    constructor(allDataTypes, allConditions, allKeys, allTypes, allTypesForTable) {
        this._allDataTypes = allDataTypes;
        this._allConditions = allConditions;
        this._allKeys = allKeys;
        this._allTypes = allTypes;
        this._allTypesForTable = allTypesForTable;
    }

    get option(): ArrayDisplayRefTableOption {

        const _configRemove: Array<ArrayDisplayRefTableActionOptionConfig> = [
            new ArrayDisplayRefTableActionOptionConfig('type', 'PREFERENCE_KEY_TYPE')
        ];

        const _configFirstStepForm: Array<FieldConfig> = [
            {
                type: 'select',
                name: 'dataType',
                label: 'DATA-TYPE',
                options: this._allDataTypes,
                validations: [{
                    name: 'required',
                    validator: Validators.required,
                    message: 'FIELD-REQUIRED'
                }],
                autoFill: new AutoFill(new Map<string, FieldValueTuple[]>([
                    ['Boolean', [{ field: 'minLength', value: 1 }, { field: 'maxLength', value: 1 }]],
                    ['Date', [{ field: 'minLength', value: 6 }, { field: 'maxLength', value: 50 }]],
                ]), ['minLength', 'maxLength'])
            },
            {
                type: 'input',
                name: 'minLength',
                label: 'MIN-LENGTH',
                width: '49%',
                validations: [{
                    name: 'required',
                    validator: Validators.required,
                    message: 'FIELD-REQUIRED'
                },
                {
                    name: 'maxLength',
                    validator: Validators.maxLength(12),
                    message: 'MAX-LENGTH-MSG'
                },
                {
                    name: 'pattern',
                    validator: Validators.pattern(String.raw`^[0-9]+$`),
                    message: 'NOT_A_NUMBER_MESSAGE'
                }
                ]
            },
            {
                type: 'input',
                name: 'maxLength',
                label: 'MAX-LENGTH',
                width: '49%',
                validations: [{
                    name: 'required',
                    validator: Validators.required,
                    message: 'FIELD-REQUIRED'
                },
                {
                    name: 'max',
                    validator: Validators.maxLength(12),
                    message: 'MAX-LENGTH-MSG'
                },
                {
                    name: 'pattern',
                    validator: Validators.pattern(String.raw`^[0-9]+$`),
                    message: 'NOT_A_NUMBER_MESSAGE'
                }
                ]
            },
            {
                type: 'select',
                name: 'key',
                label: 'PREFERENCE-DATA',
                options: this._allKeys,
                validations: [{
                    name: 'required',
                    validator: Validators.required,
                    message: 'FIELD-REQUIRED'
                }]
            },
            {
                type: 'toggleslider',
                name: 'condition',
                label: 'MANDATORY',
                options: this._allConditions,
                validations: [{
                    name: 'required',
                    validator: Validators.required,
                    message: 'FIELD-REQUIRED'
                }]
            }
        ];

        const _configSecondStepForm: Array<FieldConfig> = [
            {
                type: 'tablemultiselect',
                name: 'listPreferenceType',
                label: 'PREFERENCE',
                options: this._allTypesForTable,
                tableMultiselectIdName: 'code',
                validations: [{
                    name: 'required',
                    validator: Validators.required,
                    message: 'FIELD-REQUIRED'
                }]
            }
        ];

        const _configSteps: StepperFormConfig = {
            isWithSummary: true,
            steps: [
                {
                    label: 'FILL-PREFERENCE-INFO',
                    errorMessage: 'There is missing required field',
                    fieldConfig: _configFirstStepForm
                },
                {
                    label: 'LINK-PREFERENCE-TYPE',
                    errorMessage: 'Select at least one row',
                    fieldConfig: _configSecondStepForm
                }
            ]
        };

        const _configFormUpdate: Array<FieldConfig> = _configFirstStepForm.concat([
            {
                type: 'select',
                name: 'type',
                label: 'PREFERENCE',
                options: this._allTypes,
                validations: [{
                    name: 'required',
                    validator: Validators.required,
                    message: 'FIELD-REQUIRED'
                }]
            }
        ])


        const _configCreate: Array<ArrayDisplayRefTableActionOptionConfig> = [
            new ArrayDisplayRefTableActionOptionConfig('type', 'PREFERENCE_KEY_TYPE'),
            new ArrayDisplayRefTableActionOptionConfig('id', 'refId'),
            new ArrayDisplayRefTableActionOptionConfig('stepperForm', _configSteps)
        ];

        const _configUpdate: Array<ArrayDisplayRefTableActionOptionConfig> = [
            new ArrayDisplayRefTableActionOptionConfig('type', 'PREFERENCE_KEY_TYPE'),
            new ArrayDisplayRefTableActionOptionConfig('form', _configFormUpdate),
            new ArrayDisplayRefTableActionOptionConfig('id', 'refId')
        ];

        const _optionsAction: Array<ArrayDisplayRefTableActionOption> = [
            new ArrayDisplayRefTableActionOption(
                'REMOVE',
                DeleteActionComponent,
                'delete_forever',
                _configRemove
            ),
            new ArrayDisplayRefTableActionOption(
                'UPDATE',
                CreateUpdateActionComponent,
                'edit',
                _configUpdate
            )
        ];

        const _globalOptionsAction: Array<ArrayDisplayRefTableActionOption> = [
            new ArrayDisplayRefTableActionOption(
                'CREATE',
                CreateUpdateActionComponent,
                'add',
                _configCreate
            )
        ];

        const _configColumns: Array<ArrayDisplayRefTableColumn> = [
            new ArrayDisplayRefTableColumn('refId', 'REF-ID', false),
            new ArrayDisplayRefTableColumn('key', 'KEY', true),
            new ArrayDisplayRefTableColumn('type', 'TYPE', true),
            new ArrayDisplayRefTableColumn('minLength', 'MIN-LENGTH', true),
            new ArrayDisplayRefTableColumn('maxLength', 'MAX-LENGTH', true),
            new ArrayDisplayRefTableColumn('dataType', 'DATA-TYPE', true),
            new ArrayDisplayRefTableColumn('condition', 'CONDITION', true)
        ];

        return new ArrayDisplayRefTableOption('refId', 'PREFERENCE_KEY_TYPE',
            _optionsAction, _globalOptionsAction, _configColumns);
    }
}