import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IRoman } from 'app/shared/model/roman.model';
import { RomanService } from './roman.service';
import { RomanDeleteDialogComponent } from './roman-delete-dialog.component';

@Component({
  selector: 'jhi-roman',
  templateUrl: './roman.component.html',
})
export class RomanComponent implements OnInit, OnDestroy {
  romen?: IRoman[];
  eventSubscriber?: Subscription;

  constructor(protected romanService: RomanService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.romanService.query().subscribe((res: HttpResponse<IRoman[]>) => (this.romen = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInRomen();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IRoman): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInRomen(): void {
    this.eventSubscriber = this.eventManager.subscribe('romanListModification', () => this.loadAll());
  }

  delete(roman: IRoman): void {
    const modalRef = this.modalService.open(RomanDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.roman = roman;
  }
}
