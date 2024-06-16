import { Right } from '../common/rights/right';
import { OperationType } from '../common/rights/operation-type.enum';
import { ResourceType } from './resource-type';

/**
 * Abstract class that represents a resource.
 * Resources are data that can be found in the database concerning an individual.
 * These data can be:
 *  - Addresses,
 *  - Emails,
 *  - Telecoms,
 *  - etc...
 *
 * In the database, all of these resources are identified by their unique ID.
 * Therefore, the only parameter in the constructor is the ID (string). All the
 * data that can be found in the DB are extending this class.
 */
export class Resource {

  constructor(protected _id: string, protected _backgroundColor: string, protected _resourceType: ResourceType) { }

  /** Accessors */

  get id(): string { return this._id; }

  get backgroundColor(): string {
    return this._backgroundColor;
  }

  get resourceType(): ResourceType {
    return this._resourceType;
  }

}
