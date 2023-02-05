import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DeleteTopicValidationComponent } from './delete-topic-validation.component';

describe('DeleteTopicValidationComponent', () => {
  let component: DeleteTopicValidationComponent;
  let fixture: ComponentFixture<DeleteTopicValidationComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DeleteTopicValidationComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DeleteTopicValidationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
