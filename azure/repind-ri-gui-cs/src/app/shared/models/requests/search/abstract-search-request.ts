import {SearchType} from './search-type.enum';

export abstract class AbstractSearchRequest {

  private _sentDate: Date;

  constructor(private _type: SearchType,
              private _isMergeIncluded?: boolean,
              private _url?: string) {
    this._sentDate = new Date();
  }

  get sentDate(): Date {
    return this._sentDate;
  }

  get type(): SearchType {
    return this._type;
  }

  get url(): string {
    return this._url;
  }

  get isMergeIncluded(): boolean {
    return this._isMergeIncluded;
  }

  public abstract get toJSON(): any;

}
