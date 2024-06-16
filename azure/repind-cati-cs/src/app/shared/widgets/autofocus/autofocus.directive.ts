import { Directive, Input, ElementRef, OnInit } from '@angular/core';

@Directive({
  selector: '[appAutoFocus]'
})
export class AutofocusDirective implements OnInit {

  @Input() public autoFocus: boolean;

  constructor(private el: ElementRef) { }

  ngOnInit(): void {

    if (this.autoFocus) {
      setTimeout(() => {
        this.el.nativeElement.focus();
      }, 250);
    }

  }

}
