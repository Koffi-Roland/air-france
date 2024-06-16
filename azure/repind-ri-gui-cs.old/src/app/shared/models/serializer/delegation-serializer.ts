import { Serializer } from './serializer';
import { Delegation } from '../resources/delegation/delegation';
import { Converter } from './converter';
import { SignatureType } from '../common/signatures/signature-type';

export class DelegationSerializer extends Serializer {

  fromJsonToObject(json: any): Delegation {
    const id = (json.delegationId as number).toString();
    const status = json.status;
    const type = json.type;
    const creation = Converter.convertToSignature(SignatureType.Creation, json);
    const modification = Converter.convertToSignature(SignatureType.Modification, json);
    const sender = json.sender;
    const delegate = Converter.convertToIndividual(json.delegate);
    const delegator = Converter.convertToIndividual(json.delegator);
    return new Delegation(id, status, type, creation, modification, sender, delegate, delegator);
  }

  toJson(resource: Delegation) {

    return `{
      "id": "${resource.id}",
      "status": "${resource.status}",
      "type": "${resource.type}",
      "delegate": "${resource.delegate.gin}",
      "delegator": "${resource.delegator.gin}"
    }`;

  }


}
