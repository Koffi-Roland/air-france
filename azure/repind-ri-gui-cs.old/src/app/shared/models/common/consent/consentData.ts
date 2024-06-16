import { Signature } from '../signatures/signature';

export class ConsentData {

    constructor(
        private _id: number,
        private _type: string,
        private _isConsent: string,
        private _dateConsent: Date,
        private _creationSignature: Signature,
        private _modificationSignature: Signature,
    ) { }

    get id(): number {
        return this._id;
    }

    get type(): string {
        return this._type;
    }

    get isConsent(): string {
        return this._isConsent;
    }

    set isConsent(isConsent: string) {
        this._isConsent = isConsent;
    }

    get dateConsent(): Date {
        return this._dateConsent;
    }

    get creationSignature(): Signature {
        return this._creationSignature;
    }

    get modificationSignature(): Signature {
        return this._modificationSignature;
    }

}
