import {ComponentFixture, TestBed} from '@angular/core/testing';

import {TopGroupSkeletonComponent} from './top-group-skeleton.component';

describe('TopGroupSkeletonComponent', () => {
  let component: TopGroupSkeletonComponent;
  let fixture: ComponentFixture<TopGroupSkeletonComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [TopGroupSkeletonComponent]
    })
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(TopGroupSkeletonComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
