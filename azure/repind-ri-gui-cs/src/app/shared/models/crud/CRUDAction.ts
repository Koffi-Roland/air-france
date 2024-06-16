import { ResourceType } from './../resources/resource-type';
import { CRUDOperation } from './CRUDOperations.enum';

export class CRUDAction {

    constructor(private _operation: CRUDOperation, private _type: ResourceType, private _data?: any) { }

    get operation(): CRUDOperation {
        return this._operation;
    }

    get type(): ResourceType {
        return this._type;
    }

    get data(): any {
        return this._data;
    }

}
