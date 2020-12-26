import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ILivres } from 'app/shared/model/livres.model';
import { LivresService } from './livres.service';

@Component({
  templateUrl: './livres-delete-dialog.component.html',
})
export class LivresDeleteDialogComponent {
  livres?: ILivres;

  constructor(protected livresService: LivresService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.livresService.delete(id).subscribe(() => {
      this.eventManager.broadcast('livresListModification');
      this.activeModal.close();
    });
  }
}
