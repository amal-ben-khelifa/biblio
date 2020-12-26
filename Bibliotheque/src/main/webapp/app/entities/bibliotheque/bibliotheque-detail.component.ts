import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IBibliotheque } from 'app/shared/model/bibliotheque.model';

@Component({
  selector: 'jhi-bibliotheque-detail',
  templateUrl: './bibliotheque-detail.component.html',
})
export class BibliothequeDetailComponent implements OnInit {
  bibliotheque: IBibliotheque | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ bibliotheque }) => (this.bibliotheque = bibliotheque));
  }

  previousState(): void {
    window.history.back();
  }
}
