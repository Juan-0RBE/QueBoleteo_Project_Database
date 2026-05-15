import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DetalleConciertoComponent } from './detalle-concierto.component';

describe('DetalleConciertoComponent', () => {
  let component: DetalleConciertoComponent;
  let fixture: ComponentFixture<DetalleConciertoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DetalleConciertoComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(DetalleConciertoComponent);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
