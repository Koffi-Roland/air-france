export class AttributeIcon {
    constructor (private _attr: string, private _key: string, private _value: string) {}

    get attr(): string {
        return this._attr;
    }

    get key(): string {
        return this._key;
    }

    get value(): string {
        return this._value;
    }
}
