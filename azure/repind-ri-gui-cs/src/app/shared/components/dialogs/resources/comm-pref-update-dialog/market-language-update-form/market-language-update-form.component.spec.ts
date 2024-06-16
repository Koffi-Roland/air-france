import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MarketLanguageUpdateFormComponent } from './market-language-update-form.component';

describe('MarketLanguageUpdateFormComponent', () => {
  let component: MarketLanguageUpdateFormComponent;
  let fixture: ComponentFixture<MarketLanguageUpdateFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [MarketLanguageUpdateFormComponent]
    })
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(MarketLanguageUpdateFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
