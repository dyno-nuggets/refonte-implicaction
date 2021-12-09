import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BusinessAreaComponent } from './business-area.component';

describe('BusinessAreaComponent', () => {
  let component: BusinessAreaComponent;
  let fixture: ComponentFixture<BusinessAreaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BusinessAreaComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(BusinessAreaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
