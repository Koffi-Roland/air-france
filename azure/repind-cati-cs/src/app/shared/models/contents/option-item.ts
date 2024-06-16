export class OptionItem {

    private _value: any;
    private _mainText: string;
    private _secondaryText: string;

    constructor(value: any, mainText?: string, secondaryText?: string) {
        this._value = value;
        this._mainText = mainText || value;
        this._secondaryText = secondaryText;
    }

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
