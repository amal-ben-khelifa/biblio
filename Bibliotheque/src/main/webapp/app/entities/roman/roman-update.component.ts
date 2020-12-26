import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IRoman, Roman } from 'app/shared/model/roman.model';
import { RomanService } from './roman.service';

@Component({
  selector: 'jhi-roman-update',
  templateUrl: './roman-update.component.html',
})
export class RomanUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    streetAddress: [],
    postalCode: [],
    city: [],
    stateProvince: [],
  });

  constructor(protected romanService: RomanService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ roman }) => {
      this.updateForm(roman);
    });
  }

  updateForm(roman: IRoman): void {
    this.editForm.patchValue({
      id: roman.id,
      streetAddress: roman.streetAddress,
      postalCode: roman.postalCode,
      city: roman.city,
      stateProvince: roman.stateProvince,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const roman = this.createFromForm();
    if (roman.id !== undefined) {
      this.subscribeToSaveResponse(this.romanService.update(roman));
    } else {
      this.subscribeToSaveResponse(this.romanService.create(roman));
    }
  }

  private createFromForm(): IRoman {
    return {
      ...new Roman(),
      id: this.editForm.get(['id'])!.value,
      streetAddress: this.editForm.get(['streetAddress'])!.value,
      postalCode: this.editForm.get(['postalCode'])!.value,
      city: this.editForm.get(['city'])!.value,
      stateProvince: this.editForm.get(['stateProvince'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRoman>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }
}
