import { Component, OnInit, Inject } from '@angular/core';
import { MAT_SNACK_BAR_DATA, MatSnackBarRef } from '@angular/material/snack-bar';

@Component({
  selector: 'app-error-snackbar',
  templateUrl: './error-snackbar.component.html',
  styleUrls: ['./error-snackbar.component.scss']
})
export class ErrorSnackbarComponent implements OnInit {

  public errorMsg = '';

  constructor(@Inject(MAT_SNACK_BAR_DATA) public data: any, private snackbarRef: MatSnackBarRef<ErrorSnackbarComponent>) { }

  ngOnInit() {
    this.errorMsg = this.data.text;
  }

  close(): void {
    this.snackbarRef.dismiss();
  }

}
