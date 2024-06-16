import { FilterConfiguration } from './filter-configuration';
import { FilterName } from './filter-name.enum';

export class FilterGroup {

    constructor(private _name: FilterName, private _label: string, private _objProperty: string,
        private _configurations: FilterConfiguration[]) { }

    get name(): FilterName {
        return this._name;
    }

    get label(): string {
        return this._label;
    }

    get objProperty(): string {
        return this._objProperty;
    }

    get configurations(): FilterConfiguration[] {
        return this._configurations;
    }

}
