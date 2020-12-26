import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IBibliotheque, Bibliotheque } from 'app/shared/model/bibliotheque.model';
import { BibliothequeService } from './bibliotheque.service';
import { ILivres } from 'app/shared/model/livres.model';
import { LivresService } from 'app/entities/livres/livres.service';
import { IJeuxEducatif } from 'app/shared/model/jeux-educatif.model';
import { JeuxEducatifService } from 'app/entities/jeux-educatif/jeux-educatif.service';

type SelectableEntity = ILivres | IJeuxEducatif;

@Component({
  selector: 'jhi-bibliotheque-update',
  templateUrl: './bibliotheque-update.component.html',
})
export class BibliothequeUpdateComponent implements OnInit {
  isSaving = false;
  livres: ILivres[] = [];
  jeuxeducatifs: IJeuxEducatif[] = [];

  editForm = this.fb.group({
    id: [],
    nomBiblio: [],
    adresse: [],
    livre: [],
    jeuxEducatif: [],
  });

  constructor(
    protected bibliothequeService: BibliothequeService,
    protected livresService: LivresService,
    protected jeuxEducatifService: JeuxEducatifService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ bibliotheque }) => {
      this.updateForm(bibliotheque);

      this.livresService.query().subscribe((res: HttpResponse<ILivres[]>) => (this.livres = res.body || []));

      this.jeuxEducatifService.query().subscribe((res: HttpResponse<IJeuxEducatif[]>) => (this.jeuxeducatifs = res.body || []));
    });
  }

  updateForm(bibliotheque: IBibliotheque): void {
    this.editForm.patchValue({
      id: bibliotheque.id,
      nomBiblio: bibliotheque.nomBiblio,
      adresse: bibliotheque.adresse,
      livre: bibliotheque.livre,
      jeuxEducatif: bibliotheque.jeuxEducatif,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const bibliotheque = this.createFromForm();
    if (bibliotheque.id !== undefined) {
      this.subscribeToSaveResponse(this.bibliothequeService.update(bibliotheque));
    } else {
      this.subscribeToSaveResponse(this.bibliothequeService.create(bibliotheque));
    }
  }

  private createFromForm(): IBibliotheque {
    return {
      ...new Bibliotheque(),
      id: this.editForm.get(['id'])!.value,
      nomBiblio: this.editForm.get(['nomBiblio'])!.value,
      adresse: this.editForm.get(['adresse'])!.value,
      livre: this.editForm.get(['livre'])!.value,
      jeuxEducatif: this.editForm.get(['jeuxEducatif'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IBibliotheque>>): void {
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

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
