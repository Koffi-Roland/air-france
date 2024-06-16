import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdhocEditComponent } from './adhoc-edit.component';

describe('AdhocEditComponent', () => {
  let component: AdhocEditComponent;
  let fixture: ComponentFixture<AdhocEditComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AdhocEditComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AdhocEditComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
