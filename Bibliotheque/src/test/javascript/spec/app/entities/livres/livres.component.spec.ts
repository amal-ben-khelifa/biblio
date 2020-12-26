import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { BibliothequeTestModule } from '../../../test.module';
import { LivresComponent } from 'app/entities/livres/livres.component';
import { LivresService } from 'app/entities/livres/livres.service';
import { Livres } from 'app/shared/model/livres.model';

describe('Component Tests', () => {
  describe('Livres Management Component', () => {
    let comp: LivresComponent;
    let fixture: ComponentFixture<LivresComponent>;
    let service: LivresService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BibliothequeTestModule],
        declarations: [LivresComponent],
      })
        .overrideTemplate(LivresComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(LivresComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(LivresService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Livres(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.livres && comp.livres[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
