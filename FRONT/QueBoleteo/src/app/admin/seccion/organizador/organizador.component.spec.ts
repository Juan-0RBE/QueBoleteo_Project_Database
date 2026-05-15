import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OrganizadorComponent } from './organizador.component';

describe('OrganizadorComponent', () => {
  let component: OrganizadorComponent;
  let fixture: ComponentFixture<OrganizadorComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [OrganizadorComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(OrganizadorComponent);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
