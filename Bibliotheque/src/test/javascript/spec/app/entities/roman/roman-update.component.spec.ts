import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { BibliothequeTestModule } from '../../../test.module';
import { RomanUpdateComponent } from 'app/entities/roman/roman-update.component';
import { RomanService } from 'app/entities/roman/roman.service';
import { Roman } from 'app/shared/model/roman.model';

describe('Component Tests', () => {
  describe('Roman Management Update Component', () => {
    let comp: RomanUpdateComponent;
    let fixture: ComponentFixture<RomanUpdateComponent>;
    let service: RomanService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BibliothequeTestModule],
        declarations: [RomanUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(RomanUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(RomanUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(RomanService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Roman(123);
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
        const entity = new Roman();
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
