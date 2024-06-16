
import { FilterName } from './../../../shared/models/filters/filter-name.enum';
import { FilterConfiguration } from './../../../shared/models/filters/filter-configuration';
import { FilterGroup } from './../../../shared/models/filters/filter-group';
import { Injectable } from '@angular/core';
import { Status } from '../../../shared/models/common/status';
import { Subject } from 'rxjs';
import { BasicIndividualData } from '../../../shared/models/individual/basic-individual-data';
import { CommonService } from '../../../core/services/common.service';
import { ReferenceDataType } from '../../../shared/models/references/ReferenceDataType.enum';

@Injectable({
  providedIn: 'root'
})
export class SearchFilterService {

  private filteredIndividualsSubject = new Subject<BasicIndividualData[]>();
  private filtersGroupsSubject = new Subject<FilterGroup[]>();
  private filtersResetedSubject = new Subject<FilterGroup[]>();

  public filteredIndividualsObservable = this.filteredIndividualsSubject.asObservable();
  public filtersGroupObservable = this.filtersGroupsSubject.asObservable();
  public filtersResetedObservable = this.filtersResetedSubject.asObservable();

  private individuals: BasicIndividualData[] = null;
  private filteredIndividuals: BasicIndividualData[] = null;

  private civilities: string[];
  private status: Status[];

  private filterGroups: FilterGroup[];
  private arrayLibelle = ['gin', 'status', 'lastname', 'firstname', 'civility', 'birthDate', 'address'];

  constructor() { }

  public init(individuals: BasicIndividualData[]): void {

    this.individuals = individuals;
    this.filteredIndividuals = individuals;

    this.initYearsCivilityStatus();
    this.computeFilterGroups();

  }

  public initSearch(individuals: BasicIndividualData[]): void {

    this.init(individuals);
    this.arrayLibelle = ['gin', 'status', 'lastname', 'firstname', 'civility', 'birthDate', 'address'];

  }

  public initMergeStatistiques(individuals: BasicIndividualData[]): void {

    this.init(individuals);
    this.arrayLibelle = ['gin', 'status', 'lastname', 'firstname', 'civility', 'ginMerged', 'dateModification', 'signatureModification'];

  }

  public getArrayLibelle(): string[] {
    return this.arrayLibelle;
  }

  public getFilteredIndividuals(): BasicIndividualData[] {
    return this.filteredIndividuals;
  }

  public getFilterGroups(): FilterGroup[] {
    return this.filterGroups;
  }

  public getFilterGroup(name: FilterName): FilterGroup {
    const filter = this.filterGroups.filter((g: FilterGroup) => g.name === name);
    return filter[0];
  }

  public filterIndividuals(filter: FilterGroup, value: any): void {
    this.filteredIndividuals = this.filteredIndividuals.filter((individual: BasicIndividualData) => {
      return individual[filter.objProperty] === value;
    });
    this.computeFilterGroups();
    this.filtersGroupsSubject.next(this.filterGroups);
    this.filteredIndividualsSubject.next(this.filteredIndividuals);
  }

  public resetFilters(): any {
    this.filteredIndividuals = this.individuals;
    this.filteredIndividualsSubject.next(this.filteredIndividuals);
    this.computeFilterGroups();
    this.filtersGroupsSubject.next(this.filterGroups);
    this.filtersResetedSubject.next();
  }

  private computeFilterGroups(): void {
    const filters: FilterGroup[] = [];
    filters.push(this.getGroupConfiguration(FilterName.INDIVIDUAL_STATUS, 'STATUS', this.status, 'status', '',
    CommonService.getTransformEnumTypeStat(ReferenceDataType.INDIVIDUAL_STATUS)));
    filters.push(this.getGroupConfiguration(FilterName.INDIVIDUAL_CIVILITY, 'CIVILITY', this.civilities, 'civility',  '',
    CommonService.getTransformEnumTypeStat(ReferenceDataType.CIVILITY)));
    this.filterGroups = filters;
  }

  private getGroupConfiguration(name: FilterName, label: string, arr: any[], property: any, suffix: string,
    prefix: string = ''): FilterGroup {
    const configuration: FilterConfiguration[] = [];
    arr.map((data: any) => {
      const count = this.filteredIndividuals.filter((i: BasicIndividualData) => i[property] === data).length;
      configuration.push(new FilterConfiguration(data, prefix + data + suffix, count));
    });
    return new FilterGroup(name, label, property, configuration);
  }

  private initYearsCivilityStatus(): void {
    const civilities = [], status = [];
    this.individuals.map((individual: BasicIndividualData) => {
      if (civilities.indexOf(individual.civility) === -1) { civilities.push(individual.civility); }
      if (status.indexOf(individual.status) === -1) { status.push(individual.status); }
    });
    this.civilities = civilities;
    this.status = status;
  }

}
