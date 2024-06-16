/**
 * Class last activity: Last modification according to the given gin
 */
export class LastActivity {

    constructor(private _gin: string, private _dateModification: Date, private _sourceModification: string, private _signatureModification: string, private _siteModification:string) 
    {  }

 
    get gin(): string
    {
        return this._gin;
    }

    get dateModification(): Date 
    {
        return this._dateModification;
    }

    get sourceModification(): string 
    {
        return this._sourceModification;
    }

    get signatureModification(): string 
    {
        return this._signatureModification;
    }
    get siteModification(): string 
    {
        return this._siteModification;
    }

}
