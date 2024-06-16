import { Injectable } from '@angular/core';
import { Resolve, Router } from '@angular/router';
import { ResourceUtilityService } from '../services/resources/resource-utility.service';
// tslint:disable-next-line: max-line-length
import { ResourcesNotFoundDialogComponent } from '../../shared/components/dialogs/resources-not-found-dialog/resources-not-found-dialog.component';
import { ResourceService } from '../services/resources/resource.service';
import { DialogService } from '../services/dialogs/dialog.service';
import { IndividualService } from '../services/individual/individual.service';
import { IndividualTypeRestrictedData } from '../../shared/models/individual/individual-type-restricted-data';

@Injectable()
export class ResourceResolver implements Resolve<any> {

  private resourceService: ResourceService<any>;

  constructor(
    private individualService: IndividualService,
    private _resourceUtilityService: ResourceUtilityService,
    private router: Router,
    private dialogService: DialogService
  ) { }

  resolve(): Promise<any> | boolean | void {
    const i = this.individualService.getIndividual();
    if (!i) {
      this.router.navigate(['']);
      return;
    }
    const dataForModal = {'type': null, 'individualTorW': null};
    dataForModal.type = this._resourceUtilityService.getResourceType();
    this.resourceService = this._resourceUtilityService.getResourceService(dataForModal.type);
    // If the resource service has not been initialized correctly, navigate to search
    if (!this.resourceService) {
      this.router.navigate(['individuals/search/gin']);
      return null;
    }

    dataForModal.individualTorW = IndividualTypeRestrictedData.haveRestriction(i.type);
    return this.resourceService
      .list()
      .then((data: any[]) => {
        // if no resources have been found stay on dashboard and pop an error message
        if (!data || data.length === 0) {
          this.dialogService.openDialogComponent(ResourcesNotFoundDialogComponent, dataForModal, 'auto', 'auto');
          this.router.navigate(['individuals/individual/dashboard']);

        } else {
          const title = this._resourceUtilityService.getResourcePageTitle(dataForModal.type);
          return {
            gin: i.gin,
            type: dataForModal.type,
            data: data,
            title: title
          };
        }
      });
  }

}
