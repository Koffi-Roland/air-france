import { Serializer } from './serializer';
import { Telecom } from '../resources/telecom';
import { Type } from '../common/type';
import { Status } from '../common/status';
import { TerminalType } from '../common/terminal-type';
import { Signature } from '../common/signatures/signature';
import { SignatureType } from '../common/signatures/signature-type';
import { Converter } from './converter';

export class TelecomSerializer extends Serializer {

  fromJsonToObject(json: any): Telecom {
    const id: string = json.identifiant;
    const type: Type = Converter.convertToType(json.type);
    const status: Status = Converter.convertToStatus(json.status);
    const phoneNumber: string = (json.phoneNumber) ? json.phoneNumber : '';
    const phoneNumberNotNormalized: string = json.phoneNumberNotNormalized;
    const terminalType: TerminalType = Converter.convertToTerminalType(
      json.terminal
    );
    const countryCode: string = (json.countryCode) ? json.countryCode : '';
    const regionCode: string = json.regionCode;
    const signatureCreation: Signature = Converter.convertToSignature(
      SignatureType.Creation,
      json
    );
    const signatureModification: Signature = Converter.convertToSignature(
      SignatureType.Modification,
      json
    );
    const version: number = json.version;
    return new Telecom(
      id,
      type,
      status,
      terminalType,
      countryCode,
      regionCode,
      phoneNumber,
      phoneNumberNotNormalized,
      signatureCreation,
      signatureModification,
      version
    );
  }

  toJson(resource: Telecom): any {
    if (!resource.signatureCreation || !resource.signatureModification) {
      const jsonTelecom = `{
        "type": "${resource.type}",
        "status": "${resource.status}",
        "phoneNumber": "${resource.phoneNumber}",
        "terminal": "${resource.terminalType}",
        "countryCode": "${resource.countryCode}"
      }`;
      return jsonTelecom;
    } else {
      const jsonTelecom = `{
        "identifiant": "${resource.id}",
        "type": "${resource.type}",
        "status": "${resource.status}",
        "phoneNumber": "${resource.phoneNumber}",
        "terminal": "${resource.terminalType}",
        "countryCode": "${resource.countryCode}",
        "signature": {
          "modificationSignature": "${resource.signatureModification.signature}",
          "modificationSite": "${resource.signatureModification.site}",
          "modificationDate": "${resource.signatureModification.date}",
          "creationSignature": "${resource.signatureCreation.signature}",
          "creationSite": "${resource.signatureCreation.site}",
          "creationDate": "${resource.signatureCreation.date}"
        },
        "version": ${resource.version}
      }`;
      return jsonTelecom;
    }
  }
}
