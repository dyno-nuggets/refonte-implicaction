import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProfileInfoDisplayComponent } from './profile-info-display.component';

describe('ProfileInfoDisplayComponent', () => {
  let component: ProfileInfoDisplayComponent;
  let fixture: ComponentFixture<ProfileInfoDisplayComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ProfileInfoDisplayComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ProfileInfoDisplayComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
