import { Serializer } from './serializer';
import { ExternalIdentifier } from '../resources/external-identifier';
import { Converter } from './converter';
import { SignatureType } from '../common/signatures/signature-type';

export class ExternalIdentifierSerializer extends Serializer {

  fromJsonToObject(json: any): ExternalIdentifier {
    const id = (json.identifierId as number).toString();
    const type = (json.type as string).split('_')[0];
    const creation = Converter.convertToSignature(SignatureType.Creation, json);
    const modification = Converter.convertToSignature(SignatureType.Modification, json);
    const functionalData = Converter.convertToFunctionalData(json);
    const lastSeenDate = json.lastSeenDate;
    return new ExternalIdentifier(id, type, lastSeenDate, creation, modification, functionalData, json.identifier);
  }

  toJson(resource: ExternalIdentifier) {
    throw new Error('Method not implemented.');
  }

}
