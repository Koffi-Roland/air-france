export class IndividualProfileRequest {

    constructor (private _nat: string, private _branch: string, private _motherLanguage: string) {}

    get json(): any {
        return `{
            "nat": "${ (this._nat) ? this._nat : ''}",
            "branch": "${(this._branch) ? this._branch : ''}",
            "motherLanguage": "${(this._motherLanguage) ? this._motherLanguage : ''}"
        }`;
    }

}
