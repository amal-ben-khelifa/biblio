import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IJeuxEducatif, JeuxEducatif } from 'app/shared/model/jeux-educatif.model';
import { JeuxEducatifService } from './jeux-educatif.service';

@Component({
  selector: 'jhi-jeux-educatif-update',
  templateUrl: './jeux-educatif-update.component.html',
})
export class JeuxEducatifUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    nom: [],
    prix: [],
  });

  constructor(protected jeuxEducatifService: JeuxEducatifService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ jeuxEducatif }) => {
      this.updateForm(jeuxEducatif);
    });
  }

  updateForm(jeuxEducatif: IJeuxEducatif): void {
    this.editForm.patchValue({
      id: jeuxEducatif.id,
      nom: jeuxEducatif.nom,
      prix: jeuxEducatif.prix,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const jeuxEducatif = this.createFromForm();
    if (jeuxEducatif.id !== undefined) {
      this.subscribeToSaveResponse(this.jeuxEducatifService.update(jeuxEducatif));
    } else {
      this.subscribeToSaveResponse(this.jeuxEducatifService.create(jeuxEducatif));
    }
  }

  private createFromForm(): IJeuxEducatif {
    return {
      ...new JeuxEducatif(),
      id: this.editForm.get(['id'])!.value,
      nom: this.editForm.get(['nom'])!.value,
      prix: this.editForm.get(['prix'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IJeuxEducatif>>): void {
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
