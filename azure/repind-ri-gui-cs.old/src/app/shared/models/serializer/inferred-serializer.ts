import { Serializer } from './serializer';
import { Converter } from './converter';
import { SignatureType } from '../common/signatures/signature-type';
import { Inferred } from '../resources/inferred';
import { InferredData } from '../common/inferredData';
import { Signature } from '../common/signatures/signature';

export class InferredSerializer extends Serializer {

  fromJsonToObject(json: any): Inferred {
    const id = (json.inferredId as number).toString();
    const type = json.type;
    const status = json.status;
    const creation = Converter.convertToSignature(SignatureType.Creation, json);
    const modification = Converter.convertToSignature(SignatureType.Modification, json);
    const inferredData = this.convertToInferredData(json);
    return new Inferred(id, type, status, creation, modification, inferredData);
  }

  toJson(resource: Inferred) {
    throw new Error('Method not implemented.');
  }

  convertToInferredData(json: any): InferredData[] {
    const inferredData: InferredData[] = [];
    if (json.inferredData) {
      json.inferredData.forEach((y: any) => {
        const id: number = y.inferredDataId;
        const key: string = y.key;
        const value: string = y.value;
        const creationSignature: Signature = Converter.convertToSignature(SignatureType.Modification, json);
        const modificationSignature: Signature = Converter.convertToSignature(SignatureType.Creation, json);
        inferredData.push(new InferredData(id, key, value, creationSignature, modificationSignature));
      });
    }
    return inferredData;
  }

}
