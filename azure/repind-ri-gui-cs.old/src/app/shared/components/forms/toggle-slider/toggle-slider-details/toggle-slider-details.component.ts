import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'app-toggle-slider-details',
  templateUrl: './toggle-slider-details.component.html',
  styleUrls: ['./toggle-slider-details.component.scss']
})
export class ToggleSliderDetailsComponent implements OnInit {

  /**
   *  handle a detail title (key) & this title's detail attributes (to translate) and values 
   */
  @Input()
  public details: Map<string, Map<string, any>>

  constructor() { }

  ngOnInit(): void {
  }

}
