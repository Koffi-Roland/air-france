import { LastActivity } from '../common/last-activity';
import { Signature } from '../common/signatures/signature';

export class ManagementData {

    constructor(private _mergeGIN: string, private _mergeDate: Date,
        private _signatureCreation: Signature, private _signatureModification: Signature, private _lastActivity:LastActivity) {}


    get mergeGIN(): string {
        return this._mergeGIN;
    }
    get mergeDate(): Date {
        return this._mergeDate;
    }
    get signatureCreation(): Signature {
        return this._signatureCreation;
    }
    get signatureModification(): Signature {
        return this._signatureModification;
    }
    get lastActivity(): LastActivity
    {
        return this._lastActivity;
    }
}
