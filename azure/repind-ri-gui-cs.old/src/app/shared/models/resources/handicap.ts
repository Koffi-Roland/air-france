import { Resource } from './resource';
import { Signature } from '../common/signatures/signature';
import { HandicapData } from '../common/handicapData';
import { ResourceType } from './resource-type';

export class Handicap extends Resource {
  constructor(
    protected _id: string,
    private _type: string,
    private _code: string,
    private _creationSignature: Signature,
    private _modificationSignature: Signature,
    private _handicapData: HandicapData[]
  ) {
    super(_id, '#adff83', ResourceType.Handicap);
  }

  get type(): string {
    return this._type;
  }

  get code(): string {
    return this._code;
  }

  get creationSignature(): Signature {
    return this._creationSignature;
  }

  get modificationSignature(): Signature {
    return this._modificationSignature;
  }

  get handicapData(): HandicapData[] {
    return this._handicapData;
  }

  get handicapDataCount(): number {
    return this._handicapData.length;
  }
}
