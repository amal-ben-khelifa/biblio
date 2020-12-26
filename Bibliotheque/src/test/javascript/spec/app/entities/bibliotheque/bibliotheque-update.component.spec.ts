import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { BibliothequeTestModule } from '../../../test.module';
import { BibliothequeUpdateComponent } from 'app/entities/bibliotheque/bibliotheque-update.component';
import { BibliothequeService } from 'app/entities/bibliotheque/bibliotheque.service';
import { Bibliotheque } from 'app/shared/model/bibliotheque.model';

describe('Component Tests', () => {
  describe('Bibliotheque Management Update Component', () => {
    let comp: BibliothequeUpdateComponent;
    let fixture: ComponentFixture<BibliothequeUpdateComponent>;
    let service: BibliothequeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BibliothequeTestModule],
        declarations: [BibliothequeUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(BibliothequeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(BibliothequeUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(BibliothequeService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Bibliotheque(123);
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
        const entity = new Bibliotheque();
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
