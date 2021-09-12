import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FriendsOverviewComponent } from './friends-overview.component';

describe('FriendsOverviewComponent', () => {
  let component: FriendsOverviewComponent;
  let fixture: ComponentFixture<FriendsOverviewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ FriendsOverviewComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(FriendsOverviewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
