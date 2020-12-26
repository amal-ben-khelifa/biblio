import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { BibliothequeTestModule } from '../../../test.module';
import { AuteursUpdateComponent } from 'app/entities/auteurs/auteurs-update.component';
import { AuteursService } from 'app/entities/auteurs/auteurs.service';
import { Auteurs } from 'app/shared/model/auteurs.model';

describe('Component Tests', () => {
  describe('Auteurs Management Update Component', () => {
    let comp: AuteursUpdateComponent;
    let fixture: ComponentFixture<AuteursUpdateComponent>;
    let service: AuteursService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BibliothequeTestModule],
        declarations: [AuteursUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(AuteursUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AuteursUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AuteursService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Auteurs(123);
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
        const entity = new Auteurs();
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
