import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { BasicIndividualCardComponent } from './basic-individual-card.component';

describe('BasicIndividualCardComponent', () => {
  let component: BasicIndividualCardComponent;
  let fixture: ComponentFixture<BasicIndividualCardComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ BasicIndividualCardComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BasicIndividualCardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
