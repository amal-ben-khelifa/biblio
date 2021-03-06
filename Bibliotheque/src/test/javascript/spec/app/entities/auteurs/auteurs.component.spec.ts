import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { BibliothequeTestModule } from '../../../test.module';
import { AuteursComponent } from 'app/entities/auteurs/auteurs.component';
import { AuteursService } from 'app/entities/auteurs/auteurs.service';
import { Auteurs } from 'app/shared/model/auteurs.model';

describe('Component Tests', () => {
  describe('Auteurs Management Component', () => {
    let comp: AuteursComponent;
    let fixture: ComponentFixture<AuteursComponent>;
    let service: AuteursService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BibliothequeTestModule],
        declarations: [AuteursComponent],
      })
        .overrideTemplate(AuteursComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AuteursComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AuteursService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Auteurs(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.auteurs && comp.auteurs[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
