import { Resource } from './resource';
import { Signature } from '../common/signatures/signature';
import { ResourceType } from './resource-type';

export class Contract extends Resource {

  constructor(
    protected _id: string,
    private _contractNumber: string,
    private _contractType: string,
    private _productType: string,
    private _productSubType: string,
    private _status: string,
    private _company: string,
    private _startingDate: any,
    private _endingDate: any,
    private _tier: string,
    private _creationSignature: Signature,
    private _modificationSignature: Signature,
    private _memberType: string
  ) {
    super(_id, '#FFC9E4', ResourceType.Contract);
  }

  /** Accessors */

  get memberType(): string {
    return this._memberType;
  }

  get contractNumber(): string {
    return this._contractNumber;
  }

  get contractType(): string {
    return this._contractType;
  }

  get productType(): string {
    return this._productType;
  }

  get productSubType(): string {
    return this._productSubType;
  }

  get status(): string {
    return this._status;
  }

  get company(): string {
    return this._company;
  }

  get startingDate(): Date {
    return this._startingDate;
  }

  get endingDate(): Date {
    return this._endingDate;
  }

  get tier(): string {
    return this._tier;
  }

  get creationSignature(): Signature {
    return this._creationSignature;
  }

  get modificationSignature(): Signature {
    return this._modificationSignature;
  }

}
