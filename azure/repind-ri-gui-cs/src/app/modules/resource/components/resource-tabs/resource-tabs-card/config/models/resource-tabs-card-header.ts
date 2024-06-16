import { Label } from './label';

export class ResourceTabsCardHeader {

    constructor(private _icon: string, private _title: Label, private _backgroundColor: string) { }

    get icon(): string {
        return this._icon;
    }

    get title(): Label {
        return this._title;
    }

    get backgroundColor(): string {
        return this._backgroundColor;
    }

}
