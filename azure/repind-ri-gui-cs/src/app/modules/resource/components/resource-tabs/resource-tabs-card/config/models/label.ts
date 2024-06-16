import { LabelConfiguration } from './label-configuration';

export class Label {
    constructor (private _resource: any, private _configuration: LabelConfiguration) {}

    get text(): string {
        if (this._configuration.resourceAttribute === '') { return this._configuration.prefix; }
        const computedMiddleText = this._resource[this._configuration.resourceAttribute];
        return `${this._configuration.prefix}${computedMiddleText}${this._configuration.suffix}`;
    }

    get isTranslatable(): boolean {
        return this._configuration.isTranslatable;
    }

    get isDate(): boolean {
        return this._configuration.isDate;
    }
}
