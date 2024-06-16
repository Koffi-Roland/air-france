import { Serializer } from './serializer';
import { Consent } from '../resources/consent';
import { ConsentData } from '../common/consent/consentData';
import { Converter } from './converter';
import { SignatureType } from '../common/signatures/signature-type';
import { Signature } from '../common/signatures/signature';
import { ConsentRequest } from '../common/consent/consentRequest';



export class ConsentSerializer extends Serializer {

  /**
   * ### Overwrite of the `Serializer` parent's class.
   * *refactor the content of a JSON `Consent` list from the server.*
   * * Create a new `Consent`, for each `ConsentData` of the server response.
   * @param json - **JSON** List of Consent from the API
   */

  fromJsonToArrayObject(json: any): Consent[] {
    const consents: Consent[] = [];
    json.forEach(jsonConsent => {
      jsonConsent.data.forEach(jsonConsentData => {
        const consent = jsonConsent;
        consent.consentData = jsonConsentData;
        consents.push(this.fromJsonToObject(consent));
      });
    });
    return consents;
  }

  fromJsonToObject(jsonConsent: any): Consent {
    let consent: Consent;
    const type: string = jsonConsent.type;
    const creationSignature: Signature = jsonConsent;
    const modificationSignature: Signature = jsonConsent;
    const consentData: ConsentData = this.convertToConsentData(jsonConsent.consentData);
    consent = new Consent(
      type,
      creationSignature,
      modificationSignature,
      consentData
    );
    return consent;
  }

  /**
   * overrides the serializer toJson() method
   * it will provide the right request body to update a consent
   * 
   * @param resource consent
   * @returns ConsentUpdateRequest
   */
  toJson(resource: Consent) {
    let updateRequest: ConsentRequest = {
      application: 'RIGUI/IHM',
      id: resource.consentData.id,
      isConsent: resource.consentDataIsConsent
    }
    return updateRequest;
  }

  convertToConsentData(jsonConsentData: any): ConsentData {
    const id: number = jsonConsentData.id;
    const type: string = jsonConsentData.type;
    const isConsent: string = jsonConsentData.isConsent;
    const dateConsent: Date = jsonConsentData.dateConsent;
    const creationSignature: Signature = Converter.convertToSignature(SignatureType.Creation, jsonConsentData);
    const modificationSignature: Signature = Converter.convertToSignature(SignatureType.Modification, jsonConsentData);
    const consentData: ConsentData = new ConsentData(
      id, type, isConsent, dateConsent, creationSignature, modificationSignature
    );
    return consentData;
  }

}
