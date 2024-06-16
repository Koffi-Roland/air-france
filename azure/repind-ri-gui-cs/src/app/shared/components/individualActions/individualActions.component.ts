import { Component, OnInit, EventEmitter, Output } from '@angular/core';
import { MatBottomSheetRef } from '@angular/material/bottom-sheet';
import { Router } from '@angular/router';
import { ResourceType } from '../../models/resources/resource-type';
import { ResourceUtilityService } from '../../../core/services/resources/resource-utility.service';
import { AdministratorService } from '../../../core/services/administrator-tools/administrator.service';
import { IndividualService } from '../../../core/services/individual/individual.service';
import { Individual } from '../../models/individual/individual';

@Component({
  selector: 'app-individualactions',
  templateUrl: './individualActions.component.html',
  styleUrls: ['./individualActions.component.scss']
})
export class IndividualActionsComponent implements OnInit {

  constructor(
    private bottomSheetRef: MatBottomSheetRef,
    private resourceUtilityService: ResourceUtilityService,
    private router: Router,
    private administratorService: AdministratorService,
    private individualService: IndividualService) { }

  ngOnInit() {}

  openLink(): void {
    this.bottomSheetRef.dismiss();
  }

  goToInferred(): void {

    this.resourceUtilityService.setResourceType(ResourceType.Inferred);
    this.router.navigate(['individuals/individual/resources']);

  }

  goToHandicap(): void {

    this.resourceUtilityService.setResourceType(ResourceType.Handicap);
    this.router.navigate(['individuals/individual/resources']);

  }

  goToConsent(): void {
    this.resourceUtilityService.setResourceType(ResourceType.Consent);
    this.router.navigate(['individuals/individual/resources']);

  }

  unmergeIndividual(): void {
    this.administratorService.unmergeIndividual(this.individualService.getIndividual().gin);
  }

  reactivateAccountIndividual(): void {
    this.administratorService.reactivateAccountIndividual(this.individualService.getIndividual().gin);
  }

}
