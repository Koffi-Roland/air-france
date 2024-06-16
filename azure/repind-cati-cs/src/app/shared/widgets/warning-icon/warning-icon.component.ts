import { Component, OnInit, Inject, Input } from '@angular/core';
import { WarningIcon } from '../../models/WarningIcon';

/**
 * WarningIconComponent
 * This component is to be initialized as a component by calling the factory method.
 * The component to display warning message depending on conditions
 */

@Component({
  selector: 'app-warning-icon',
  templateUrl: './warning-icon.component.html'
})
export class WarningIconComponent implements OnInit  {

  @Input()
  public data: any;

  @Input()
  public warning: WarningIcon;

  public condition: boolean;

  constructor() {}

  ngOnInit(): void {
    this.setCondition();
  }

  setCondition() {
    let value = false;
    if (this.warning.condition.action === 'EQUALS') {
      if (this.data[this.warning.condition.property] === this.warning.condition.value) {
        value = true;
      }
    }
    this.condition = value;
  }

}
