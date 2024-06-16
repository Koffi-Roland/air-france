import { Component, OnInit, ViewChild, Output, EventEmitter, Input } from '@angular/core';
import { MatSlideToggle } from '@angular/material/slide-toggle';

@Component({
  selector: 'app-addr-confirmation-step',
  templateUrl: './addr-confirmation-step.component.html',
  styleUrls: ['./addr-confirmation-step.component.scss']
})
export class AddrConfirmationStepComponent implements OnInit {

  @Input() btnLabel: string;
  @Input() isUpdate: boolean;

  @Output() update = new EventEmitter();
  @Output() create = new EventEmitter();
  @Output() check = new EventEmitter<void>();
  @Output() force = new EventEmitter<void>();

  @ViewChild('slideToggle') normalizationSlideToggle: MatSlideToggle;

  public normalizationProcessHasFailed = false;
  public isForced = true;

  constructor() { }

  ngOnInit() {
  }

  get isChecked(): boolean {
    return this.normalizationSlideToggle.checked;
  }

  get hasNormalizationFailed(): boolean {
    return this.normalizationProcessHasFailed;
  }

  set hasNormalizationFailed(bool: boolean) {
    this.normalizationProcessHasFailed = bool;
  }

  public onConfirm(): void {
    (this.isUpdate) ? this.update.emit() : this.create.emit();
  }

  public onCheckBtnClicked(): void {
    this.check.emit();
  }

  public onForceBtnClicked(): void {
    this.force.emit();
  }

}
