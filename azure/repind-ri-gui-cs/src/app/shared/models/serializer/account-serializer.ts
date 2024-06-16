import {Serializer} from './serializer';
import {SignatureType} from '../common/signatures/signature-type';
import {Signature} from '../common/signatures/signature';
import {Account} from '../resources/account';

export class AccountSerializer extends Serializer {


  fromJsonToObject(json: any): Account {
    const identifier = json.emailFbIdentifier as string;
    const type = identifier.includes('@') ? 'E' : 'F';
    const email = json.emailIdentifier;
    const fb = json.fbIdentifier;
    return new Account(identifier, type, identifier, email, fb);
  }

  toJson(resource: Account): any {

  }

}
