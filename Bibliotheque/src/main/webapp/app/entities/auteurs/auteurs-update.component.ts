import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IAuteurs, Auteurs } from 'app/shared/model/auteurs.model';
import { AuteursService } from './auteurs.service';

@Component({
  selector: 'jhi-auteurs-update',
  templateUrl: './auteurs-update.component.html',
})
export class AuteursUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    nom: [],
    prenom: [],
    datedenaissance: [],
  });

  constructor(protected auteursService: AuteursService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ auteurs }) => {
      if (!auteurs.id) {
        const today = moment().startOf('day');
        auteurs.datedenaissance = today;
      }

      this.updateForm(auteurs);
    });
  }

  updateForm(auteurs: IAuteurs): void {
    this.editForm.patchValue({
      id: auteurs.id,
      nom: auteurs.nom,
      prenom: auteurs.prenom,
      datedenaissance: auteurs.datedenaissance ? auteurs.datedenaissance.format(DATE_TIME_FORMAT) : null,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const auteurs = this.createFromForm();
    if (auteurs.id !== undefined) {
      this.subscribeToSaveResponse(this.auteursService.update(auteurs));
    } else {
      this.subscribeToSaveResponse(this.auteursService.create(auteurs));
    }
  }

  private createFromForm(): IAuteurs {
    return {
      ...new Auteurs(),
      id: this.editForm.get(['id'])!.value,
      nom: this.editForm.get(['nom'])!.value,
      prenom: this.editForm.get(['prenom'])!.value,
      datedenaissance: this.editForm.get(['datedenaissance'])!.value
        ? moment(this.editForm.get(['datedenaissance'])!.value, DATE_TIME_FORMAT)
        : undefined,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAuteurs>>): void {
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
