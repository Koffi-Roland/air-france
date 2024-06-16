import { Resource } from './resource';
import { Type } from '../common/type';
import { Status } from '../common/status';
import { Usage } from '../common/usage';
import { Signature } from '../common/signatures/signature';
import { Right } from '../common/rights/right';
import { OperationType } from '../common/rights/operation-type.enum';
import { ResourceType } from './resource-type';

/**
 * This class represent the account resource and is used to represent that kind of data in the UI.
 */
export class Account extends Resource {

  /**
   * The constructor of this class has 15 parameters representing all the
   * attributes of a postal address.
   * @param _id - the unique identifier
   * @param _type
   * @param _identifier
   * @param _email
   * @param _fb
   */
  constructor(
    protected _id: string,
    private _type: 'E' | 'F',
    private _identifier: string,
    private _email: string,
    private _fb: string
  ) {
    super(_id, '#66ff33', ResourceType.Account);
  }

  get type(): 'E' | 'F' {
    return this._type;
  }

  set type(value: 'E' | 'F') {
    this._type = value;
  }

  get identifier(): string {
    return this._identifier;
  }

  set identifier(value: string) {
    this._identifier = value;
  }

  get email(): string {
    return this._email;
  }

  set email(value: string) {
    this._email = value;
  }

  get fb(): string {
    return this._fb;
  }

  set fb(value: string) {
    this._fb = value;
  }


}
