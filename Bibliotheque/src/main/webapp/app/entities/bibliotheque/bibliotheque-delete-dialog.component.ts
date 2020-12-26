import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IBibliotheque } from 'app/shared/model/bibliotheque.model';
import { BibliothequeService } from './bibliotheque.service';

@Component({
  templateUrl: './bibliotheque-delete-dialog.component.html',
})
export class BibliothequeDeleteDialogComponent {
  bibliotheque?: IBibliotheque;

  constructor(
    protected bibliothequeService: BibliothequeService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.bibliothequeService.delete(id).subscribe(() => {
      this.eventManager.broadcast('bibliothequeListModification');
      this.activeModal.close();
    });
  }
}
