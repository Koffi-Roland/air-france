import { Resource } from './resource';
import { Type } from '../common/type';
import { Status } from '../common/status';
import { Usage } from '../common/usage';
import { Signature } from '../common/signatures/signature';
import { Right } from '../common/rights/right';
import { OperationType } from '../common/rights/operation-type.enum';
import { ResourceType } from './resource-type';

/**
 * This class represent the address resource and is used to represent that kind of data in the UI.
 */
export class Address extends Resource {

  /**
   * The constructor of this class has 15 parameters representing all the
   * attributes of a postal address.
   * @param _id - the unique identifier
   * @param _type - the code medium (Home & Business)
   * @param _status - the status medium (Valid, Invalid & Archived)
   * @param _corporateName - the corporate name
   * @param _complement - the address complement
   * @param _locality - the locality
   * @param _numberAndStreet - the number and street
   * @param _zipCode - the zip code
   * @param _city - the city
   * @param _country - the country
   * @param _forced - whether the address is forced or not
   * @param _state - the state
   * @param _signatureCreation - the creation signature
   * @param _signatureModification - the modification signature
   * @param _usages - the usages
   */
  constructor(
    protected _id: string,
    private _type: Type,
    private _status: Status,
    private _corporateName: string,
    private _complement: string,
    private _locality: string,
    private _numberAndStreet: string,
    private _zipCode: string,
    private _city: string,
    private _country: string,
    private _forced: string,
    private _state: string,
    private _signatureCreation: Signature,
    private _signatureModification: Signature,
    private _usages: Array<Usage>,
    private _version: number
  ) {
    super(_id, '#83C1FF', ResourceType.Address);
  }

  /** Accessors */

  get type(): Type { return this._type; }
  get status(): Status { return this._status; }
  get corporateName(): string { return this._corporateName; }
  get complement(): string { return this._complement; }
  get locality(): string { return this._locality; }
  get numberAndStreet(): string { return this._numberAndStreet; }
  get zipCode(): string { return this._zipCode; }
  get city(): string { return this._city; }
  get country(): string { return this._country; }
  get forced(): string { return this._forced; }
  get signatureCreation(): Signature { return this._signatureCreation; }
  get signatureModification(): Signature { return this._signatureModification; }
  get state(): string { return this._state; }
  get usages(): Array<Usage> { return this._usages; }
  set forced(val: string) { this._forced = val; }
  get usagesCount(): number { return this._usages.length; }
  get version(): number { return this._version; }

}
