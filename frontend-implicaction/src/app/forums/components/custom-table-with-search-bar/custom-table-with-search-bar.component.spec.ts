import {ComponentFixture, TestBed} from '@angular/core/testing';

import {CustomTableWithSearchBarComponent} from './custom-table-with-search-bar.component';

describe('CustomTableWithSearchBarComponent', () => {
  let component: CustomTableWithSearchBarComponent;
  let fixture: ComponentFixture<CustomTableWithSearchBarComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [CustomTableWithSearchBarComponent]
    })
      .compileComponents();

    fixture = TestBed.createComponent(CustomTableWithSearchBarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
