import { AbstractSearchRequest } from './abstract-search-request';
import { SearchType } from './search-type.enum';
import {StringHelper} from '../../../utils/helpers/string.helper';

export class EmailSearchRequest extends AbstractSearchRequest {

    constructor(private _email: string) {
        super(SearchType.EMAIL, 'search/email');
    }

    get toJSON(): any {
        this._email = StringHelper.escapeQuotesForEmails(this._email);
        return `{
            "email": "${this._email}"
        }`;
    }
}
