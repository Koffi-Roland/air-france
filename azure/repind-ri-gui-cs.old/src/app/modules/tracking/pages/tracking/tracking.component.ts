import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { TrackingIndividualService } from '../../../../core/services/tracking/trackingIndividual.service';
import { CommonService } from '../../../../core/services/common.service';
import { EnumService } from '../../../../core/services/enum.service';
import { IndividualService } from '../../../../core/services/individual/individual.service';
import { Individual } from '../../../../shared/models/individual/individual';

@Component({
  selector: 'app-tracking',
  templateUrl: './tracking.component.html',
  styleUrls: ['./tracking.component.scss']
})
export class TrackingComponent implements OnInit {

  private individualGIN = '';

  public tracking: any;
  public dataSelected = {
    dateSelected: null,
    actionOrTypeSelected: ''
  };
  public export: any;

  constructor(public trackingIndividualService: TrackingIndividualService, private route: ActivatedRoute,
    public common: CommonService, public individualService: IndividualService, private router: Router) {
   }

  ngOnInit() {
    this.tracking = this.route.snapshot.data['trackingIndividual'];
    this.export = this.trackingIndividualService.getExport();
    this.individualGIN = this.individualService.getIndividual().gin;
  }

  // Send request to get next data
  getNextTracking() {
    this.trackingIndividualService.getNextTracking().then(data => {
      this.updateControllerData(data);
    });
  }

  // Send request with specific filter
  getFilteredData() {
    this.trackingIndividualService.getFilteredData(this.dataSelected.dateSelected, this.dataSelected.actionOrTypeSelected).then(data => {
      this.updateControllerData(data);
    });
  }

  // Reset data and load initial request
  resetData() {
    this.dataSelected.dateSelected = null;
    this.dataSelected.actionOrTypeSelected = '';
    this.trackingIndividualService.getFilteredData(this.dataSelected.dateSelected, this.dataSelected.actionOrTypeSelected).then(data => {
      this.updateControllerData(data);
    });
  }

  // Get Icon for specific type
  getIcon(type: string) {
    return EnumService.getIcon(type);
  }

  // Return all tracking type from service already loaded
  getRefTracking() {
    return EnumService.REF_TRACKING;
  }

  // Update data of controller with data from service
  private updateControllerData(data: any) {
    this.tracking = data;
    this.export = this.trackingIndividualService.getExport();
  }

  public goBack() {
    this.router.navigate(['/individuals/individual/dashboard']);
  }

}
