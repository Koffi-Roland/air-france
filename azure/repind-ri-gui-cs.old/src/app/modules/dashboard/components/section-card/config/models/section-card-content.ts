import { AdditionalInfo } from './additional-info';

export class SectionCardContent {

  constructor(private _title: string, private _mainIcon: string, private _additionalInfo: AdditionalInfo[], private _color: string) {}

  get title(): string {
    return this._title;
  }

  get mainIcon(): string {
    return this._mainIcon;
  }

  get additionalInfo(): AdditionalInfo[] {
    return this._additionalInfo;
  }

  get color(): string {
    return this._color;
  }

}
