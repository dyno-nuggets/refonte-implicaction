import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EditCategoryFormComponent } from './edit-category-form.component';

describe('EditCategoryFormComponent', () => {
  let component: EditCategoryFormComponent;
  let fixture: ComponentFixture<EditCategoryFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ EditCategoryFormComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(EditCategoryFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
