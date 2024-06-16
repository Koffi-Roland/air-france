import { Serializer } from './serializer';
import { Contract } from '../resources/contract';
import { Converter } from './converter';
import { SignatureType } from '../common/signatures/signature-type';

export class ContractSerializer extends Serializer {


  fromJsonToObject(json: any): Contract {
    const id = json.identifiant;
    const contractNo = json.contractNumber;
    const productType = json.productType;
    const productSubType = json.productSubType;
    const contractType = json.contractType;
    const status = json.status;
    const company = json.companyCode;
    const startingDate = json.startingDate;
    const endingDate = json.endingDate;
    const tier = json.tier;
    const creation = Converter.convertToSignature(SignatureType.Creation, json);
    const modification = Converter.convertToSignature(SignatureType.Modification, json);
    const memberType = json.memberType;
    return new Contract(id, contractNo, contractType, productType, productSubType, status, company,
      startingDate, endingDate, tier, creation, modification, memberType);
  }

  toJson(resource: Contract) {
    return `{
      "identifiant": "${(resource.id) ? resource.id : ''}",
      "contractNumber": "${(resource.contractNumber) ? resource.contractNumber : ''}",
      "contractType": "${(resource.contractType) ? resource.contractType : ''}",
      "status": "${(resource.status) ? resource.status : ''}",
      "productType": "${(resource.productType) ? resource.productType : ''}",
      "productSubType": "${(resource.productSubType) ? resource.productSubType : ''}",
      "tier": "${(resource.tier) ? resource.tier : ''}",
      "companyCode": "${(resource.company) ? resource.company : ''}",
      "endingDate": "${(resource.endingDate) ? resource.endingDate : ''}",
      "startingDate": "${(resource.startingDate) ? resource.startingDate : ''}",
      "memberType": "${(resource.memberType) ? resource.memberType : ''}"
    }`;
  }

}
