import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { BibliothequeTestModule } from '../../../test.module';
import { BibliothequeComponent } from 'app/entities/bibliotheque/bibliotheque.component';
import { BibliothequeService } from 'app/entities/bibliotheque/bibliotheque.service';
import { Bibliotheque } from 'app/shared/model/bibliotheque.model';

describe('Component Tests', () => {
  describe('Bibliotheque Management Component', () => {
    let comp: BibliothequeComponent;
    let fixture: ComponentFixture<BibliothequeComponent>;
    let service: BibliothequeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BibliothequeTestModule],
        declarations: [BibliothequeComponent],
      })
        .overrideTemplate(BibliothequeComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(BibliothequeComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(BibliothequeService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Bibliotheque(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.bibliotheques && comp.bibliotheques[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
