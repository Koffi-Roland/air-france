import { OperationType } from './operation-type.enum';

export class Right {

    constructor (private _op: OperationType, private _roles: string[]) {}

    get operation(): OperationType {
        return this._op;
    }

    get roles(): string[] {
        return this._roles;
    }

}
