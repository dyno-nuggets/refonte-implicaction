import {ComponentFixture, TestBed} from '@angular/core/testing';

import {JobPostingFormComponent} from './offer-form.component';

describe('OfferFormComponent', () => {
  let component: JobPostingFormComponent;
  let fixture: ComponentFixture<JobPostingFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [JobPostingFormComponent]
    })
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(JobPostingFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
