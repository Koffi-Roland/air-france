import { Component, Input, OnChanges, SimpleChanges, ViewChild, OnDestroy, OnInit } from '@angular/core';
import { ResourceTabsConfiguration } from './config/models/resource-tabs-configuration';
import { ResourceTabsConfigurationLoader } from './config/configuration.loader';
import { ResourceType } from '../../../../shared/models/resources/resource-type';

@Component({
  selector: 'app-resource-tabs',
  templateUrl: './resource-tabs.component.html',
  styleUrls: ['./resource-tabs.component.scss']
})
export class ResourceTabsComponent implements OnChanges, OnDestroy {

  @ViewChild('scrollingThing') scrollingThing: HTMLElement;

  @Input() resourceType: ResourceType;
  @Input() resourceList: any;

  public configuration: Array<ResourceTabsConfiguration>;

  public timer: any;

  constructor() { }

  ngOnChanges(changes: SimpleChanges): void {
    this.resourceList = changes.resourceList.currentValue;
    this.configuration = ResourceTabsConfigurationLoader.loadTabsConfig(this.resourceType, this.resourceList);
  }

  ngOnDestroy() {
    clearTimeout(this.timer);
  }

  public scrollDiv(elementToScroll: HTMLElement, depl: any) {
    elementToScroll.scrollLeft -= depl;
    this.timer = setTimeout(() => {
      this.scrollDiv(elementToScroll, depl);
    }, 30);
  }

  public stopTimer() {
    clearTimeout(this.timer);
  }

}
