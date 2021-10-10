import {ComponentFixture, TestBed} from '@angular/core/testing';

import {DateTimelapseComponent} from './date-timelapse.component';

describe('DateTimelapseComponent', () => {
  let component: DateTimelapseComponent;
  let fixture: ComponentFixture<DateTimelapseComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [DateTimelapseComponent]
    })
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(DateTimelapseComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
