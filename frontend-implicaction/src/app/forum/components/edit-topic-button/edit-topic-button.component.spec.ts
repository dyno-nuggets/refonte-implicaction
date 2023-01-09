import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EditTopicButtonComponent } from './edit-topic-button.component';

describe('EditTopicButtonComponent', () => {
  let component: EditTopicButtonComponent;
  let fixture: ComponentFixture<EditTopicButtonComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ EditTopicButtonComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(EditTopicButtonComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
