import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ConsentUpdateDialogComponent } from './consent-update-dialog.component';

describe('ConsentUpdateDialogComponent', () => {
  let component: ConsentUpdateDialogComponent;
  let fixture: ComponentFixture<ConsentUpdateDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ConsentUpdateDialogComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ConsentUpdateDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
