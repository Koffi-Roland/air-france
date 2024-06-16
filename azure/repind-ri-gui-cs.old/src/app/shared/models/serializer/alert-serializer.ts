import { Serializer } from './serializer';
import { Alert } from '../resources/alert';
import { Converter } from './converter';
import { SignatureType } from '../common/signatures/signature-type';

export class AlertSerializer extends Serializer {

  fromJsonToObject(json: any): Alert {
    const id = (json.alertId as number).toString();
    const creation = Converter.convertToSignature(SignatureType.Creation, json);
    const modification = Converter.convertToSignature(SignatureType.Modification, json);
    const type = json.type;
    const optin = json.optIn;
    const alertData = Converter.convertToAlertData(json);
    return new Alert(id, creation, modification, type, optin, alertData);
  }

  toJson(resource: Alert) {
    return `
    {
      "alertId" : ${resource.id},
      "type" : "${resource.type}",
      "optIn" : "${resource.optin}"
    }
    `
  }

}
