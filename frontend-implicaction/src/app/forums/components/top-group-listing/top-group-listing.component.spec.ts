import {ComponentFixture, TestBed} from '@angular/core/testing';

import {TopGroupListingComponent} from './top-group-listing.component';

describe('TopGroupListingComponent', () => {
  let component: TopGroupListingComponent;
  let fixture: ComponentFixture<TopGroupListingComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [TopGroupListingComponent]
    })
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(TopGroupListingComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
