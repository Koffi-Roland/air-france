import { AbstractSearchRequest } from './abstract-search-request';
import { SearchType } from './search-type.enum';

export class TelecomSearchRequest extends AbstractSearchRequest {

    constructor(private _phoneNumber: string) {
        super(SearchType.TELECOM, 'search/telecom');
    }

    get toJSON(): any {
        return `{
            "phoneNumber": "${this._phoneNumber}"
        }`;
    }

}
