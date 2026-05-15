import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ConciertosComponent } from './concierto.component';

describe('ConciertosComponent', () => {
  let component: ConciertosComponent;
  let fixture: ComponentFixture<ConciertosComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ConciertosComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(ConciertosComponent);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
