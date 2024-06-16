import { Component, OnInit, Input, OnDestroy, ViewChild, QueryList, Output, EventEmitter } from '@angular/core';
import { ResourceTabsCardConfigurationLoader } from './config/resource.tabs.configuration.loader';
import { Subscription } from 'rxjs';
import { ResourceTabsCardConfiguration } from './config/models/resource-tabs-card-configuration';
import { ResourceTabsCardHeader } from './config/models/resource-tabs-card-header';
import { ResourceTabsCardContent } from './config/models/resource-tabs-card-content';
import { ResourceUtilityService } from '../../../../../core/services/resources/resource-utility.service';
import { ResourceType } from '../../../../../shared/models/resources/resource-type';
import { Resource } from '../../../../../shared/models/resources/resource';

@Component({
  selector: 'app-resource-tabs-card',
  templateUrl: './resource-tabs-card.component.html',
  styleUrls: ['./resource-tabs-card.component.scss']
})
export class ResourceTabsCardComponent implements OnInit, OnDestroy {

  @ViewChild('scrollableDiv') scrollableDiv: HTMLElement;

  @Input() resource: any;
  @Input() type: ResourceType;

  public isSelectedResource: boolean;
  public selectedResourceSub: Subscription;
  public configuration: ResourceTabsCardConfiguration;
  public header: ResourceTabsCardHeader;
  public content: ResourceTabsCardContent;

  public timer: any;

  constructor(private _resourceUtilityService: ResourceUtilityService) { }

  ngOnInit() {
    this.configuration = ResourceTabsCardConfigurationLoader.loadContent(this.type, this.resource);
    this.header = this.configuration.header;
    this.content = this.configuration.content;
    const currentlySelectedResource = this._resourceUtilityService.getCurrentlySelectedResource();
    this.isSelectedResource = (currentlySelectedResource && (currentlySelectedResource.id === (this.resource as Resource).id));
    this.selectedResourceSub = this._resourceUtilityService.getSelectedResource().subscribe((res: Resource) => {
      if (!res) {
        this.isSelectedResource = false;
      } else {
        this.isSelectedResource = res.id === (this.resource as Resource).id;
      }
    });
  }

  ngOnDestroy() {
    this.selectedResourceSub.unsubscribe();
    clearTimeout(this.timer);
  }

  selectResource() {
    if (this.isSelectedResource) {
      return;
    } else {
      const isAlreadySelected = this.isCurrentSelectedResource();
      const resource = (isAlreadySelected) ? null : this.resource;
      this.isSelectedResource = (isAlreadySelected) ? false : true;
      this._resourceUtilityService.selectResource(resource);
    }
  }

  private isCurrentSelectedResource(): boolean {
    const selectedResource = this._resourceUtilityService.getCurrentlySelectedResource();
    if (!selectedResource) {
      return false;
    } else {
      return (selectedResource.id === this.resource.id);
    }
  }

  /** Scroll div when text length is too long */
  public scrollDiv(elementToScroll: HTMLElement, depl: any) {
    elementToScroll.style.textOverflow = 'clip';
    elementToScroll.scrollLeft -= depl;
    this.timer = setTimeout(() => {
      this.scrollDiv(elementToScroll, depl);
    }, 30);
  }

  public stopTimer(elementToScroll: HTMLElement) {
    clearTimeout(this.timer);
    elementToScroll.scrollTo(0, 0);
    elementToScroll.style.textOverflow = 'ellipsis';
  }

}
