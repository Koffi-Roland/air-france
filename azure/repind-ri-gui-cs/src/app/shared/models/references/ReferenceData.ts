import { ReferenceDataType } from './ReferenceDataType.enum';

export class ReferenceData {

    private _isRequired: boolean;

    constructor (private _type: ReferenceDataType, private _code: string, private _labelFR: string, private _labelEN: string) {}

    get type(): ReferenceDataType {
        return this._type;
    }

    get code(): string {
        return this._code;
    }

    get labelFR(): string {
        return (this._labelFR) ? this._labelFR : this._code;
    }

    get labelEN(): string {
        return (this._labelEN) ? this._labelEN : this._code;
    }

    get isRequired(): boolean {
        return this._isRequired;
    }

    set isRequired(b: boolean) {
        this._isRequired = b;
    }

}
