import {ComponentFixture, TestBed} from '@angular/core/testing';

import {PendingUserTableComponent} from './pending-user-table.component';

describe('PendingUserTableComponent', () => {
  let component: PendingUserTableComponent;
  let fixture: ComponentFixture<PendingUserTableComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [PendingUserTableComponent]
    })
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PendingUserTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
