import { IndividualService } from './../../../../core/services/individual/individual.service';
import { Component, OnInit, OnDestroy, ViewChild, ElementRef } from '@angular/core';
import { Individual } from '../../../../shared/models/individual/individual';
import { Subscription } from 'rxjs';
import { ActivatedRoute, Router } from '@angular/router';
import { ResourceUtilityService } from '../../../../core/services/resources/resource-utility.service';
import { SearchService } from '../../../../core/services/search/search.service';
import { ErrorService } from '../../../../core/services/error.service';
import { MatDialog } from '@angular/material/dialog';
import { ResourceService } from '../../../../core/services/resources/resource.service';
import { RightsService } from '../../../../core/services/rights/rights.service';
import { DialogService } from '../../../../core/services/dialogs/dialog.service';
import { ResourceType } from '../../../../shared/models/resources/resource-type';
import { Resource } from '../../../../shared/models/resources/resource';
import { CrudService } from '../../../../core/services/crud/crud.service';
import { CRUDAction } from '../../../../shared/models/crud/CRUDAction';
import { CRUDOperation } from '../../../../shared/models/crud/CRUDOperations.enum';
import { IndividualTypeRestrictedData } from '../../../../shared/models/individual/individual-type-restricted-data';

@Component({
  selector: 'app-resource',
  templateUrl: './resource.component.html',
  styleUrls: ['./resource.component.scss']
})
export class ResourceComponent implements OnInit, OnDestroy {

  @ViewChild('targetDiv', { static: true }) targetDiv: ElementRef;

  public title: string;
  public type: ResourceType;
  public resources: any[];
  public gin: string;
  public selectedResource: Resource;
  public individual: Individual;
  public isTabsDisplayed: boolean;
  public displayOrHideTabsBtnLabel: string;

  private subscription: Subscription;
  private crudSubscription: Subscription;
  private resourceService: ResourceService<any>;

  public isCreateBtnDisabled: boolean;

  public isAddBtnVisible = false;

  private isOnSmallScreen = false;

  constructor(
    private route: ActivatedRoute,
    private _resourceUtilityService: ResourceUtilityService,
    private individualService: IndividualService,
    private _errorService: ErrorService,
    private _rightService: RightsService,
    public dialog: MatDialog,
    private router: Router,
    private dialogService: DialogService,
    private crudService: CrudService
  ) { }

  ngOnInit() {
    const resolvedData: any = this.route.snapshot.data['resources'];
    this.title = resolvedData.title;
    this.type = resolvedData.type;
    this.isCreateBtnDisabled = this.isBtnDisabled();
    this.gin = resolvedData.gin;
    this.resources = resolvedData.data;
    this.individual = this.individualService.getIndividual();
    this.isTabsDisplayed = true;
    this.displayOrHideTabsBtnLabel = 'HIDE-ALL-RESOURCES';
    this.selectedResource = this.resources[0];
    this._resourceUtilityService.selectResource(this.selectedResource);
    this.resourceService = this._resourceUtilityService.getResourceService(this.type);
    this.checkIfCanCreate();
    this.isOnSmallScreen = window.innerWidth <= 1275;
    // Subscribe to resource utility service when resource is selected
    this.subscription = this._resourceUtilityService
      .getSelectedResource()
      .subscribe((res: Resource) => {
        this.selectedResource = res;
        if (res && this.isOnSmallScreen) {
          this.scrollDown();
        }
      });

    // Subscribe to CRUD service
    this.crudSubscription = this.crudService.crud$.subscribe((act: CRUDAction) => {
      switch (act.operation) {
        case CRUDOperation.Create:
          this.createCallback();
          break;
        case CRUDOperation.Update:
          this.updateCallback(act.data);
          break;
        case CRUDOperation.Delete:
          this.deleteCallback();
          break;
      }
    });
    // Listen to window's inner width, if superior to 1275 then force the tabs to be displayed
    window.onresize = () => {
      if (window.innerWidth > 1275) {
        this.isTabsDisplayed = true;
        this.displayOrHideTabsBtnLabel = (this.isTabsDisplayed) ? 'HIDE-ALL-RESOURCES' : 'DISPLAY-ALL-RESOURCES';
        this.isOnSmallScreen = false;
      } else {
        this.isOnSmallScreen = true;
      }
    };
  }

  private scrollDown() {
    if (this.isOnSmallScreen) {
      const targetElement = this.targetDiv.nativeElement;
      targetElement.scrollIntoView({behavior: 'smooth'});
    }
  }

  private deleteCallback(): void {
    this._errorService.displayDefaultError('DELETION-SUCCESS-MSG');
    this.resourceService.list().then((data: any[]) => {
      if (!data || data.length === 0) { this.router.navigate(['/individuals/individual/dashboard']); }
      this._resourceUtilityService.selectResource(null);
      this.resources = data;
    });
  }

  private updateCallback(d: any): void {
    this._errorService.displayDefaultError('UPDATE-SUCCESS-MSG');
    this.resourceService.list().then((data: any[]) => {
      this.resources = data;
      this._resourceUtilityService.selectUpdatedResourceById(d.id, data);
    });
  }

  private createCallback(): void {
    this._errorService.displayDefaultError('CREATION-SUCCESS-MSG');
    this.resourceService.list().then((data: any[]) => {
      this.selectedResource = this.findCreatedResource(this.resources, data);
      this._resourceUtilityService.selectResource(this.selectedResource);
      this.resources = data;
    });
  }

  /**
   * Check if the create button has to be displayed by checking user rights
   */
  private checkIfCanCreate(): any {
    // Use the rightService to know if it is possible to create a resource
    this._rightService.canCreateResource(this.type).then((res: boolean) => {
      this.isAddBtnVisible = res;
      if (this.isAddBtnVisible) {
        // For travelers and prospects it is not possible to create a data
        if (IndividualTypeRestrictedData.haveRestriction(this.individual.type)) {
          this.isAddBtnVisible = false;
        }
      }
    });
  }

  openCreateDialog(): void {
    this.dialogService.openCreateDialog(this.type, '75%', '90%');
  }

  ngOnDestroy() {
    this._resourceUtilityService.selectResource(null);
    this.subscription.unsubscribe();
    this.crudSubscription.unsubscribe();
  }

  displayOrHideTabs(): void {
    this.isTabsDisplayed = !this.isTabsDisplayed;
    this.displayOrHideTabsBtnLabel = (this.isTabsDisplayed) ? 'HIDE-ALL-RESOURCES' : 'DISPLAY-ALL-RESOURCES';
  }

  private isBtnDisabled(): boolean {
    return (!(this.dialogService.isModifiableResource(this.type)));
  }

  private findCreatedResource(oldListOfResources: Resource[], newListOfResources: Resource[]): Resource {
    if (newListOfResources.length === 1) { return newListOfResources[0]; }
    const oldResourceIDs = [];
    oldListOfResources.map((resource: Resource) => oldResourceIDs.push(resource.id));
    const newResources = [];
    newListOfResources.forEach((resource: Resource) => {
      if (oldResourceIDs.indexOf(resource.id) === -1) { newResources.push(resource); }
    });
    return (newResources.length > 0) ? newResources[0] : null;
  }

}
