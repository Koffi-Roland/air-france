import { Component, OnInit, ElementRef, ViewChild, Input, Output, EventEmitter, Inject } from '@angular/core';
import { DOCUMENT } from '@angular/common';
/**
 * SearchByColumnDirective
 * This directive is to be initialized as a directive by calling the factory method.
 * The directive for display search input and search in column
 */
@Component({
  selector: 'search-by-column',
  templateUrl: './search-by-column.component.html'
})
export class SearchByColumnComponent implements OnInit {

    searchVisible: boolean;
    vcImplemented: boolean;
    @Input() columnName: string;
    @Output() columnNameChange = new EventEmitter();
    @ViewChild('input', { static: true }) vc: ElementRef;

    constructor() {
    }

    /**
     * Called at link stage of the directive
     */
      ngOnInit(): void {
        // Init display input search to false
        this.searchVisible = false;
        this.vcImplemented = false;
      }

    // For show/hide input search
    displaySearch = function() {
      this.searchVisible = !this.searchVisible;
      setTimeout(() => {
        this.vc.nativeElement.focus();
      });
    };

    // If user press key "enter" or "escape", hide input
    checkIfEnterKeyWasPressed = function(event) {
      const keyCode = event.which || event.keyCode;
      if (!this.columnName && (keyCode === 13 || keyCode === 27)) {
          this.searchVisible = false;
      }

  };

  onClickedOutside = function(event) {
    if (!this.columnName) {
      this.searchVisible = false;
    }
  };

  sendToParentComponent = function() {
    this.columnNameChange.emit(this.columnName);
  };

}
