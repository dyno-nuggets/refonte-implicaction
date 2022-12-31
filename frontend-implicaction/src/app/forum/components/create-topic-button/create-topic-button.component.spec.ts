import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateTopicButtonComponent } from './create-topic-button.component';

describe('CreateTopicButtonComponent', () => {
  let component: CreateTopicButtonComponent;
  let fixture: ComponentFixture<CreateTopicButtonComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CreateTopicButtonComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CreateTopicButtonComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
