import { CrudService } from './../crud/crud.service';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { Injectable } from '@angular/core';
import { ErrorService } from '../error.service';
import { ResourceType } from '../../../shared/models/resources/resource-type';
import { ResourceUtilityService } from '../resources/resource-utility.service';
import { EmailUpdateDialogComponent } from '../../../shared/components/dialogs/resources/email-update-dialog/email-update-dialog.component';
// tslint:disable-next-line: max-line-length
import { TelecomUpdateDialogComponent } from '../../../shared/components/dialogs/resources/telecom-update-dialog/telecom-update-dialog.component';
// tslint:disable-next-line: max-line-length
import { PreferenceUpdateDialogComponent } from '../../../shared/components/dialogs/resources/preference-update-dialog/preference-update-dialog.component';
// tslint:disable-next-line: max-line-length
import { AddressUpdateDialogComponent } from '../../../shared/components/dialogs/resources/address-update-dialog/address-update-dialog.component';
// tslint:disable-next-line: max-line-length
import { CommPrefUpdateDialogComponent } from '../../../shared/components/dialogs/resources/comm-pref-update-dialog/comm-pref-update-dialog.component';
// tslint:disable-next-line: max-line-length
import { DeleteConfirmationDialogComponent } from '../../../shared/components/dialogs/resources/delete-confirmation-dialog/delete-confirmation-dialog.component';
import { DelegationUpdateDialogComponent } from '../../../shared/components/dialogs/resources/delegation-update-dialog/delegation-update-dialog.component';
import { AlertUpdateDialogComponent } from '../../../shared/components/dialogs/resources/alert-update-dialog/alert-update-dialog.component';
import { ConsentUpdateDialogComponent } from '../../../shared/components/dialogs/resources/consent-update-dialog/consent-update-dialog.component';


@Injectable({
  providedIn: 'root'
})
export class DialogService {

  constructor(private dialog: MatDialog, private errorService: ErrorService, private resourceUtilityService: ResourceUtilityService,
    private crudService: CrudService) { }

  public openDialogComponent(component: any, data?: any, width?: string, height?: string): void {
    const config = this.getMatDialogConfig(height, width);
    config.data = data;
    this.dialog.open(component, config);
  }

  public openCreateDialog(type: ResourceType, height?: string, width?: string): void {
    const config = this.getMatDialogConfig(height, width);
    config.data = this.getCreateDialogConfiguration();
    switch (type) {
      case ResourceType.Email:
        this.dialog.open(EmailUpdateDialogComponent, config);
        break;
      case ResourceType.Telecom:
        this.dialog.open(TelecomUpdateDialogComponent, config);
        break;
      case ResourceType.Preference:
        this.dialog.open(PreferenceUpdateDialogComponent, config);
        break;
      case ResourceType.Address:
        this.dialog.open(AddressUpdateDialogComponent, config);
        break;
    }
  }

  public openUpdateDialog(type: ResourceType, resource: any, height?: string, width?: string): void {
    const config = this.getMatDialogConfig(height, width);
    config.data = this.getUpdateDialogConfiguration(resource);
    switch (type) {
      case ResourceType.Telecom:
        this.dialog.open(TelecomUpdateDialogComponent, config);
        break;
      case ResourceType.Email:
        this.dialog.open(EmailUpdateDialogComponent, config);
        break;
      case ResourceType.Address:
        this.dialog.open(AddressUpdateDialogComponent, config);
        break;
      case ResourceType.CommunicationPreference:
        this.dialog.open(CommPrefUpdateDialogComponent, config);
        break;
      case ResourceType.Preference:
        this.dialog.open(PreferenceUpdateDialogComponent, config);
        break;
      case ResourceType.Delegation:
        this.dialog.open(DelegationUpdateDialogComponent, config);
        break;
      case ResourceType.Alert:
        this.dialog.open(AlertUpdateDialogComponent, config);
        break;
      case ResourceType.Consent:
        this.dialog.open(ConsentUpdateDialogComponent, config);
        break;
    }
  }

  public openDeleteDialog(type: ResourceType, resource: any, height?: string, width?: string): void {
    const config = this.getMatDialogConfig(height, width);
    config.disableClose = true;
    const ref = this.dialog.open(DeleteConfirmationDialogComponent, config);
    ref.afterClosed().toPromise().then((data: boolean) => {
      if (data) {
        const resourceService = this.resourceUtilityService.getResourceService(type);
        resourceService.delete(resource);
      } else {
        this.errorService.displayDefaultError('DATA-NOT-DELETED');
        return;
      }
    });
  }

  public isCreatableResource(type: ResourceType): boolean {
    return (type === ResourceType.Email || type === ResourceType.Telecom || type === ResourceType.Preference
      || type === ResourceType.Address);
  }

  public isModifiableResource(type: ResourceType): boolean {
    return (type === ResourceType.Email || type === ResourceType.Telecom || type === ResourceType.Preference
      || type === ResourceType.CommunicationPreference || type === ResourceType.Address);
  }

  private getMatDialogConfig(height?: string, width?: string): MatDialogConfig<any> {
    const config: MatDialogConfig<any> = new MatDialogConfig();
    config.panelClass = 'custom-dialog-container';
    config.autoFocus = false;
    config.height = (height) ? height : '90%';
    config.width = (width) ? width : '75%';
    return config;
  }

  private getCreateDialogConfiguration(): any {
    const configuration = {
      isUpdate: false,
      title: 'CREATE',
      validationBtnLabel: 'CONFIRM-CREATE'
    };
    return configuration;
  }

  private getUpdateDialogConfiguration(resource: any): any {
    const configuration = {
      isUpdate: true,
      title: 'UPDATE',
      resource: resource,
      validationBtnLabel: 'CONFIRM-UPDATE'
    };
    return configuration;
  }

}
