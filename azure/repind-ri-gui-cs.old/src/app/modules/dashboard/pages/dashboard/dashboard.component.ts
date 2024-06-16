import { Subscription } from 'rxjs';
import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { MatBottomSheet } from '@angular/material/bottom-sheet';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { Individual } from '../../../../shared/models/individual/individual';
import { CommonService } from '../../../../core/services/common.service';
import { SearchService } from '../../../../core/services/search/search.service';
// tslint:disable-next-line: max-line-length
import { ResourceTypeSelectorDialogComponent } from '../../../../shared/components/dialogs/resource-type-selector-dialog/resource-type-selector-dialog.component';
import { IndividualActionsComponent } from '../../../../shared/components/individualActions/individualActions.component';
import { IndividualService } from '../../../../core/services/individual/individual.service';
import { ResourceType } from '../../../../shared/models/resources/resource-type';
import { CrudService } from '../../../../core/services/crud/crud.service';
import { CRUDAction } from '../../../../shared/models/crud/CRUDAction';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent implements OnInit, OnDestroy {

  public individual: Individual;

  public crudSubscription: Subscription;

  constructor(private route: ActivatedRoute, private bottomSheet: MatBottomSheet, private dialog: MatDialog,
    private common: CommonService, private crudService: CrudService, private router: Router,
    private searchService: SearchService, private individualService: IndividualService) { }

  public sectionCards: Array<ResourceType> = [ResourceType.Address, ResourceType.Email, ResourceType.Telecom,
  ResourceType.Contract, ResourceType.CommunicationPreference,
  ResourceType.ExternalIdentifier, ResourceType.Preference, ResourceType.Delegation,
  ResourceType.Alert, ResourceType.UCCRRole];

  ngOnInit() {
    this.individual = this.route.snapshot.data['individual'];
    this.crudSubscription = this.crudService.crud$.
      subscribe((action: CRUDAction) => {
        if (action.type === ResourceType.Individual) { return; }
        this.common.showMessage('CREATION-SUCCESS-MSG');
        this.individualService.reloadIndividualData().then((ind: Individual) => this.individual = ind);
      });
  }

  ngOnDestroy() {
    this.crudSubscription.unsubscribe();
  }


  public onDataReloaded(): void {
    this.individual = this.individualService.getIndividual();
  }


  public openResourceSelectorDialog(): void {
    const config: MatDialogConfig<any> = new MatDialogConfig();
    config.width = '300px';
    config.height = 'auto';
    this.dialog.open(ResourceTypeSelectorDialogComponent, config);
  }

  public openBottomSheet(): void {
    this.bottomSheet.open(IndividualActionsComponent);
  }

  public goBack(): void {
    const hasFoundMultipleIndividuals = this.searchService.getHasFoundMultipleIndividuals();
    if (hasFoundMultipleIndividuals) {
      this.router.navigate(['/individuals/search/results/grid']);
    } else {
      this.router.navigate(['/individuals/search/gin']);
    }
  }

}
