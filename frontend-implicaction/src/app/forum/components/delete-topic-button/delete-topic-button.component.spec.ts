import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DeleteTopicButtonComponent } from './delete-topic-button.component';

describe('DeleteTopicButtonComponent', () => {
  let component: DeleteTopicButtonComponent;
  let fixture: ComponentFixture<DeleteTopicButtonComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DeleteTopicButtonComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DeleteTopicButtonComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
