import { Serializer } from './serializer';
import { Email } from '../resources/email';
import { Type } from '../common/type';
import { Status } from '../common/status';
import { SignatureType } from '../common/signatures/signature-type';
import { Signature } from '../common/signatures/signature';
import { Converter } from './converter';
import { AuthorizationMailingUtil, AuthorizationMailing } from '../common/authorization-mailing';

export class EmailSerializer extends Serializer {

  fromJsonToObject(json: any): Email {
    const id: string = json.identifiant;
    const type: Type = Converter.convertToType(json.type);
    const status: Status = Converter.convertToStatus(json.status);
    const emailAddress: string = json.email;
    const mailingAuthorization: AuthorizationMailing =
      AuthorizationMailingUtil.convertToAuthorizationMailing(json.authorizationMailing);
    const signatureCreation: Signature = Converter.convertToSignature(SignatureType.Creation, json);
    const signatureModification: Signature = Converter.convertToSignature(SignatureType.Modification, json);
    const version: number = json.version;
    return new Email(id, type, status, emailAddress, mailingAuthorization, signatureCreation, signatureModification, version);
  }

  toJson(resource: Email): any {
    if (!resource.signatureCreation || !resource.signatureModification) {
      const jsonEmail = `{
        "type": "${resource.type}",
        "status": "${resource.status}",
        "email": "${resource.emailAddress}",
        "authorizationMailing": "${resource.mailingAuthorization}"
      }`;
      return jsonEmail;
    } else {
      const jsonEmail = `{
        "identifiant": "${resource.id}",
        "type": "${resource.type}",
        "status": "${resource.status}",
        "email": "${resource.emailAddress}",
        "authorizationMailing": "${resource.mailingAuthorization}",
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
      return jsonEmail;
    }
  }

}
