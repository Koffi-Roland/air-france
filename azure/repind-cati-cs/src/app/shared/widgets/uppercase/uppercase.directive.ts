import { Directive, Input, HostListener, Output, EventEmitter } from '@angular/core';
import { NgControl } from '@angular/forms';

@Directive({
  selector: '[appUpperCase]'

})
export class UpperCaseDirective {

  @Input() public uppercase: boolean;

  constructor(private control : NgControl) { }

  @HostListener('input', ['$event']) onInput(event) {
    if (this.uppercase) {
      this.control.control.setValue(event.target.value.toUpperCase());
    }
  }

}
