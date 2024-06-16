import { Injectable } from '@angular/core';
import { Resolve, Router } from '@angular/router';
import { Individual } from '../../shared/models/individual/individual';
import { IndividualService } from '../services/individual/individual.service';

@Injectable({ providedIn: 'root' })
export class DashboardResolver implements Resolve<any> {

    constructor(private router: Router, private individualService: IndividualService) { }

    resolve(): Promise<Individual> | Individual {
        const promise = new Promise<Individual>((resolve, reject) => {
            let individual = this.individualService.getIndividual();
            if (!individual) { this.router.navigate(['']); }
            if (this.individualService.hasToBeReloaded) {
                // If the data of the individual has been changed then reload the dashboard data
                this.individualService.reloadIndividualData().then((i: Individual) => {
                    individual = i;
                    // Set the boolean to reload data to false
                    this.individualService.hasToBeReloaded = false;
                    // Resolve the new individual data
                    resolve(individual);
                });
            } else {
                // No reload has to be done so just return the previous individual data
                resolve(individual);
            }
        });
        return promise;
    }
}
