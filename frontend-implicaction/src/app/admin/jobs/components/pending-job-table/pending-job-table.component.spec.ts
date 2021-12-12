import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PendingJobTableComponent } from './pending-job-table.component';

describe('PendingJobTableComponent', () => {
  let component: PendingJobTableComponent;
  let fixture: ComponentFixture<PendingJobTableComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PendingJobTableComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PendingJobTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
