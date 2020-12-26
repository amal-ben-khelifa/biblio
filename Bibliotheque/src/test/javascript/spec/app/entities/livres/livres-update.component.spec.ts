import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { BibliothequeTestModule } from '../../../test.module';
import { LivresUpdateComponent } from 'app/entities/livres/livres-update.component';
import { LivresService } from 'app/entities/livres/livres.service';
import { Livres } from 'app/shared/model/livres.model';

describe('Component Tests', () => {
  describe('Livres Management Update Component', () => {
    let comp: LivresUpdateComponent;
    let fixture: ComponentFixture<LivresUpdateComponent>;
    let service: LivresService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BibliothequeTestModule],
        declarations: [LivresUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(LivresUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(LivresUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(LivresService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Livres(123);
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
        const entity = new Livres();
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
