import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SocialSearchFormComponent } from './social-search-form.component';

describe('SocialSearchFormComponent', () => {
  let component: SocialSearchFormComponent;
  let fixture: ComponentFixture<SocialSearchFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SocialSearchFormComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SocialSearchFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
