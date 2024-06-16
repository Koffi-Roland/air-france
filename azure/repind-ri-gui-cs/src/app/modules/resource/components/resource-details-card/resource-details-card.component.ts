import { Component, Input, OnChanges, SimpleChanges, OnDestroy, ViewChild } from '@angular/core';
import { ResourceDetailsCardConfiguration } from './config/models/resrouce-details-card-configuration';
import { ResourceDetailsCardConfigurationLoader } from './config/resource.details.card.configuration.loader';
import { ResourceDetailsCardHeader } from './config/models/resource-details-card-header';
import { ResourceDetailsCardContent } from './config/models/resource-details-card-content';
import { MatDialog } from '@angular/material/dialog';
import { Subscription } from 'rxjs';
import { ResourceDetailsKeyVal } from './config/models/resource-details-key-val';
import { Individual } from '../../../../shared/models/individual/individual';
import { ErrorService } from '../../../../core/services/error.service';
import { ResourceUtilityService } from '../../../../core/services/resources/resource-utility.service';
import { ResourceService } from '../../../../core/services/resources/resource.service';
import { RightsService } from '../../../../core/services/rights/rights.service';
import { DialogService } from '../../../../core/services/dialogs/dialog.service';
import { ResourceType } from '../../../../shared/models/resources/resource-type';
import { Resource } from '../../../../shared/models/resources/resource';
import { CommunicationPreference } from '../../../../shared/models/resources/communication-preference';
import { ExternalIdentifier } from '../../../../shared/models/resources/external-identifier';
import { IndividualService } from '../../../../core/services/individual/individual.service';
import { MarketLanguagesComponent } from '../../../../shared/components/dialogs/resources/market-languages/market-languages.component';
// tslint:disable-next-line: max-line-length
import { ExternalIdentifierDataComponent } from '../../../../shared/components/dialogs/resources/external-identifier-data/external-identifier-data.component';
import { IndividualTypeRestrictedData } from '../../../../shared/models/individual/individual-type-restricted-data';

@Component({
  selector: 'app-resource-details-card',
  templateUrl: './resource-details-card.component.html',
  styleUrls: ['./resource-details-card.component.scss']
})
export class ResourceDetailsCardComponent implements OnChanges, OnDestroy {

  @ViewChild('scrollableDiv') scrollableDiv: HTMLElement;

  @Input() resource: any;
  @Input() resourceType: ResourceType;

  public timer: any;

  private configuration: ResourceDetailsCardConfiguration;
  public header: ResourceDetailsCardHeader;
  public content: ResourceDetailsCardContent;
  public btnBackgroundColor: string;
  private resourceService: ResourceService<Resource>;

  private deleteSubscription: Subscription;
  private individual: Individual;

  public isBtnUpdateVisible = false;
  public isBtnDeleteVisible = false;


  constructor(public dialog: MatDialog, private _errorService: ErrorService,
    private _resourceUtilityService: ResourceUtilityService, private dialogService: DialogService,
    private individualService: IndividualService, private _rightService: RightsService) {
  }

  ngOnChanges(changes: SimpleChanges): void {
    this.individual = this.individualService.getIndividual();
    this.resource = changes.resource.currentValue;
    this.configuration = ResourceDetailsCardConfigurationLoader.loadCardConfiguration(this.resourceType, this.resource);
    this.configuration.content.data.map((elt: ResourceDetailsKeyVal) => elt.keyCopy = elt.key);
    this.header = this.configuration.header;
    this.content = this.configuration.content;
    this.btnBackgroundColor = this.header.buttonColor;
    this.resourceService = this._resourceUtilityService.getResourceService(this.resourceType);
    this.checkIfCanDelete();
    this.checkIfCanUpdate();
  }

  ngOnDestroy(): void {
    if (this.deleteSubscription) { this.deleteSubscription.unsubscribe(); }
    clearTimeout(this.timer);
  }

  openDialog(): void {
    switch (this.resourceType) {
      case ResourceType.CommunicationPreference:
        this.openMarketLanguagesDialog();
        break;
      case ResourceType.ExternalIdentifier:
        this.openExternalIdDataDialog();
        break;
      default:
        break;
    }
  }

  openEditDialog(): void {
    this.dialogService.openUpdateDialog(this.resourceType, this.resource, '75%', '90%');
  }

  deleteResource(): void {
    this.dialogService.openDeleteDialog(this.resourceType, this.resource, 'auto', 'auto');
  }

  /** Details dialogs */

  private openMarketLanguagesDialog(): void {
    if (this.resourceType !== ResourceType.CommunicationPreference) { return; }
    if ((this.resource as CommunicationPreference).marketLanguages.length === 0) {
      this._errorService.displayDefaultError('NO-MARKET-LANGUAGES');
      return;
    }
    this.dialog.open(MarketLanguagesComponent, {
      panelClass: 'custom-dialog-container',
      data: { market: (this.resource as CommunicationPreference).marketLanguages }
    });
  }

  private openExternalIdDataDialog(): void {
    if (this.resourceType !== ResourceType.ExternalIdentifier) { return; }
    if ((this.resource as ExternalIdentifier).functionalData.length === 0) {
      this._errorService.displayDefaultError('NO-FUNCTIONAL-DATA-MSG');
      return;
    }
    this.dialog.open(ExternalIdentifierDataComponent, {
      panelClass: 'custom-dialog-container',
      data: { extIdData: (this.resource as ExternalIdentifier).functionalData }
    });
  }

  /** Scroll div when text length is too long */
  public scrollDiv(elementToScroll: HTMLElement, depl: any) {
    elementToScroll.style.textOverflow = 'clip';
    elementToScroll.scrollLeft -= depl;
    this.timer = setTimeout(() => {
      this.scrollDiv(elementToScroll, depl);
    }, 30);
  }

  public stopTimer(elementToScroll: HTMLElement) {
    clearTimeout(this.timer);
    elementToScroll.scrollTo(0, 0);
    elementToScroll.style.textOverflow = 'ellipsis';
  }

  /**
   * Check if the update button has to be displayed by checking user rights
   */
  checkIfCanUpdate(): any {

    this._rightService.canUpdateResource(this.resourceType).then((res: boolean) => {

      this.isBtnUpdateVisible = res;
      if (this.isBtnUpdateVisible) {
        // For the travelers, it is not possible to update any kind of data
        if (IndividualTypeRestrictedData.haveRestriction(this.individual.type)) {
          this.isBtnUpdateVisible = false;

          if (this.resourceType === ResourceType.CommunicationPreference) {
            this.isBtnUpdateVisible = IndividualTypeRestrictedData.restrictComPrefUpdates(this.individual.type);
          }
        }
      }
    });
  }

  /**
   * Check if the delete button has to be displayed by checking user rights
   */
  private checkIfCanDelete(): any {
    this._rightService.canDeleteResource(this.resourceType).then((res: boolean) => {
      this.isBtnDeleteVisible = res;
      if (this.isBtnDeleteVisible) {
        // For the travelers and prospects, the button to delete should never be visible
        if (IndividualTypeRestrictedData.haveRestriction(this.individual.type)) {
          this.isBtnDeleteVisible = false;
        }
      }
    });
  }

}
