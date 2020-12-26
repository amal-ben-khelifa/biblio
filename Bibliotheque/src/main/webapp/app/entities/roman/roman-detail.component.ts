import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IRoman } from 'app/shared/model/roman.model';

@Component({
  selector: 'jhi-roman-detail',
  templateUrl: './roman-detail.component.html',
})
export class RomanDetailComponent implements OnInit {
  roman: IRoman | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ roman }) => (this.roman = roman));
  }

  previousState(): void {
    window.history.back();
  }
}
