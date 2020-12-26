import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { BibliothequeTestModule } from '../../../test.module';
import { AuteursDetailComponent } from 'app/entities/auteurs/auteurs-detail.component';
import { Auteurs } from 'app/shared/model/auteurs.model';

describe('Component Tests', () => {
  describe('Auteurs Management Detail Component', () => {
    let comp: AuteursDetailComponent;
    let fixture: ComponentFixture<AuteursDetailComponent>;
    const route = ({ data: of({ auteurs: new Auteurs(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BibliothequeTestModule],
        declarations: [AuteursDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(AuteursDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AuteursDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load auteurs on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.auteurs).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
