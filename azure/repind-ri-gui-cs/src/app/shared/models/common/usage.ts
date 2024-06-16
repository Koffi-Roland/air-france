export class Usage {

    constructor(private _applicationCode: string, private _number: number, private _role1: string,
        private _role2: string, private _role3: string, private _role4: string, private _role5: string) { }

    get applicationCode(): string {
        return this._applicationCode;
    }

    get number(): number {
        return this._number;
    }

    get role1(): string {
        return this._role1;
    }

    get role2(): string {
        return this._role2;
    }

    get role3(): string {
        return this._role3;
    }

    get role4(): string {
        return this._role4;
    }

    get role5(): string {
        return this._role5;
    }

    get fullUsageLabel(): string {
        let label = `${this._applicationCode} ${this._number}${this._role1}${this._role2}${this._role3}${this._role4}${this._role5}`;
        // Add M = mailing if needed
        if (this._role1 === 'M') {
            label += ' (M : Mailing)';
        }
        return label;
    }

    public toString(): string {
        let label = `${this._applicationCode} 0${this._number}${this._role1}${this._role2}${this._role3}${this._role4}${this._role5}`;
        // Add M = mailing if needed
        if (this._role1 === 'M') {
            label = `${this._applicationCode} 0${this._number}`;
            label += ' (M : Mailing)';
        }
        return label;
    }

}
