import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RelationButtonComponent } from './relation-button.component';

describe('RelationButtonComponent', () => {
  let component: RelationButtonComponent;
  let fixture: ComponentFixture<RelationButtonComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RelationButtonComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RelationButtonComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
