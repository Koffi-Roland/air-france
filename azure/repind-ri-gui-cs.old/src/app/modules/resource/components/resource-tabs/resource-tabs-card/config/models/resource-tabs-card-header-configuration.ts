import { LabelConfiguration } from './label-configuration';

export class ResourceTabsCardHeaderConfiguration {

    constructor(private _titleLabelConfiguration: LabelConfiguration, private _iconAttribute: string) {}

    get titleLabelConfiguration(): LabelConfiguration {
        return this._titleLabelConfiguration;
    }

    get iconAttribute(): string {
        return this._iconAttribute;
    }

}
