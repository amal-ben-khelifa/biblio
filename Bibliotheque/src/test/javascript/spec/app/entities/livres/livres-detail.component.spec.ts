import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { BibliothequeTestModule } from '../../../test.module';
import { LivresDetailComponent } from 'app/entities/livres/livres-detail.component';
import { Livres } from 'app/shared/model/livres.model';

describe('Component Tests', () => {
  describe('Livres Management Detail Component', () => {
    let comp: LivresDetailComponent;
    let fixture: ComponentFixture<LivresDetailComponent>;
    const route = ({ data: of({ livres: new Livres(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BibliothequeTestModule],
        declarations: [LivresDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(LivresDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(LivresDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load livres on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.livres).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
