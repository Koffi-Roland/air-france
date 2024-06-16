import { Resource } from './resource';
import { Type } from '../common/type';
import { Status } from '../common/status';
import { Signature } from '../common/signatures/signature';
import { AuthorizationMailing } from '../common/authorization-mailing';
import { Right } from '../common/rights/right';
import { OperationType } from '../common/rights/operation-type.enum';
import { ResourceType } from './resource-type';

/**
 * This class represent the email resource and is used to represent that kind of data in the UI.
 */
export class Email extends Resource {

  /**
   * The constructor of this class has 7 parameters representing all the
   * attributes of an email.
   * @param _id - the unique identifier
   * @param _type - the code medium (Home & Business)
   * @param _status - the status medium (Valid, Invalid & Archived)
   * @param _emailAddress - the email address
   * @param _mailingAuthorization - the mailing authorization
   * @param _signatureCreation - the creation signature
   * @param _signatureModification - the modification signature
   */
  constructor(
    protected _id: string,
    private _type: Type,
    private _status: Status,
    private _emailAddress: string,
    private _mailingAuthorization: AuthorizationMailing,
    private _signatureCreation: Signature,
    private _signatureModification: Signature,
    private _version: number
  ) {
    super(_id, '#516DBC', ResourceType.Email);
  }

  /** Accessors */

  get type(): Type { return this._type; }
  get status(): Status { return this._status; }
  get emailAddress(): string { return this._emailAddress; }
  get mailingAuthorization(): AuthorizationMailing { return this._mailingAuthorization; }
  get signatureCreation(): Signature { return this._signatureCreation; }
  get signatureModification(): Signature { return this._signatureModification; }
  get version(): number { return this._version; }

}
