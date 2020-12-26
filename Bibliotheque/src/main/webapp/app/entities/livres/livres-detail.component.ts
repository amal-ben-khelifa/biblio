import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ILivres } from 'app/shared/model/livres.model';

@Component({
  selector: 'jhi-livres-detail',
  templateUrl: './livres-detail.component.html',
})
export class LivresDetailComponent implements OnInit {
  livres: ILivres | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ livres }) => (this.livres = livres));
  }

  previousState(): void {
    window.history.back();
  }
}
