import { ResourceDetailsKeyVal } from './resource-details-key-val';
import { Signature } from '../../../../../../shared/models/common/signatures/signature';

export class ResourceDetailsCardContent {
  constructor(
    private _title: string,
    private _data: Array<ResourceDetailsKeyVal>,
    private _signatureCreation: Signature,
    private _signatureModification: Signature,
    private _isUpdatable: boolean,
    private _isDeletable: boolean,
    private _buttonLabel?: string
  ) {}

  get title(): string {
    return this._title;
  }

  get data(): Array<ResourceDetailsKeyVal> {
    return this._data;
  }

  get signatureCreation(): Signature {
    return this._signatureCreation;
  }

  get signatureModification(): Signature {
    return this._signatureModification;
  }

  get buttonLabel(): string {
    return this._buttonLabel;
  }

  get isUpdatable(): boolean {
    return this._isUpdatable;
  }

  get isDeletable(): boolean {
    return this._isDeletable;
  }
}
