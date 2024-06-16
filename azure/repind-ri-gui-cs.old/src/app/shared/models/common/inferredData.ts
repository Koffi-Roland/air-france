import { Signature } from './signatures/signature';

export class InferredData {

    constructor (private _id: number, private _key: string, private _value: string, private _creationSignature: Signature,
      private _modificationSignature: Signature,) {}

    get id(): number {
        return this._id;
    }

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
