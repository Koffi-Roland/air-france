export class ResourceResume {

    constructor(private _count: number, private _icon: string, private _tooltip: string) {}

    get count(): number {
        return this._count;
    }

    get icon(): string {
        return this._icon;
    }

    get tooltip(): string {
        return this._tooltip;
    }

}
