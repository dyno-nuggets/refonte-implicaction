import {ComponentFixture, TestBed} from '@angular/core/testing';

import {VoteboxComponent} from './votebox.component';

describe('VoteboxComponent', () => {
  let component: VoteboxComponent;
  let fixture: ComponentFixture<VoteboxComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [VoteboxComponent]
    })
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(VoteboxComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
