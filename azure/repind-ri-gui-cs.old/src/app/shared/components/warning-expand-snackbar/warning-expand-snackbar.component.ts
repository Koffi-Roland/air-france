import { Component, Inject, OnInit } from '@angular/core';
import { MatSnackBarRef, MAT_SNACK_BAR_DATA } from '@angular/material/snack-bar';
import { WarningExpandSnackbarModel } from './model/warning-expand-snackbar-model';

@Component({
  selector: 'app-warning-expand-snackbar',
  templateUrl: './warning-expand-snackbar.component.html',
  styleUrls: ['./warning-expand-snackbar.component.scss']
})
export class WarningExpandSnackbarComponent implements OnInit {

  public isExpanded: boolean = false;
  public warningModel: WarningExpandSnackbarModel;

  constructor(@Inject(MAT_SNACK_BAR_DATA) public data, private snackbarRef: MatSnackBarRef<WarningExpandSnackbarComponent>) { }

  ngOnInit(): void {
    this.warningModel = this.data;
  }

  expand() {
    this.isExpanded = true;
  }

  unexpand() {
    this.isExpanded = false;
  }

  doAction() {
    this.warningModel.actionFunction();
    this.close();
  }

  close() {
    this.snackbarRef.dismiss();
    this.unexpand();
  }

}
