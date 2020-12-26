import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IJeuxEducatif } from 'app/shared/model/jeux-educatif.model';
import { JeuxEducatifService } from './jeux-educatif.service';
import { JeuxEducatifDeleteDialogComponent } from './jeux-educatif-delete-dialog.component';

@Component({
  selector: 'jhi-jeux-educatif',
  templateUrl: './jeux-educatif.component.html',
})
export class JeuxEducatifComponent implements OnInit, OnDestroy {
  jeuxEducatifs?: IJeuxEducatif[];
  eventSubscriber?: Subscription;

  constructor(
    protected jeuxEducatifService: JeuxEducatifService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.jeuxEducatifService.query().subscribe((res: HttpResponse<IJeuxEducatif[]>) => (this.jeuxEducatifs = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInJeuxEducatifs();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IJeuxEducatif): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInJeuxEducatifs(): void {
    this.eventSubscriber = this.eventManager.subscribe('jeuxEducatifListModification', () => this.loadAll());
  }

  delete(jeuxEducatif: IJeuxEducatif): void {
    const modalRef = this.modalService.open(JeuxEducatifDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.jeuxEducatif = jeuxEducatif;
  }
}
