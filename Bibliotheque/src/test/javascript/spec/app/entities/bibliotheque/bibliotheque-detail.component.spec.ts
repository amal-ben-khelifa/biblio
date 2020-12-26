import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { BibliothequeTestModule } from '../../../test.module';
import { BibliothequeDetailComponent } from 'app/entities/bibliotheque/bibliotheque-detail.component';
import { Bibliotheque } from 'app/shared/model/bibliotheque.model';

describe('Component Tests', () => {
  describe('Bibliotheque Management Detail Component', () => {
    let comp: BibliothequeDetailComponent;
    let fixture: ComponentFixture<BibliothequeDetailComponent>;
    const route = ({ data: of({ bibliotheque: new Bibliotheque(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BibliothequeTestModule],
        declarations: [BibliothequeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(BibliothequeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(BibliothequeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load bibliotheque on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.bibliotheque).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
