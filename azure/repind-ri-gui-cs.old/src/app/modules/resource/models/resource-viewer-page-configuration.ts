import { ResourceTabsConfiguration } from '../components/resource-tabs/config/models/resource-tabs-configuration';
// tslint:disable-next-line: max-line-length
import { ResourceTabsCardConfiguration } from '../components/resource-tabs/resource-tabs-card/config/models/resource-tabs-card-configuration';
import { ResourceDetailsCardConfiguration } from '../components/resource-details-card/config/models/resrouce-details-card-configuration';

export class ResourceViewerPageConfiguration {

    constructor(private _title: string, private _resourceTabsConfig: ResourceTabsConfiguration[],
        private _resourceTabsCardConfig: ResourceTabsCardConfiguration,
        private _resourceDetailsConfig: ResourceDetailsCardConfiguration) { }

    get title(): string {
        return this._title;
    }

    get tabsConfig(): ResourceTabsConfiguration[] {
        return this._resourceTabsConfig;
    }

    get tabsCardConfig(): ResourceTabsCardConfiguration {
        return this._resourceTabsCardConfig;
    }

    get resourceDetailsCardConfig(): ResourceDetailsCardConfiguration {
        return this._resourceDetailsConfig;
    }

}
