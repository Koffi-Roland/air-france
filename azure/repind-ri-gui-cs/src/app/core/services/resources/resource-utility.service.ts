import {Injectable, Injector} from '@angular/core';
import {Observable, Subject} from 'rxjs';
import {ResourceService} from './resource.service';
import {AddressService} from './address.service';
import {AlertService} from './alert.service';
import {CommunicationPreferenceService} from './communication-preference.service';
import {ContractService} from './contract.service';
import {DelegationService} from './delegation.service';
import {EmailService} from './email.service';
import {ExternalIdentifierService} from './external-identifier.service';
import {PreferencesService} from './preferences.service';
import {TelecomService} from './telecom.service';
import {RoleUccrService} from './role-uccr.service';
import {InferredsService} from './inferreds.service';
import {HandicapsService} from './handicaps.service';
import {Resource} from '../../../shared/models/resources/resource';
import {ResourceType} from '../../../shared/models/resources/resource-type';
import {ConsentsService} from './consents.service';
import {AccountService} from './account.service';

@Injectable({
  providedIn: 'root'
})
export class ResourceUtilityService {

  private selectedResource: Resource;

  private resourceType: ResourceType;

  private selectedResourceSubject = new Subject<Resource>();

  constructor(private injector: Injector) { }

  public setResourceType(type: ResourceType): void {
    this.resourceType = type;
  }

  public getResourceType(): ResourceType {
    return this.resourceType;
  }

  public selectResource(res: Resource): void {
    this.selectedResource = res;
    this.selectedResourceSubject.next(res);
  }

  public getSelectedResource(): Observable<Resource> {
    return this.selectedResourceSubject.asObservable();
  }

  public getCurrentlySelectedResource(): Resource {
    return this.selectedResource;
  }

  public selectUpdatedResourceById(id: string, arr: Resource[]): void {
    const filteredArr = arr.filter((r: Resource) => r.id === id);
    this.selectedResource = filteredArr[0];
    this.selectResource(this.selectedResource);
  }

  /**
   * Method to get the appropriate resource service depending on the type of resource given in parameter.
   * @param type `ResourceType` is the enum used to identify what kind of resource is used.
   */
  public getResourceService(type: ResourceType): ResourceService<any> {
    switch (type) {
      case ResourceType.Address:
        return this.injector.get(AddressService);
      case ResourceType.Alert:
        return this.injector.get(AlertService);
      case ResourceType.CommunicationPreference:
        return this.injector.get(CommunicationPreferenceService);
      case ResourceType.Contract:
        return this.injector.get(ContractService);
      case ResourceType.Delegation:
        return this.injector.get(DelegationService);
      case ResourceType.Email:
        return this.injector.get(EmailService);
      case ResourceType.ExternalIdentifier:
        return this.injector.get(ExternalIdentifierService);
      case ResourceType.Preference:
        return this.injector.get(PreferencesService);
      case ResourceType.Telecom:
        return this.injector.get(TelecomService);
      case ResourceType.UCCRRole:
        return this.injector.get(RoleUccrService);
      case ResourceType.Inferred:
        return this.injector.get(InferredsService);
      case ResourceType.Handicap:
        return this.injector.get(HandicapsService);
      case ResourceType.Consent:
        return this.injector.get(ConsentsService);
      case ResourceType.Account:
        return this.injector.get(AccountService);
    }
    return null;
  }

  /**
   * Get the resource page title depending on the resource type (`type`) given in parameter.
   * @param type the resource type
   */
  public getResourcePageTitle(type: ResourceType): string {
    switch (type) {
      case ResourceType.Address:
        return 'ADDRESSES';
      case ResourceType.Alert:
        return 'ALERTS';
      case ResourceType.CommunicationPreference:
        return 'COMM-PREF';
      case ResourceType.Contract:
        return 'CONTRACTS';
      case ResourceType.Delegation:
        return 'DELEGATIONS';
      case ResourceType.Email:
        return 'EMAILS';
      case ResourceType.ExternalIdentifier:
        return 'EXTERNAL-IDENTIFIERS';
      case ResourceType.Preference:
        return 'PREFERENCES';
      case ResourceType.Telecom:
        return 'TELECOMS';
      case ResourceType.UCCRRole:
        return 'UCCR-ROLES';
      case ResourceType.Handicap:
        return 'HANDICAPS';
      case ResourceType.Consent:
        return 'CONSENTS';
      case ResourceType.Account:
        return 'ACCOUNT-IDENTIFIER';
    }
  }

}
