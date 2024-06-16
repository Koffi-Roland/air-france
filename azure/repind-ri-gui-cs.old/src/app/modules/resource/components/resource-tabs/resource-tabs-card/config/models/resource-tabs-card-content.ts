import { Label } from './label';

export class ResourceTabsCardContent {

  constructor (private _labels: Label[], private _chipLabels: Label[]) {}

  get labels(): Label[] {
    return this._labels;
  }

  get chips(): Label[] {
    return this._chipLabels;
  }

}
