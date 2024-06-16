import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-results-view-picker',
  templateUrl: './results-view-picker.component.html',
  styleUrls: ['./results-view-picker.component.scss']
})
export class ResultsViewPickerComponent implements OnInit {

  public view = '';

  constructor(private router: Router) { }

  ngOnInit() {
    this.initializeViewName();
  }

  /**
   * Initialize the view name according to the url path
   */
  private initializeViewName(): void {
    const url = this.router.url;
    const splittedUrl = url.split('/');
    const name = splittedUrl[splittedUrl.length - 1];
    this.view = name;
  }

}
