import {ComponentFixture, TestBed} from '@angular/core/testing';

import {AdminJobsComponent} from './admin-jobs.component';

describe('JobComponent', () => {
  let component: AdminJobsComponent;
  let fixture: ComponentFixture<AdminJobsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [AdminJobsComponent]
    })
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AdminJobsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
