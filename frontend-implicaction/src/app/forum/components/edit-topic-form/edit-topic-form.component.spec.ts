import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EditTopicFormComponent } from './edit-topic-form.component';

describe('EditTopicFormComponent', () => {
  let component: EditTopicFormComponent;
  let fixture: ComponentFixture<EditTopicFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ EditTopicFormComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(EditTopicFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
