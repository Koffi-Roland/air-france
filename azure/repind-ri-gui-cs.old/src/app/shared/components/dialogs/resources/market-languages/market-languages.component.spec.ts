import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { MarketLanguagesComponent } from './market-languages.component';

describe('MarketLanguagesComponent', () => {
  let component: MarketLanguagesComponent;
  let fixture: ComponentFixture<MarketLanguagesComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ MarketLanguagesComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MarketLanguagesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
