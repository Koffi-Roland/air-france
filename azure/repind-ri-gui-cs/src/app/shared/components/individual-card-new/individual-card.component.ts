import { Component, OnInit, Input, Output, EventEmitter, OnDestroy } from '@angular/core';
import { Individual } from '../../models/individual/individual';
import { IndividualService } from '../../../core/services/individual/individual.service';
import { CrudService } from '../../../core/services/crud/crud.service';
import { CRUDAction } from '../../models/crud/CRUDAction';
import { ResourceType } from '../../models/resources/resource-type';
import { Subscription } from 'rxjs';
import { DialogService } from '../../../core/services/dialogs/dialog.service';
import { IndividualDetailsComponent } from '../dialogs/individual-details/individual-details.component';
import { IndividualUpdateDialogComponent } from '../dialogs/individual-update-dialog/individual-update-dialog.component';
import { CommonService } from '../../../core/services/common.service';

@Component({
  selector: 'app-individual-card',
  templateUrl: './individual-card.component.html',
  styleUrls: ['./individual-card.component.scss']
})
export class IndividualCardComponent implements OnInit, OnDestroy {

  @Output() dataReloaded = new EventEmitter<any>();

  public individual: Individual;

  private updateSubscription: Subscription;

  constructor(public common: CommonService, private individualService: IndividualService,
    private crudService: CrudService, private dialogService: DialogService) { }

  ngOnInit() {
    this.individual = this.individualService.getIndividual();
    this.updateSubscription = this.crudService.crud$.
      subscribe((action: CRUDAction) => {
        if (action.type === ResourceType.Individual) {
          this.individualService.reloadIndividualData().then((data: Individual) => this.individual = data);
        }
      });
  }

  ngOnDestroy() {
    this.updateSubscription.unsubscribe();
  }

  public reloadData(): void {
    this.individualService.reloadIndividualData().then((i: Individual) => {
      this.individual = i;
      this.dataReloaded.emit();
    });
  }

  public openIndividualDetailsModal(): void {
    this.dialogService.openDialogComponent(IndividualDetailsComponent, { individual: this.individual });

  }

  public openIndividualUpdateModal(): void {
    this.dialogService.openDialogComponent(IndividualUpdateDialogComponent, { individual: this.individual });
  }

}
