export class RadioButtonOption {

    constructor(private _value: string, private _label: string) { }

    get value(): string {
        return this._value;
    }

    get label(): string {
        return this._label;
    }

}
