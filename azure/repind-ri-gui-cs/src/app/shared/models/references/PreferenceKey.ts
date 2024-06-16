import { PreferenceValueConfiguration } from './PreferenceValueConfiguration';

export class PreferenceKey {

    constructor (private _key: string, private _type: string, private _valueConfig: PreferenceValueConfiguration) {}

    get key(): string {
        return this._key;
    }

    get type(): string {
        return this._type;
    }

    get valueConfig(): PreferenceValueConfiguration {
        return this._valueConfig;
    }

}
