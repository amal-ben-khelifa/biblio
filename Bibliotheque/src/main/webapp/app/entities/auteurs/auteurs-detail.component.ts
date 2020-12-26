import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAuteurs } from 'app/shared/model/auteurs.model';

@Component({
  selector: 'jhi-auteurs-detail',
  templateUrl: './auteurs-detail.component.html',
})
export class AuteursDetailComponent implements OnInit {
  auteurs: IAuteurs | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ auteurs }) => (this.auteurs = auteurs));
  }

  previousState(): void {
    window.history.back();
  }
}
