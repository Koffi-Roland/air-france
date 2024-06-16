import { LabelConfiguration } from './label-configuration';

export class ResourceTabsCardContentConfiguration {
    constructor (private _labelConfigurations: LabelConfiguration[], private _chipLabelConfigurations: LabelConfiguration[]) {}

    get labelConfigurations(): LabelConfiguration[] {
        return this._labelConfigurations;
    }

    get chipLabelConfigurations(): LabelConfiguration[] {
        return this._chipLabelConfigurations;
    }
}
