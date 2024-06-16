import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AccountSearchFormComponent } from './account-search-form.component';

describe('AccountSearchFormComponent', () => {
  let component: AccountSearchFormComponent;
  let fixture: ComponentFixture<AccountSearchFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AccountSearchFormComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AccountSearchFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
