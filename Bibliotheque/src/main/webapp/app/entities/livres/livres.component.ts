import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ILivres } from 'app/shared/model/livres.model';
import { LivresService } from './livres.service';
import { LivresDeleteDialogComponent } from './livres-delete-dialog.component';

@Component({
  selector: 'jhi-livres',
  templateUrl: './livres.component.html',
})
export class LivresComponent implements OnInit, OnDestroy {
  livres?: ILivres[];
  eventSubscriber?: Subscription;

  constructor(protected livresService: LivresService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.livresService.query().subscribe((res: HttpResponse<ILivres[]>) => (this.livres = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInLivres();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ILivres): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInLivres(): void {
    this.eventSubscriber = this.eventManager.subscribe('livresListModification', () => this.loadAll());
  }

  delete(livres: ILivres): void {
    const modalRef = this.modalService.open(LivresDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.livres = livres;
  }
}
