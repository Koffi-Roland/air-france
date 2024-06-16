import { ReferenceData } from '../references/ReferenceData';

export class PreferenceData {

    private _refData: ReferenceData;

    constructor (private _id: number, private _key: string, private _value: string) {}

    get id(): number {
        return this._id;
    }

    get key(): string {
        return this._key;
    }

    get value(): string {
        return this._value;
    }

    set value(str: string) {
        this._value = str;
    }

    get refData(): ReferenceData {
        return this._refData;
    }

    set refData(ref: ReferenceData) {
        this._refData = ref;
    }

}
