import { AbstractSearchRequest } from './abstract-search-request';
import { SearchType } from './search-type.enum';

export class MulticriteriaSearchRequest extends AbstractSearchRequest {

    constructor(private _firstname: string, private _lastname: string) {
        super(SearchType.MULTICRITERIA, 'search/multicriteria');
    }

    get toJSON(): any {
        return `{
            "firstname": "${this._firstname}",
            "lastname": "${this._lastname}"
        }`;
    }

}
