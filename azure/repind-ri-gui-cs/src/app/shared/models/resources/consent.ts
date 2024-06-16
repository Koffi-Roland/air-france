import { Resource } from './resource';
import { Signature } from '../common/signatures/signature';
import { ResourceType } from './resource-type';
import { ConsentData } from '../common/consent/consentData';


export class Consent extends Resource {
  constructor(
    private _type: string,
    private _creationSignature: Signature,
    private _modificationSignature: Signature,
    private _consentData: ConsentData,
  ) {
    super(_type, '#7897b1', ResourceType.Consent);
    if (this._consentData.id) {
      this._id = this._consentData.id.toString();
    }
  }

  get type(): string {
    return this._type;
  }

  get consentDataType(): string {
    return this._consentData.type;
  }

  get consentDataIsConsent(): string {
    return this._consentData.isConsent;
  }

  set consentDataIsConsent(isConsent: string) {
    this._consentData.isConsent = isConsent;
  }

  get consentDataDateConsent(): Date {
    return this._consentData.dateConsent;
  }

  get modificationSignature(): Signature {
    return this._modificationSignature;
  }

  get creationSignature(): Signature {
    return this._creationSignature;
  }

  get consentData(): ConsentData {
    return this._consentData;
  }

}
