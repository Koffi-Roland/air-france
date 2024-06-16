import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';

@Component({
  selector: 'app-confirmation-step',
  templateUrl: './confirmation-step.component.html',
  styleUrls: ['./confirmation-step.component.scss']
})
export class ConfirmationStepComponent implements OnInit {

  @Input() label: string;
  @Input() isUpdate: boolean;
  @Input() disabled: boolean;

  @Output() update = new EventEmitter();
  @Output() create = new EventEmitter();

  constructor() { }

  ngOnInit() {
  }

  public onConfirm(): void {
    (this.isUpdate) ? this.update.emit() : this.create.emit();
  }

}
