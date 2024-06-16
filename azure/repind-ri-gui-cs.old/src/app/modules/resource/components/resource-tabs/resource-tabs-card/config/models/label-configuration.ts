export class LabelConfiguration {
    constructor (
        private _prefix: string,
        private _resourceAttribute: string,
        private _suffix: string,
        private _isTranslatable: boolean,
        private _hasMultipleValues: boolean,
        private _isDate: boolean = false) {}
    get prefix(): string {
        return this._prefix;
    }
    get resourceAttribute(): string {
        return this._resourceAttribute;
    }
    get suffix(): string {
        return this._suffix;
    }
    get isTranslatable(): boolean {
        return this._isTranslatable;
    }
    get hasMultipleValues(): boolean {
        return this._hasMultipleValues;
    }
    get isDate(): boolean {
        return this._isDate;
    }
}
