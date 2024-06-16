import { IndividualProfileRequest } from './IndividualProfileRequest';
import { IndividualIdentificationRequest } from './IndividualIdentificationRequest';

export class IndividualRequest {

    constructor (private _identification: IndividualIdentificationRequest, private _profile: IndividualProfileRequest) { }

    get json(): any {
        return `{
            "identification": ${this._identification.json},
            "profile": ${this._profile.json}
        }`;
    }

}
