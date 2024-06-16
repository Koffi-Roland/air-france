import { ResourceTabsCardHeader } from './resource-tabs-card-header';
import { ResourceTabsCardContent } from './resource-tabs-card-content';

export class ResourceTabsCardConfiguration {

    constructor (private _header: ResourceTabsCardHeader, private _content: ResourceTabsCardContent) {}

    get header(): ResourceTabsCardHeader {
        return this._header;
    }

    get content(): ResourceTabsCardContent {
        return this._content;
    }

}
