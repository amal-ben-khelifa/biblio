import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { BibliothequeTestModule } from '../../../test.module';
import { RomanComponent } from 'app/entities/roman/roman.component';
import { RomanService } from 'app/entities/roman/roman.service';
import { Roman } from 'app/shared/model/roman.model';

describe('Component Tests', () => {
  describe('Roman Management Component', () => {
    let comp: RomanComponent;
    let fixture: ComponentFixture<RomanComponent>;
    let service: RomanService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BibliothequeTestModule],
        declarations: [RomanComponent],
      })
        .overrideTemplate(RomanComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(RomanComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(RomanService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Roman(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.romen && comp.romen[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
