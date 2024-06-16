export class AlertData {

    constructor(private _key: string, private _value: string) {}

    get key(): string {
        return this._key;
    }

    get value(): string {
        return this._value;
    }

}
