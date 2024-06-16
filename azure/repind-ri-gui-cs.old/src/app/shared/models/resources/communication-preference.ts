import { Resource } from './resource';
import { Signature } from '../common/signatures/signature';
import { MarketLanguage } from '../common/market-language';
import { Right } from '../common/rights/right';
import { OperationType } from '../common/rights/operation-type.enum';
import { ResourceType } from './resource-type';

export class CommunicationPreference extends Resource {

  constructor(
    protected _id: string,
    private _channel: string,
    private _domain: string,
    private _groupType: string,
    private _type: string,
    private _subscribe: string,
    private _dateOfConsent: Date,
    private _creationSignature: Signature,
    private _modificationSignature: Signature,
    private _marketLanguages: MarketLanguage[]
  ) {
    super(_id, '#FDFF67', ResourceType.CommunicationPreference);
  }

  get channel(): string {
    return this._channel;
  }

  get domain(): string {
    return this._domain;
  }

  get groupType(): string {
    return this._groupType;
  }

  get type(): string {
    return this._type;
  }

  get subscribe(): string {
    return this._subscribe;
  }

  set subscribe(str: string) {
    this._subscribe = str;
  }

  get optin(): string {
    return this._subscribe;
  }

  get dateOfConsent(): Date {
    return this._dateOfConsent;
  }

  get creationSignature(): Signature {
    return this._creationSignature;
  }

  get modificationSignature(): Signature {
    return this._modificationSignature;
  }

  get marketLanguages(): MarketLanguage[] {
    return this._marketLanguages;
  }

  get marketLanguagesCount(): number {
    return this._marketLanguages.length;
  }

  copyAndUpdateCopy(subscribe: string, marketLanguage: MarketLanguage[]) {
    const commPref = this.shallowClone();
    commPref._subscribe = subscribe;
    commPref._marketLanguages = marketLanguage;
    return commPref;
  }

  shallowClone(): CommunicationPreference {
    return Object.create(
      Object.getPrototypeOf(this),
      Object.getOwnPropertyDescriptors(this)
    );
  }



}
