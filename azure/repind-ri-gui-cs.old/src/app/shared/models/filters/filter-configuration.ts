export class FilterConfiguration {

    constructor(private _key: any, private _label: string, private _count: number) { }

    get key(): any {
        return this._key;
    }

    get count(): number {
        return this._count;
    }

    get label(): string {
        return this._label;
    }

}
