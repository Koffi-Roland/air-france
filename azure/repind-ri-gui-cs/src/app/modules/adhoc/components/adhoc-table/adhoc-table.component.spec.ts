import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdhocTableComponent } from './adhoc-table.component';

describe('AdhocTableComponent', () => {
  let component: AdhocTableComponent;
  let fixture: ComponentFixture<AdhocTableComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AdhocTableComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AdhocTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
