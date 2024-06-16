/**
 * This class represent an additional information that can be seen on section cards.
 * These information are:
 *  - the icon used
 *  - the tooltip text used
 *  - the value indicated by the badge
 */
export class AdditionalInfo {

  constructor(private _icon: string, private _tooltipText: string, private _badgeValue: number) {}

  get icon(): string {
    return this._icon;
  }

  get tooltipText(): string {
    return this._tooltipText;
  }

  get badgeValue(): number {
    return this._badgeValue;
  }

}
