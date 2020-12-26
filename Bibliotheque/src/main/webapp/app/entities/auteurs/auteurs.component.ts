import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IAuteurs } from 'app/shared/model/auteurs.model';
import { AuteursService } from './auteurs.service';
import { AuteursDeleteDialogComponent } from './auteurs-delete-dialog.component';

@Component({
  selector: 'jhi-auteurs',
  templateUrl: './auteurs.component.html',
})
export class AuteursComponent implements OnInit, OnDestroy {
  auteurs?: IAuteurs[];
  eventSubscriber?: Subscription;

  constructor(protected auteursService: AuteursService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.auteursService.query().subscribe((res: HttpResponse<IAuteurs[]>) => (this.auteurs = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInAuteurs();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IAuteurs): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInAuteurs(): void {
    this.eventSubscriber = this.eventManager.subscribe('auteursListModification', () => this.loadAll());
  }

  delete(auteurs: IAuteurs): void {
    const modalRef = this.modalService.open(AuteursDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.auteurs = auteurs;
  }
}
