import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { BibliothequeTestModule } from '../../../test.module';
import { JeuxEducatifDetailComponent } from 'app/entities/jeux-educatif/jeux-educatif-detail.component';
import { JeuxEducatif } from 'app/shared/model/jeux-educatif.model';

describe('Component Tests', () => {
  describe('JeuxEducatif Management Detail Component', () => {
    let comp: JeuxEducatifDetailComponent;
    let fixture: ComponentFixture<JeuxEducatifDetailComponent>;
    const route = ({ data: of({ jeuxEducatif: new JeuxEducatif(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BibliothequeTestModule],
        declarations: [JeuxEducatifDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(JeuxEducatifDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(JeuxEducatifDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load jeuxEducatif on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.jeuxEducatif).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
