import { Status } from '../common/status';

export class BasicIndividualData {

    constructor(private _gin: string, private _status: Status, private _lastname: string, private _firstname: string,
        private _civility: string, private _birthdate: Date, private _postalAddress: string) { }

    get gin(): string {
        return this._gin;
    }

    get status(): Status {
        return this._status;
    }

    get lastname(): string {
        return this._lastname;
    }

    get firstname(): string {
        return this._firstname;
    }

    get civility(): string {
        return this._civility;
    }

    get birthdate(): Date {
        return this._birthdate;
    }

    get postalAddress(): string {
        return this._postalAddress;
    }

}
