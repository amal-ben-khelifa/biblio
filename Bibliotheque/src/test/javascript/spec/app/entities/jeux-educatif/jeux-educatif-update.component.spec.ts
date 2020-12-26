import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { BibliothequeTestModule } from '../../../test.module';
import { JeuxEducatifUpdateComponent } from 'app/entities/jeux-educatif/jeux-educatif-update.component';
import { JeuxEducatifService } from 'app/entities/jeux-educatif/jeux-educatif.service';
import { JeuxEducatif } from 'app/shared/model/jeux-educatif.model';

describe('Component Tests', () => {
  describe('JeuxEducatif Management Update Component', () => {
    let comp: JeuxEducatifUpdateComponent;
    let fixture: ComponentFixture<JeuxEducatifUpdateComponent>;
    let service: JeuxEducatifService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BibliothequeTestModule],
        declarations: [JeuxEducatifUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(JeuxEducatifUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(JeuxEducatifUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(JeuxEducatifService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new JeuxEducatif(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new JeuxEducatif();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
