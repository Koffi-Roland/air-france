import { Serializer } from './serializer';
import { Converter } from './converter';
import { SignatureType } from '../common/signatures/signature-type';
import { Handicap } from '../resources/handicap';
import { HandicapData } from '../common/handicapData';
import { Signature } from '../common/signatures/signature';

export class HandicapSerializer extends Serializer {

  fromJsonToObject(json: any): Handicap {
    const id = (json.handicapId as number).toString();
    const type = json.type;
    const code = json.code;
    const creation = Converter.convertToSignature(SignatureType.Creation, json);
    const modification = Converter.convertToSignature(SignatureType.Modification, json);
    const handicapData = this.convertToHandicapData(json);
    return new Handicap(id, type, code, creation, modification, handicapData);
  }

  toJson(resource: Handicap) {
    throw new Error('Method not implemented.');
  }

  convertToHandicapData(json: any): HandicapData[] {
    const handicapData: HandicapData[] = [];
    if (json.handicapData) {
      json.handicapData.forEach((y: any) => {
        const id: number = y.handicapDataId;
        const key: string = y.key;
        const value: string = y.value;
        const creationSignature: Signature = Converter.convertToSignature(SignatureType.Modification, json);
        const modificationSignature: Signature = Converter.convertToSignature(SignatureType.Creation, json);
        handicapData.push(new HandicapData(id, key, value, creationSignature, modificationSignature));
      });
    }
    return handicapData;
  }

}
