import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { BibliothequeTestModule } from '../../../test.module';
import { RomanDetailComponent } from 'app/entities/roman/roman-detail.component';
import { Roman } from 'app/shared/model/roman.model';

describe('Component Tests', () => {
  describe('Roman Management Detail Component', () => {
    let comp: RomanDetailComponent;
    let fixture: ComponentFixture<RomanDetailComponent>;
    const route = ({ data: of({ roman: new Roman(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BibliothequeTestModule],
        declarations: [RomanDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(RomanDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(RomanDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load roman on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.roman).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
