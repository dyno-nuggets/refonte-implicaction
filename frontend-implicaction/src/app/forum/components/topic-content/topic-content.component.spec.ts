import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TopicContentComponent } from './topic-content.component';

describe('TopicContentComponent', () => {
  let component: TopicContentComponent;
  let fixture: ComponentFixture<TopicContentComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TopicContentComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TopicContentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
