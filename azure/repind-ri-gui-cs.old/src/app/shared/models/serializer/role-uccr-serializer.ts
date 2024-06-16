import { Serializer } from './serializer';
import { RoleUccr } from '../resources/role-uccr';
import { Converter } from './converter';
import { SignatureType } from '../common/signatures/signature-type';

export class RoleUccrSerializer extends Serializer {

  fromJsonToObject(json: any): RoleUccr {
    const id = (json.uccrID as number).toString();
    const ceid = json.ceID;
    const type = json.type;
    const status = json.etat;
    const startValidity = json.debutValidite;
    const endValidity = json.finValidite;
    const creation = Converter.convertToSignature(SignatureType.Creation, json);
    const modification = Converter.convertToSignature(SignatureType.Modification, json);
    return new RoleUccr(id, ceid, type, status, startValidity, endValidity, creation, modification);
  }

  toJson(resource: RoleUccr) {
    throw new Error('Method not implemented.');
  }

}
