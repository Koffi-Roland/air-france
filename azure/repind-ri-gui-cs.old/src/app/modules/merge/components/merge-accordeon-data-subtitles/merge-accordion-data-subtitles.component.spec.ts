/* tslint:disable:no-unused-variable */
import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { DebugElement } from '@angular/core';

import { MergeAccordionDataSubtitlesComponent } from './merge-accordion-data-subtitles.component';

describe('MergeAccordionDataSubtitlesComponent', () => {
  let component: MergeAccordionDataSubtitlesComponent;
  let fixture: ComponentFixture<MergeAccordionDataSubtitlesComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ MergeAccordionDataSubtitlesComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MergeAccordionDataSubtitlesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
