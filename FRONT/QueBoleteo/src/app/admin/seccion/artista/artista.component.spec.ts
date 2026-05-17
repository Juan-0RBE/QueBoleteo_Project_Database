  import { ComponentFixture, TestBed } from '@angular/core/testing';

  import { ArtistasComponent } from './artista.component';

  describe('ArtistasComponent', () => {
    let component: ArtistasComponent;
    let fixture: ComponentFixture<ArtistasComponent>;

    beforeEach(async () => {
      await TestBed.configureTestingModule({
        imports: [ArtistasComponent],
      }).compileComponents();

      fixture = TestBed.createComponent(ArtistasComponent);
      component = fixture.componentInstance;
      await fixture.whenStable();
    });

    it('should create', () => {
      expect(component).toBeTruthy();
    });
  });
