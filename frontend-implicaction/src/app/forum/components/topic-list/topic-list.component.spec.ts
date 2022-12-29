import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TopicListComponent } from './topic-list.component';

describe('TopicListComponent', () => {
  let component: TopicListComponent;
  let fixture: ComponentFixture<TopicListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TopicListComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TopicListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
