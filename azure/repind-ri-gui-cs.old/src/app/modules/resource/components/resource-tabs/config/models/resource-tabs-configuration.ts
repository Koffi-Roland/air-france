
export class ResourceTabsConfiguration {
  constructor (private _label: string, private _content: Array<any>) {}

  get labels(): string {
    return this._label;
  }

  get content(): Array<any> {
    return this._content;
  }
}
