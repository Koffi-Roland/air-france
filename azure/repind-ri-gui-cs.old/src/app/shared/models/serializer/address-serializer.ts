import { Serializer } from './serializer';
import { Address } from '../resources/address';
import { Type } from '../common/type';
import { Status } from '../common/status';
import { Usage } from '../common/usage';
import { SignatureType } from '../common/signatures/signature-type';
import { Signature } from '../common/signatures/signature';
import { Converter } from './converter';

export class AddressSerializer extends Serializer {



  fromJsonToObject(json: any): Address {
    const id: string = json.identifiant;
    const type: Type = Converter.convertToType(json.type);
    const status: Status = Converter.convertToStatus(json.status);
    const locality: string = json.locality;
    const state: string = json.state;
    const corpName: string = json.corporateName;
    const addrComplement: string = json.addressComplement;
    const noAndStreet: string = json.numberAndStreet;
    const zipCode: string = json.zipCode;
    const city: string = json.city;
    const country: string = json.country;
    const forced: string = json.forced;
    const signatureCreation: Signature = Converter.convertToSignature(SignatureType.Creation, json);
    const signatureModification: Signature = Converter.convertToSignature(SignatureType.Modification, json);
    const usages: Usage[] = Converter.convertToUsages(json);
    const version = json.version;
    return new Address(id, type, status, corpName, addrComplement, locality, noAndStreet, zipCode, city, country, forced,
      state, signatureCreation, signatureModification, usages, version);
  }

  toJson(resource: Address): any {
    const jsonAddress: any = `{
      "identifiant": "${(resource.id) ? resource.id : ''}",
      "version": "${(resource.version) ? resource.version : ''}",
      "type": "${resource.type}",
      "status": "${resource.status}",
      "corporateName": "${(resource.corporateName) ? resource.corporateName : ''}",
      "addressComplement": "${(resource.complement) ? resource.complement : ''}",
      "numberAndStreet": "${resource.numberAndStreet}",
      "locality": "${(resource.locality) ? resource.locality : ''}",
      "zipCode": "${resource.zipCode}",
      "city": "${resource.city}",
      "state": "${(resource.state) ? resource.state : ''}",
      "country": "${resource.country}",
      "forced": "${resource.forced}",
      "signature": {
        "modificationSignature": "${(resource.signatureModification && resource.signatureModification.signature)
        ? resource.signatureModification.signature : ''}",
        "modificationSite": "${(resource.signatureModification && resource.signatureModification.site) ?
        resource.signatureModification.site : ''}",
        "modificationDate": "${(resource.signatureModification && resource.signatureModification.date) ?
        resource.signatureModification.date : ''}",
        "creationSignature": "${(resource.signatureCreation) ? resource.signatureCreation.signature : ''}",
        "creationSite": "${(resource.signatureCreation) ? resource.signatureCreation.site : ''}",
        "creationDate": "${(resource.signatureCreation) ? resource.signatureCreation.date : ''}"
      }
    }`;
    return jsonAddress;
  }

}
