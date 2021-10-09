import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ContractTypeComponent } from './contract-type.component';

describe('ContractTypeComponent', () => {
  let component: ContractTypeComponent;
  let fixture: ComponentFixture<ContractTypeComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ContractTypeComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ContractTypeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
