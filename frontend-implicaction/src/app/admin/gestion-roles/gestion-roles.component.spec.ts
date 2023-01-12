import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GestionRolesComponent } from './gestion-roles.component';

describe('GestionRolesComponent', () => {
  let component: GestionRolesComponent;
  let fixture: ComponentFixture<GestionRolesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ GestionRolesComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(GestionRolesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
