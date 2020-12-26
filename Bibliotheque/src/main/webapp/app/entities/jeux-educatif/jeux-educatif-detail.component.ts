import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IJeuxEducatif } from 'app/shared/model/jeux-educatif.model';

@Component({
  selector: 'jhi-jeux-educatif-detail',
  templateUrl: './jeux-educatif-detail.component.html',
})
export class JeuxEducatifDetailComponent implements OnInit {
  jeuxEducatif: IJeuxEducatif | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ jeuxEducatif }) => (this.jeuxEducatif = jeuxEducatif));
  }

  previousState(): void {
    window.history.back();
  }
}
