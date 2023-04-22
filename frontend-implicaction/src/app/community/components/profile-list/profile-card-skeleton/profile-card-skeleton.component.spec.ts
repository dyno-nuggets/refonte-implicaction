import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProfileCardSkeletonComponent } from './profile-card-skeleton.component';

describe('ProfileCardSkeletonComponent', () => {
  let component: ProfileCardSkeletonComponent;
  let fixture: ComponentFixture<ProfileCardSkeletonComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ProfileCardSkeletonComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ProfileCardSkeletonComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
