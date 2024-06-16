import { Resource } from './resource';
import { Type } from '../common/type';
import { Status } from '../common/status';
import { TerminalType } from '../common/terminal-type';
import { Signature } from '../common/signatures/signature';
import { Right } from '../common/rights/right';
import { OperationType } from '../common/rights/operation-type.enum';
import { ResourceType } from './resource-type';

/**
 * This class represent the telecom resource and is used to represent that kind of data in the UI.
 */
export class Telecom extends Resource {

  /**
   * The constructor of this class has 10 parameters representing all the
   * attributes of a telecom.
   * @param _id - the unique identifier
   * @param _type - the code medium (Home & Business)
   * @param _status - the status medium (Valid, Invalid & Archived)
   * @param _terminalType - the terminal type
   * @param _countryCode - the country code
   * @param _regionCode - the region code
   * @param _phoneNumber - the phone number (not normalized)
   * @param _phoneNumberNotNormalized - the normalized phone number
   * @param _signatureCreation - the creation signature
   * @param _signatureModification - the modification signature
   */
  constructor(
    protected _id: string,
    private _type: Type,
    private _status: Status,
    private _terminalType: TerminalType,
    private _countryCode: string,
    private _regionCode: string,
    private _phoneNumber: string,
    private _phoneNumberNotNormalized: string,
    private _signatureCreation: Signature,
    private _signatureModification: Signature,
    private _version: number
  ) {
    super(_id, '#E1CFFF', ResourceType.Telecom);
  }

  /** Accessors */

  get type(): Type { return this._type; }
  get status(): Status { return this._status; }
  get terminalType(): TerminalType { return this._terminalType; }
  get countryCode(): string { return this._countryCode; }
  get regionCode(): string { return this._regionCode; }
  get phoneNumber(): string { return this._phoneNumber; }
  get phoneNumberNotNormalized(): string { return this._phoneNumberNotNormalized; }
  get signatureCreation(): Signature { return this._signatureCreation; }
  get signatureModification(): Signature { return this._signatureModification; }
  get version(): number { return this._version; }

}
