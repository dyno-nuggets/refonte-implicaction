import {ComponentFixture, TestBed} from '@angular/core/testing';

import {JobsPostingsComponent} from './offers.component';

describe('OffersComponent', () => {
  let component: JobsPostingsComponent;
  let fixture: ComponentFixture<JobsPostingsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [JobsPostingsComponent]
    })
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(JobsPostingsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
