export class PreferenceValueConfiguration {

    constructor (private _minLength: number, private _maxLength: number, private _type: string, private _condition: string) {}

    get minLength(): number {
        return this._minLength;
    }

    get maxLength(): number {
        return this._maxLength;
    }

    get type(): string {
        return this._type;
    }

    get isRequired(): boolean {
        return (this._condition === 'M');
    }

}
