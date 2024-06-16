import { CreateUpdateActionComponent } from "src/app/shared/arrayDisplayRefTable/arrayDisplayRefTableAction/genericActions/createUpdateAction/createUpdateAction.component";
import { ArrayDisplayRefTableActionOption, ArrayDisplayRefTableActionOptionConfig } from "src/app/shared/arrayDisplayRefTable/_models/ArrayDisplayRefTableActionOption";
import { ArrayDisplayRefTableColumn } from "src/app/shared/arrayDisplayRefTable/_models/ArrayDisplayRefTableColumn";
import { ArrayDisplayRefTableOption } from "src/app/shared/arrayDisplayRefTable/_models/ArrayDisplayRefTableOption";
import { ToggleSliderOption } from "src/app/shared/models/contents/toggle-slider-option";

import { FieldConfig } from "src/app/shared/models/forms/field-config";

export class PaysConfig {
    private static _configForm: Array<FieldConfig> = [
        {
            type: 'input',
            name: 'codePays',
            label: 'CODE_PAYS',
            disableForUpdate: true,
        },
        {
            type: 'toggleslider',
            name: 'normalisable',
            label: 'NORMALISABLE',
            options: [
                new ToggleSliderOption("O", "N")
            ]
        },
    ];


    private static _configColumns: Array<ArrayDisplayRefTableColumn> = [
        new ArrayDisplayRefTableColumn('codePays', 'CODE_PAYS', true),
        new ArrayDisplayRefTableColumn('libellePays', 'LIBELLE_PAYS', true),
        new ArrayDisplayRefTableColumn('codeIata', 'CODE_IATA', false),
        new ArrayDisplayRefTableColumn('libellePaysEn', 'LIBELLE_PAYS_EN', true),
        new ArrayDisplayRefTableColumn('codeGestionCP', 'CODE_GESTION_CP', true),
        new ArrayDisplayRefTableColumn('codeCapitale', 'CODE_CAPITALE', true),
        new ArrayDisplayRefTableColumn('normalisable', 'NORMALISABLE', true),
        new ArrayDisplayRefTableColumn('formatAdr', 'FORMAT_ADR', false),
        new ArrayDisplayRefTableColumn('iformatAdr', 'I_FORMAT_ADR', false),
        new ArrayDisplayRefTableColumn('forcage', 'FORCAGE', true),
        new ArrayDisplayRefTableColumn('iso3Code', 'CODE_ISO3', false),
    ];

    private static _globalOtionsAction: Array<ArrayDisplayRefTableActionOption> = [];

    private static _configCreateUpdate: Array<ArrayDisplayRefTableActionOptionConfig> = [
        new ArrayDisplayRefTableActionOptionConfig('type', 'PAYS'),
        new ArrayDisplayRefTableActionOptionConfig('form', PaysConfig._configForm),
        new ArrayDisplayRefTableActionOptionConfig('id', 'codePays')
    ];

    private static _optionsAction: Array<ArrayDisplayRefTableActionOption> = [
        new ArrayDisplayRefTableActionOption(
            'UPDATE',
            CreateUpdateActionComponent,
            'edit',
            PaysConfig._configCreateUpdate
        )
    ];

    private static _option: ArrayDisplayRefTableOption = new ArrayDisplayRefTableOption(
        'codePays', 'PAYS', PaysConfig._optionsAction, PaysConfig._globalOtionsAction, PaysConfig._configColumns);

    static get option(): ArrayDisplayRefTableOption {
        return PaysConfig._option;
    }
}