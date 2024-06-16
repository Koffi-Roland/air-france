export class ProfileData {

    constructor(private _mailing: string, private _branch: string, private _communicationLanguage: string) {}

    get mailing(): string {
        return this._mailing;
    }
    get branch(): string {
        return this._branch;
    }
    get communicationLanguage(): string {
        return this._communicationLanguage;
    }

}
