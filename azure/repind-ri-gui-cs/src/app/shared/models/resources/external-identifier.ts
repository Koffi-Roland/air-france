import { Resource } from './resource';
import { Signature } from '../common/signatures/signature';
import { ExternalIdentifierData } from '../common/external-identifier-data';
import { Right } from '../common/rights/right';
import { OperationType } from '../common/rights/operation-type.enum';
import { ResourceType } from './resource-type';

export class ExternalIdentifier extends Resource {
  constructor(
    protected _id: string,
    private _type: string,
    private _lastSeenDate: Date,
    private _creationSignature: Signature,
    private _modificationSignature: Signature,
    private _functionalData: ExternalIdentifierData[],
    private _identifier: string
  ) {
    super(_id, '#FF9379', ResourceType.ExternalIdentifier);
  }

  get type(): string {
    return this._type;
  }

  get lastSeenDate(): Date {
    return this._lastSeenDate;
  }

  get creationSignature(): Signature {
    return this._creationSignature;
  }

  get modificationSignature(): Signature {
    return this._modificationSignature;
  }

  get functionalData(): ExternalIdentifierData[] {
    return this._functionalData;
  }

  get functionalDataCount(): number {
    return this._functionalData.length;
  }

  get identifier(): string {
    return this._identifier;
  }

}
