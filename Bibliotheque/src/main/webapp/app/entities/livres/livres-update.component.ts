import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { ILivres, Livres } from 'app/shared/model/livres.model';
import { LivresService } from './livres.service';
import { IAuteurs } from 'app/shared/model/auteurs.model';
import { AuteursService } from 'app/entities/auteurs/auteurs.service';

@Component({
  selector: 'jhi-livres-update',
  templateUrl: './livres-update.component.html',
})
export class LivresUpdateComponent implements OnInit {
  isSaving = false;
  auteurs: IAuteurs[] = [];

  editForm = this.fb.group({
    id: [],
    nomLivre: [],
    genre: [],
    nombredepages: [],
    langues: [],
    prix: [],
    auteurs: [],
  });

  constructor(
    protected livresService: LivresService,
    protected auteursService: AuteursService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ livres }) => {
      this.updateForm(livres);

      this.auteursService
        .query({ filter: 'livre-is-null' })
        .pipe(
          map((res: HttpResponse<IAuteurs[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IAuteurs[]) => {
          if (!livres.auteurs || !livres.auteurs.id) {
            this.auteurs = resBody;
          } else {
            this.auteursService
              .find(livres.auteurs.id)
              .pipe(
                map((subRes: HttpResponse<IAuteurs>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IAuteurs[]) => (this.auteurs = concatRes));
          }
        });
    });
  }

  updateForm(livres: ILivres): void {
    this.editForm.patchValue({
      id: livres.id,
      nomLivre: livres.nomLivre,
      genre: livres.genre,
      nombredepages: livres.nombredepages,
      langues: livres.langues,
      prix: livres.prix,
      auteurs: livres.auteurs,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const livres = this.createFromForm();
    if (livres.id !== undefined) {
      this.subscribeToSaveResponse(this.livresService.update(livres));
    } else {
      this.subscribeToSaveResponse(this.livresService.create(livres));
    }
  }

  private createFromForm(): ILivres {
    return {
      ...new Livres(),
      id: this.editForm.get(['id'])!.value,
      nomLivre: this.editForm.get(['nomLivre'])!.value,
      genre: this.editForm.get(['genre'])!.value,
      nombredepages: this.editForm.get(['nombredepages'])!.value,
      langues: this.editForm.get(['langues'])!.value,
      prix: this.editForm.get(['prix'])!.value,
      auteurs: this.editForm.get(['auteurs'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ILivres>>): void {
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

  trackById(index: number, item: IAuteurs): any {
    return item.id;
  }
}
