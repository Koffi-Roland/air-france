import { Resource } from './resource';
import { Signature } from '../common/signatures/signature';
import { Right } from '../common/rights/right';
import { OperationType } from '../common/rights/operation-type.enum';
import { ResourceType } from './resource-type';

export class RoleUccr extends Resource {
  constructor(
    protected _id: string,
    private _ceid: string,
    private _type: string,
    private _status: string,
    private _startValidityDate: Date,
    private _endValidityDate: Date,
    private _signatureCreation: Signature,
    private _signatureModification: Signature
  ) {
    super(_id, '#4491FE', ResourceType.UCCRRole);
  }

  get ceid(): string {
    return this._ceid;
  }

  get type(): string {
    return this._type;
  }

  get status(): string {
    return this._status;
  }

  get startValidityDate(): Date {
    return this._startValidityDate;
  }

  get endValidityDate(): Date {
    return this._endValidityDate;
  }

  get signatureCreation(): Signature {
    return this._signatureCreation;
  }

  get signatureModification(): Signature {
    return this._signatureModification;
  }

}
