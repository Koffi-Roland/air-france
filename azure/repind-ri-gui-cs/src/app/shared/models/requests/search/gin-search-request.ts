import { AbstractSearchRequest } from './abstract-search-request';
import { SearchType } from './search-type.enum';

export class GinSearchRequest extends AbstractSearchRequest {

    constructor(private _gin: string) {
        super(SearchType.GIN_OR_CIN);
    }

    get gin(): string {
        return this._gin;
    }

    get toJSON(): any {
        return null;
    }

}
