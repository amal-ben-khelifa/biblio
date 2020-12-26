import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { BibliothequeTestModule } from '../../../test.module';
import { JeuxEducatifComponent } from 'app/entities/jeux-educatif/jeux-educatif.component';
import { JeuxEducatifService } from 'app/entities/jeux-educatif/jeux-educatif.service';
import { JeuxEducatif } from 'app/shared/model/jeux-educatif.model';

describe('Component Tests', () => {
  describe('JeuxEducatif Management Component', () => {
    let comp: JeuxEducatifComponent;
    let fixture: ComponentFixture<JeuxEducatifComponent>;
    let service: JeuxEducatifService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BibliothequeTestModule],
        declarations: [JeuxEducatifComponent],
      })
        .overrideTemplate(JeuxEducatifComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(JeuxEducatifComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(JeuxEducatifService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new JeuxEducatif(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.jeuxEducatifs && comp.jeuxEducatifs[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
