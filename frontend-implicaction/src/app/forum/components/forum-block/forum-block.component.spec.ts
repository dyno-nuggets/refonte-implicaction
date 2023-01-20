import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ForumBlockComponent } from './forum-block.component';

describe('ForumBlockComponent', () => {
  let component: ForumBlockComponent;
  let fixture: ComponentFixture<ForumBlockComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ForumBlockComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ForumBlockComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
