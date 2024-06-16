import { Signature } from './signatures/signature';

export class MarketLanguage {

    constructor(private _market: string, private _language: string, private _optin: string,
        private _channel: string, private _dateOfConsent: Date, private _creationSignature: Signature,
        private _modificationSignature: Signature) { }

    get market(): string {
        return this._market;
    }

    get language(): string {
        return this._language;
    }

    get optin(): string {
        return this._optin;
    }

    get channel(): string {
        return this._channel;
    }

    get dateOfConsent(): Date {
        return this._dateOfConsent;
    }

    get creationSignature(): Signature {
        return this._creationSignature;
    }

    get modificationSignature(): Signature {
        return this._modificationSignature;
    }

    copyAndUpdateCopy(optin: string) {
        let copy = this.shallowClone();
        copy._optin = optin;
        return copy;
    }

    shallowClone(): MarketLanguage {
        return Object.create(
            Object.getPrototypeOf(this),
            Object.getOwnPropertyDescriptors(this)
        );
    }
}
