import { Resource } from '../resource';
import { Signature } from '../../common/signatures/signature';
import { Individual } from '../../individual/individual';
import { ResourceType } from '../resource-type';

export class Delegation extends Resource {
  constructor(
    protected _id: string,
    private _status: string,
    private _type: string,
    private _creationSignature: Signature,
    private _modificationSignature: Signature,
    private _sender: string,
    private _delegate: Individual,
    private _delegator: Individual
  ) {
    super(_id, '#FF5234', ResourceType.Delegation);
  }

  get status(): string {
    return this._status;
  }

  set status(status: string) {
    this._status = status;
  }

  get type(): string {
    return this._type;
  }

  get creationSignature(): Signature {
    return this._creationSignature;
  }

  get modificationSignature(): Signature {
    return this._modificationSignature;
  }

  get sender(): string {
    return this._sender;
  }

  get delegate(): Individual {
    return this._delegate;
  }

  get delegator(): Individual {
    return this._delegator;
  }

  get delegatorCivilityAndLastName(): string {
    return this._delegator.civility + ' ' + this._delegator.lastname;
  }

  get delegateCivilityAndLastName(): string {
    return this._delegate.civility + ' ' + this._delegate.lastname;
  }

}
