import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PendingGroupTableComponent } from './pending-group-table.component';

describe('PendingGroupTableComponent', () => {
  let component: PendingGroupTableComponent;
  let fixture: ComponentFixture<PendingGroupTableComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PendingGroupTableComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PendingGroupTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
