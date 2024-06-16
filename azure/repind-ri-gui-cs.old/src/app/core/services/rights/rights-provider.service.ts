
import { Injectable } from '@angular/core';
import { ResourceType } from '../../../shared/models/resources/resource-type';
import { Right } from '../../../shared/models/common/rights/right';
import { OperationType } from '../../../shared/models/common/rights/operation-type.enum';

@Injectable({
  providedIn: 'root'
})
export class RightsProviderService {

  constructor() { }

  /**
   * Given a `ResourceType`, this method returns an array of rights for the available operations for the resource.
   * All the rights are based on the access keys in the backend file called `accessKey.json` (with all the roles &
   * permissions related to Habile).
   * @param type
   */
  public provideRightsFor(type: ResourceType): Right[] {
    switch (type) {
      case ResourceType.Address:
        return this.getAddressRights();
      case ResourceType.Alert:
        return this.getAlertRights();
      case ResourceType.CommunicationPreference:
        return this.getCommunicationPreferenceRights();
      case ResourceType.Contract:
        return this.getContractRights();
      case ResourceType.Delegation:
        return this.getDelegationRights();
      case ResourceType.Email:
        return this.getEmailRights();
      case ResourceType.ExternalIdentifier:
        return this.getExternalIdRights();
      case ResourceType.Preference:
        return this.getPreferenceRights();
      case ResourceType.Telecom:
        return this.getTelecomRights();
      case ResourceType.UCCRRole:
        return this.getUCCRRoleRights();
      case ResourceType.Inferred:
        return this.getInferredRights();
      case ResourceType.Handicap:
        return this.getHandicapRights();
      case ResourceType.Consent:
        return this.getConsentRights();
    }
  }

  /**
   * The method returns the rights for the resource: postal address
   */
  private getAddressRights(): Right[] {
    const rights: Right[] = [];
    rights.push(new Right(OperationType.READ, ['ROLE_READ', 'ROLE_READ_ADDR']));
    rights.push(new Right(OperationType.UPDATE, ['ROLE_UPDATE', 'ROLE_UPDATE_ADDR']));
    rights.push(new Right(OperationType.DELETE, ['ROLE_DELETE', 'ROLE_DELETE_ADDR']));
    rights.push(new Right(OperationType.CREATE, ['ROLE_CREATE', 'ROLE_CREATE_ADDR']));
    return rights;
  }

  /**
  * The method returns the rights for the resource: alert
  */
  private getAlertRights(): Right[] {
    const rights: Right[] = [];
    rights.push(new Right(OperationType.READ, ['ROLE_READ', 'ROLE_READ_ALERTS']));
    rights.push(new Right(OperationType.UPDATE, ['ROLE_UPDATE', 'ROLE_UPDATE_ALERTS']));
    return rights;
  }

  /**
  * The method returns the rights for the resource: communication preference
  */
  private getCommunicationPreferenceRights(): Right[] {
    const rights: Right[] = [];
    rights.push(new Right(OperationType.READ, ['ROLE_READ', 'ROLE_READ_COMMPREF']));
    rights.push(new Right(OperationType.UPDATE, ['ROLE_UPDATE', 'ROLE_UPDATE_COMMPREF']));
    return rights;
  }

  /**
  * The method returns the rights for the resource: contract
  */
  private getContractRights(): Right[] {
    const rights: Right[] = [];
    rights.push(new Right(OperationType.READ, ['ROLE_READ', 'ROLE_READ_CONTRACTS']));
    return rights;
  }

  /**
  * The method returns the rights for the resource: delegation
  */
  private getDelegationRights(): Right[] {
    const rights: Right[] = [];
    rights.push(new Right(OperationType.READ, ['ROLE_READ', 'ROLE_READ_DELEG']));
    rights.push(new Right(OperationType.UPDATE, ['ROLE_UPDATE', 'ROLE_UPDATE_DELEG']));
    rights.push(new Right(OperationType.DELETE, ['ROLE_DELETE', 'ROLE_DELETE_DELEG']));
    return rights;
  }

  /**
  * The method returns the rights for the resource: email
  */
  private getEmailRights(): Right[] {
    const rights: Right[] = [];
    rights.push(new Right(OperationType.READ, ['ROLE_READ', 'ROLE_READ_EMAILS']));
    rights.push(new Right(OperationType.UPDATE, ['ROLE_UPDATE', 'ROLE_UPDATE_EMAILS']));
    rights.push(new Right(OperationType.DELETE, ['ROLE_DELETE', 'ROLE_DELETE_EMAILS']));
    rights.push(new Right(OperationType.CREATE, ['ROLE_CREATE', 'ROLE_CREATE_EMAILS']));
    return rights;
  }

  /**
  * The method returns the rights for the resource: external identifier
  */
  private getExternalIdRights(): Right[] {
    const rights: Right[] = [];
    rights.push(new Right(OperationType.READ, ['ROLE_READ', 'ROLE_READ_EXTID']));
    return rights;
  }

  /**
  * The method returns the rights for the resource: role GP
  */
  private getGPRoleRights(): Right[] {
    const rights: Right[] = [];
    rights.push(new Right(OperationType.READ, ['ROLE_READ', 'ROLE_READ_GPROLES']));
    rights.push(new Right(OperationType.UPDATE, ['ROLE_UPDATE', 'ROLE_UPDATE_GP']));
    rights.push(new Right(OperationType.DELETE, ['ROLE_DELETE', 'ROLE_DELETE_GP']));
    return rights;
  }

  /**
  * The method returns the rights for the resource: preference
  */
  private getPreferenceRights(): Right[] {
    const rights: Right[] = [];
    rights.push(new Right(OperationType.READ, ['ROLE_READ', 'ROLE_READ_PREF']));
    rights.push(new Right(OperationType.UPDATE, ['ROLE_UPDATE', 'ROLE_UPDATE_PREF']));
    rights.push(new Right(OperationType.DELETE, ['ROLE_DELETE', 'ROLE_DELETE_PREF']));
    rights.push(new Right(OperationType.CREATE, ['ROLE_CREATE', 'ROLE_CREATE_PREF']));
    return rights;
  }

  /**
  * The method returns the rights for the resource: telecom
  */
  private getTelecomRights(): Right[] {
    const rights: Right[] = [];
    rights.push(new Right(OperationType.READ, ['ROLE_READ', 'ROLE_READ_TELECOMS']));
    rights.push(new Right(OperationType.UPDATE, ['ROLE_UPDATE', 'ROLE_UPDATE_TELECOMS']));
    rights.push(new Right(OperationType.DELETE, ['ROLE_DELETE', 'ROLE_DELETE_TELECOMS']));
    rights.push(new Right(OperationType.CREATE, ['ROLE_CREATE', 'ROLE_CREATE_TELECOMS']));
    return rights;
  }

  /**
  * The method returns the rights for the resource: role UCCR
  */
  private getUCCRRoleRights(): Right[] {
    const rights: Right[] = [];
    rights.push(new Right(OperationType.READ, ['ROLE_READ', 'ROLE_READ_UCCROLES']));
    return rights;
  }

  /**
* The method returns the rights for the resource: infered
*/
  private getInferredRights(): Right[] {
    const rights: Right[] = [];
    rights.push(new Right(OperationType.READ, ['ROLE_READ', 'ROLE_READ_INFERRED']));
    return rights;
  }

  /**
* The method returns the rights for the resource: alert
*/
  private getHandicapRights(): Right[] {
    const rights: Right[] = [];
    rights.push(new Right(OperationType.READ, ['ROLE_READ', 'ROLE_READ_HANDICAP']));
    return rights;
  }


  private getConsentRights(): Right[] {
    const rights: Right[] = [];
    rights.push(new Right(OperationType.READ, ['ROLE_READ', 'ROLE_READ_CONSENT']));
    rights.push(new Right(OperationType.UPDATE, ['ROLE_UPDATE', 'ROLE_UPDATE_CONSENT']));
    return rights;
  }

}
