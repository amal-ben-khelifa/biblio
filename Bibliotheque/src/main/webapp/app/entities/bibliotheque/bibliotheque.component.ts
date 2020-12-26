import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IBibliotheque } from 'app/shared/model/bibliotheque.model';
import { BibliothequeService } from './bibliotheque.service';
import { BibliothequeDeleteDialogComponent } from './bibliotheque-delete-dialog.component';

@Component({
  selector: 'jhi-bibliotheque',
  templateUrl: './bibliotheque.component.html',
})
export class BibliothequeComponent implements OnInit, OnDestroy {
  bibliotheques?: IBibliotheque[];
  eventSubscriber?: Subscription;

  constructor(
    protected bibliothequeService: BibliothequeService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.bibliothequeService.query().subscribe((res: HttpResponse<IBibliotheque[]>) => (this.bibliotheques = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInBibliotheques();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IBibliotheque): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInBibliotheques(): void {
    this.eventSubscriber = this.eventManager.subscribe('bibliothequeListModification', () => this.loadAll());
  }

  delete(bibliotheque: IBibliotheque): void {
    const modalRef = this.modalService.open(BibliothequeDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.bibliotheque = bibliotheque;
  }
}
