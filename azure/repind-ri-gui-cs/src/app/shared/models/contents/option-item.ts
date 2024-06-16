export class OptionItem {

    constructor(private _value: any, private _mainText: string, private _secondaryText: string) { }

    get value(): any {
        return this._value;
    }

    get mainText(): string {
        return this._mainText;
    }

    get secondaryText(): string {
        return this._secondaryText;
    }

}
