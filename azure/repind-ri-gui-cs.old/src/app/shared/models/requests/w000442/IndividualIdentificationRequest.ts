export class IndividualIdentificationRequest {

    constructor (private _gin: string, private _firstname: string, private _lastname: string, private _birthdate: any,
        private _civility: string, private _middlename: string, private _aliasFirstname: string, private _aliasLastname: string,
        private _title: string, private _status: string, private _gender: string) {}

    get json(): any {
        return `{
            "gin": "${this._gin}",
            "firstname": "${this._firstname}",
            "lastname": "${this._lastname}",
            "birthdate": "${this._birthdate}",
            "civility": "${(this._civility) ? this._civility : ''}",
            "middlename": "${(this._middlename) ? this._middlename : ''}",
            "aliasFirstname": "${(this._aliasFirstname) ? this._aliasFirstname : ''}",
            "aliasLastname": "${(this._aliasLastname) ? this._aliasLastname : ''}",
            "title": "${(this._title) ? this._title : ''}",
            "status": "${(this._status) ? this._status : ''}",
            "gender": "${(this._gender) ? this._gender : ''}"
        }`;
    }

}
