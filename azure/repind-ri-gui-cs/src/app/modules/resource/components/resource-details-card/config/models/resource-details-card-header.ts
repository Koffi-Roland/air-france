export class ResourceDetailsCardHeader {
  constructor(
    private _icon: string,
    private _labelTopLeft: string,
    private _buttonIcon: string,
    private _buttonColor: string,
    private _buttonTooltipMsg: string,
    private _labelBottom: string,
    private _additionalInformation: Array<string>,
    private _isChip: boolean,
    private _backgroundColor: string,
  ) {}

  get icon(): string {
    return this._icon;
  }

  get labelTopLeft(): string {
    return this._labelTopLeft;
  }

  get buttonIcon(): string {
    return this._buttonIcon;
  }

  get buttonColor(): string {
    return this._buttonColor;
  }

  get buttonTooltipMsg(): string {
    return this._buttonTooltipMsg;
  }

  get labelBottom(): string {
    return this._labelBottom;
  }

  get additionalInformation(): Array<string> {
    return this._additionalInformation;
  }

  get isChip(): boolean {
    return this._isChip;
  }

  get backgroundColor(): string {
    return this._backgroundColor;
  }
}
