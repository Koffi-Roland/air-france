import { Resource } from './resource';
import { Signature } from '../common/signatures/signature';
import { AlertData } from '../common/alert-data';
import { Right } from '../common/rights/right';
import { OperationType } from '../common/rights/operation-type.enum';
import { ResourceType } from './resource-type';

export class Alert extends Resource {
  constructor(
    protected _id: string,
    private _creationSignature: Signature,
    private _modificationSignature: Signature,
    private _type: string,
    private _optin: string,
    private _alertdata: AlertData[]
  ) {
    super(_id, '#96AFBE', ResourceType.Alert);
  }

  get creationSignature(): Signature {
    return this._creationSignature;
  }

  get modificationSignature(): Signature {
    return this._modificationSignature;
  }

  get type(): string {
    return this._type;
  }

  set optin(optin: string) {
    this._optin = optin;
  }

  get optin(): string {
    return this._optin;
  }

  get alertdata(): AlertData[] {
    return this._alertdata;
  }

  get alertDataCount(): number {
    return this._alertdata.length;
  }

}
