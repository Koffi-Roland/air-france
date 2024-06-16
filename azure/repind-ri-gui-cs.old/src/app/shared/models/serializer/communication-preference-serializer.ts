import { MarketLanguage } from './../common/market-language';
import { Serializer } from './serializer';
import { CommunicationPreference } from '../resources/communication-preference';
import { Converter } from './converter';
import { SignatureType } from '../common/signatures/signature-type';

export class CommunicationPreferenceSerializer extends Serializer {

  fromJsonToObject(json: any): CommunicationPreference {
    const id = (json.comPrefId as number).toString(); // Convert the commPrefID in string
    const channel = json.channel;
    const domain = json.domain;
    const groupType = json.comGroupType;
    const type = json.comType;
    const subscribe = json.subscribe;
    const consentDate = json.dateOptin;
    const creation = Converter.convertToSignature(SignatureType.Creation, json);
    const modification = Converter.convertToSignature(SignatureType.Modification, json);
    const marketLanguages = Converter.convertToMarketLanguages(json, channel);
    return new CommunicationPreference(id, channel, domain, groupType, type, subscribe, consentDate, creation,
      modification, marketLanguages);
  }

  toJson(resource: CommunicationPreference) {
    const jsonCommPref = `{
      "comPrefId": "${resource.id}",
      "domain": "${resource.domain}",
      "subscribe": "${resource.subscribe}",
      "comGroupType": "${resource.groupType}",
      "comType": "${resource.type}",
      "dateOptin": "${resource.dateOfConsent}",
      "channel": "${resource.channel}",
      "marketLanguage": [${this.marketLanguagesToJSON(resource)}]
    }`;
    return jsonCommPref;
  }

  private marketLanguagesToJSON(resource: CommunicationPreference): any[] {
    return resource.marketLanguages.map((value: MarketLanguage) => {
      const jsonPrefData = `{
        "market": "${value.market}",
        "language": "${value.language}",
        "optIn": "${value.optin}",
        "dateOfConsent": "${value.dateOfConsent}"
      }`;
      return jsonPrefData;
    });
  }

}
