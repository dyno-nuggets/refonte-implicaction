import {ComponentFixture, TestBed} from '@angular/core/testing';

import {TableJobsComponent} from './table-offers.component';

describe('TableOffersComponent', () => {
  let component: TableJobsComponent;
  let fixture: ComponentFixture<TableJobsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [TableJobsComponent]
    })
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(TableJobsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
