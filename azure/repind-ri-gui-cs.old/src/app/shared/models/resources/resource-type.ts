export enum ResourceType {

  Address,
  Email,
  Telecom,
  Contract,
  CommunicationPreference,
  ExternalIdentifier,
  Preference,
  Delegation,
  Alert,
  UCCRRole,
  Inferred,
  Individual,
  Handicap,
  Consent

}

export class ResourceTypeUtil {

  public static labelOf(type: ResourceType): string {
    switch (type) {
      case ResourceType.Address: return 'ADDRESSES';
      case ResourceType.Alert: return 'ALERTS';
      case ResourceType.CommunicationPreference: return 'COMM-PREF';
      case ResourceType.Contract: return 'CONTRACTS';
      case ResourceType.Delegation: return 'DELEGATIONS';
      case ResourceType.Email: return 'EMAILS';
      case ResourceType.ExternalIdentifier: return 'EXTERNAL-IDENTIFIERS';
      case ResourceType.Preference: return 'PREFERENCES';
      case ResourceType.Telecom: return 'TELECOMS';
      case ResourceType.UCCRRole: return 'UCCR-ROLES';
      case ResourceType.Inferred: return 'INFERRED';
      case ResourceType.Handicap: return 'HANDICAP';
      case ResourceType.Consent: return 'CONSENT';
    }
  }

}
