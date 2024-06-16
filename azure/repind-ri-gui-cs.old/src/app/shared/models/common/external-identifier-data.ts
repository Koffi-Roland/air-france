import { Signature } from './signatures/signature';

export class ExternalIdentifierData {

    constructor(private _key: string, private _value: string,
        private _creationSignature: Signature, private _modificationSignature: Signature) { }


    get key(): string {
        return this._key;
    }

    get value(): string {
        return this._value;
    }

    get creationSignature(): Signature {
        return this._creationSignature;
    }

    get modificationSignature(): Signature {
        return this._modificationSignature;
    }

}
