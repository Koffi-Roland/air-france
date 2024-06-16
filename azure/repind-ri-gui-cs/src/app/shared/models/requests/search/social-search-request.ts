import { AbstractSearchRequest } from './abstract-search-request';
import { SearchType } from './search-type.enum';
import {StringHelper} from '../../../utils/helpers/string.helper';

export class SocialSearchRequest extends AbstractSearchRequest {

    constructor(private _socialID: string, private _socialType: string, private _merge: boolean = false) {
        super(SearchType.EMAIL, _merge,'search/social');
    }

    get toJSON(): any {
        return `{
            "socialID": "${this._socialID}",
            "socialType": "${this._socialType}",
        }`;
    }
}
