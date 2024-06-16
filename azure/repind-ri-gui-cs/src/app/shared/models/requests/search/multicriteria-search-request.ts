import { AbstractSearchRequest } from './abstract-search-request';
import { SearchType } from './search-type.enum';

export class MulticriteriaSearchRequest extends AbstractSearchRequest {

    constructor(private _firstname: string, private _lastname: string, private _merge: boolean = false) {
        super(SearchType.SOCIAL, _merge,'search/multicriteria');
    }

    get toJSON(): any {
        return `{
            "firstname": "${this._firstname}",
            "lastname": "${this._lastname}"
        }`;
    }

}
