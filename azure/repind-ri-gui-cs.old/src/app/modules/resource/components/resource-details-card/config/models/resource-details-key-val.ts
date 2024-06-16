export class ResourceDetailsKeyVal {

  private _keyCopy: string;
  private _forceNotTranslate: boolean;

  constructor (private _key: string, private _val: string | Date, private _isDate?: boolean) {}

  public get key(): string {
    return this._key;
  }

  public set key(key: string) {
    this._key = key;
  }

  public set keyCopy(key: string) {
    this._keyCopy = key;
  }

  public get keyCopy(): string {
    return this._keyCopy;
  }

  public get val(): string | Date {
    return this._val;
  }

  public get isDate(): boolean {
    return this._isDate;
  }

  public get forceNotTranslate(): boolean {
    return this._forceNotTranslate;
  }

  public set forceNotTranslate(b: boolean) {
    this._forceNotTranslate = b;
  }
}
