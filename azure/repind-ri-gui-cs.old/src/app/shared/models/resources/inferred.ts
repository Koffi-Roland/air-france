import { Resource } from './resource';
import { Signature } from '../common/signatures/signature';
import { InferredData } from '../common/inferredData';
import { ResourceType } from './resource-type';

export class Inferred extends Resource {
  constructor(
    protected _id: string,
    private _type: string,
    private _status: string,
    private _creationSignature: Signature,
    private _modificationSignature: Signature,
    private _inferredData: InferredData[]
  ) {
    super(_id, '#ff83b7', ResourceType.Inferred);
  }

  get type(): string {
    return this._type;
  }

  get status(): string {
    return this._status;
  }

  get creationSignature(): Signature {
    return this._creationSignature;
  }

  get modificationSignature(): Signature {
    return this._modificationSignature;
  }

  get inferredData(): InferredData[] {
    return this._inferredData;
  }

  get inferredDataCount(): number {
    return this._inferredData.length;
  }
}
