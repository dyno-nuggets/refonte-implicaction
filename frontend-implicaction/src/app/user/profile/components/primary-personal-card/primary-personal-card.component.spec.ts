import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PrimaryPersonalCardComponent } from './primary-personal-card.component';

describe('PrimaryPersonalCardComponent', () => {
  let component: PrimaryPersonalCardComponent;
  let fixture: ComponentFixture<PrimaryPersonalCardComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PrimaryPersonalCardComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PrimaryPersonalCardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
