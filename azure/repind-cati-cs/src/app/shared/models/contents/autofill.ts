import { FieldValueTuple } from "../forms/field-value-tuple";

export class AutoFill {
    private _toMatch: Map<string, FieldValueTuple[]>; // for a 'string' value of this field, patch every fields with value according to tuple (field,value)
    private _resetAutofill: string[]; // it indicates the fields to reset when there is no match
    private _toReset: Map<string, boolean>; // it indicates if the fields have to be reseted

    constructor(toMatch: Map<string, FieldValueTuple[]>, resetAutofill: string[]) {
        this._toMatch = toMatch;
        this._resetAutofill = resetAutofill;
        this._toReset = new Map();
    }

    public get toMatch(): Map<string, FieldValueTuple[]> {
        return this._toMatch;
    }

    public get resetAutofill(): string[] {
        return this._resetAutofill;
    }

    public get toReset(): Map<string, boolean> {
        return this._toReset;
    }

    public updateToReset(key: string, value: boolean) {
        this._toReset.set(key, value);
    }

}