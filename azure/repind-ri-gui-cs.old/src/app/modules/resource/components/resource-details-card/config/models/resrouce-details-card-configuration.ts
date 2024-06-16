import { ResourceDetailsCardHeader } from './resource-details-card-header';
import { ResourceDetailsCardContent } from './resource-details-card-content';

export class ResourceDetailsCardConfiguration {
  constructor(private _header: ResourceDetailsCardHeader, private _content: ResourceDetailsCardContent) {}

  get header(): ResourceDetailsCardHeader {
    return this._header;
  }

  get content(): ResourceDetailsCardContent {
    return this._content;
  }
}
