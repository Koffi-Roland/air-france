import { PreferenceData } from './../common/preference-data';
import { Serializer } from './serializer';
import { Preference } from '../resources/preference';
import { Converter } from './converter';
import { SignatureType } from '../common/signatures/signature-type';

export class PreferenceSerializer extends Serializer {

  fromJsonToObject(json: any): Preference {
    const id = (json.preferenceId as number).toString();
    const type = json.type;
    const creation = Converter.convertToSignature(SignatureType.Creation, json);
    const modification = Converter.convertToSignature(SignatureType.Modification, json);
    const preferenceData = Converter.convertToPreferenceData(json);
    return new Preference(id, type, creation, modification, preferenceData);
  }

  toJson(resource: Preference) {
    const jsonPreference = `{
      "preferenceId": "${resource.id}",
      "type": "${resource.type}",
      "preferenceData": [${this.preferenceDataToJSON(resource)}]
    }`;
    return jsonPreference;
  }

  private preferenceDataToJSON(resource: Preference): any[] {
    return resource.preferenceData.map((value: PreferenceData) => {
      const jsonPrefData = `{
        "preferenceDataId": "${value.id}",
        "key": "${value.key}",
        "value": ${(value.value) ? JSON.stringify(value.value) : "\"\""}
      }`;
      return jsonPrefData;
    });
  }

}
