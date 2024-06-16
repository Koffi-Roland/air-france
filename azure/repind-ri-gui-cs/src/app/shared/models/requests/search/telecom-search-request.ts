import { AbstractSearchRequest } from './abstract-search-request';
import { SearchType } from './search-type.enum';

export class TelecomSearchRequest extends AbstractSearchRequest {

    constructor(private _phoneNumber: string, private _merge: boolean = false) {
        super(SearchType.TELECOM, _merge,'search/telecom');
    }

    get toJSON(): any {
        return `{
            "phoneNumber": "${this._phoneNumber}"
        }`;
    }

}
