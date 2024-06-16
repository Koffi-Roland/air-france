export class ToggleSliderOption {

    constructor(private _trueValue: string, private _falseValue: string) { }

    get trueValue(): string {
        return this._trueValue;
    }

    get falseValue(): string {
        return this._falseValue;
    }

    getValue(isActivated: boolean): string {
        return isActivated ? this._trueValue : this._falseValue;
    }

}