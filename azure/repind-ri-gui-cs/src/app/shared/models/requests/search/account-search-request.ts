import {AbstractSearchRequest} from './abstract-search-request';
import {SearchType} from './search-type.enum';

export class AccountSearchRequest extends AbstractSearchRequest {

  constructor(private _email: string | null, private _cin: string | null) {
    super(SearchType.ACCOUNT, false, 'search/account');
  }

  get toJSON(): any {
    const toStringOrNull = (value: string | null) => {
      return value === null ? 'null' : `"${value}"`;
    };
    return `{
            "email": ${toStringOrNull(this._email)},
            "cin": ${toStringOrNull(this._cin)}
        }`;
  }

}
