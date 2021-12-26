import {ComponentFixture, TestBed} from '@angular/core/testing';

import {BaseWithPaginationAndFilterComponent} from './base-with-pagination-and-filter.component';

describe('AbstractPaginationComponentComponent', () => {
  let component: BaseWithPaginationAndFilterComponent<any, any>;
  let fixture: ComponentFixture<BaseWithPaginationAndFilterComponent<any, any>>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [BaseWithPaginationAndFilterComponent]
    })
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(BaseWithPaginationAndFilterComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
