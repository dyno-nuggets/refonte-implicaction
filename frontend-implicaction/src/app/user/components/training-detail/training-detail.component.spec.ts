import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TrainingDetailComponent } from './training-detail.component';

describe('TrainingDetailComponent', () => {
  let component: TrainingDetailComponent;
  let fixture: ComponentFixture<TrainingDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TrainingDetailComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(TrainingDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
