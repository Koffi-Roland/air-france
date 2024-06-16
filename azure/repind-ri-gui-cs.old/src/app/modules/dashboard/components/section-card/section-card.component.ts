import { SearchService } from './../../../../core/services/search/search.service';
import { Component, Input, OnChanges } from '@angular/core';
import { SectionCardContent } from './config/models/section-card-content';
import { SectionCardConfigurationLoader } from './config/loaders/section-card-conf-loader';
import { Router } from '@angular/router';
import { Individual } from '../../../../shared/models/individual/individual';
import { CommonService } from '../../../../core/services/common.service';
import { RightsService } from '../../../../core/services/rights/rights.service';
import { ResourceType } from '../../../../shared/models/resources/resource-type';
import { ResourceUtilityService } from '../../../../core/services/resources/resource-utility.service';

@Component({
  selector: 'app-section-card',
  templateUrl: './section-card.component.html',
  styleUrls: ['./section-card.component.scss']
})
export class SectionCardComponent implements OnChanges {

  public config: SectionCardContent;

  @Input() private individual: Individual;
  @Input() private type: ResourceType;

  constructor(private router: Router, private resourceUtilityService: ResourceUtilityService,
    private _rightService: RightsService, private common: CommonService) { }

  ngOnChanges() {
    this.config = SectionCardConfigurationLoader.load(
      this.type,
      this.individual.resume
    );
  }

  openDialog(): void {

    this._rightService.canReadResource(this.type).then((val: boolean) => {
      if (val) {
        this.goToResourceView();
      } else {
        this.common.showMessage('NO-RIGHT-FOR-ACTION-MSG');
      }
    });

  }

  private goToResourceView(): void {
    this.resourceUtilityService.setResourceType(this.type);
    this.router.navigate(['individuals/individual/resources']);
  }

}
