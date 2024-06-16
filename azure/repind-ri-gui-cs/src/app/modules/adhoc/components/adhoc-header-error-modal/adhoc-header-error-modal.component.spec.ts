import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdhocModalComponent } from './adhoc-header-error-modal.component';

describe('AdhocModalComponent', () => {
  let component: AdhocModalComponent;
  let fixture: ComponentFixture<AdhocModalComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AdhocModalComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AdhocModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
