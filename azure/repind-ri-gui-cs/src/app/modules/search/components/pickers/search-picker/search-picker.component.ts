import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-search-picker',
  templateUrl: './search-picker.component.html',
  styleUrls: ['./search-picker.component.scss']
})
export class SearchPickerComponent implements OnInit {

  public search: string;

  constructor(private router: Router) { }

  ngOnInit() {
    this.updateSelectedSearch();
  }

  /**
   * Initialize the search type from the url of the page
   */
  private updateSelectedSearch(): void {
    const url = this.router.url;
    const splittedUrl = url.split('/');
    const name = splittedUrl[splittedUrl.length - 1];
    this.search = name;
  }

}
