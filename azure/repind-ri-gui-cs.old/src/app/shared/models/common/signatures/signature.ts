import { SignatureType } from './signature-type';

export class Signature {

    constructor(private _type: SignatureType, private _signature: string, private _site: string, private _date: Date) { }

    get type(): SignatureType {
        return this._type;
    }

    get signature(): string {
        return this._signature;
    }

    get site(): string {
        return this._site;
    }

    get date(): Date {
        return this._date;
    }

}
