import {ComponentFixture, TestBed} from '@angular/core/testing';

import {BaseWithPaginationComponent} from './base-with-pagination.component';

describe('AbstractPaginationComponentComponent', () => {
  let component: BaseWithPaginationComponent<any>;
  let fixture: ComponentFixture<BaseWithPaginationComponent<any>>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [BaseWithPaginationComponent]
    })
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(BaseWithPaginationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
