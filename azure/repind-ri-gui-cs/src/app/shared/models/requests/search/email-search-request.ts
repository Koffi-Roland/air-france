import { AbstractSearchRequest } from './abstract-search-request';
import { SearchType } from './search-type.enum';
import {StringHelper} from '../../../utils/helpers/string.helper';

export class EmailSearchRequest extends AbstractSearchRequest {

    constructor(private _email: string, private _merge: boolean = false) {
        super(SearchType.EMAIL, _merge,'search/email',);
    }

    get toJSON(): any {
        this._email = StringHelper.escapeQuotesForEmails(this._email);
        return `{
            "email": "${this._email}"
        }`;
    }
}
