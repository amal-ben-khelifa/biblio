import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAuteurs } from 'app/shared/model/auteurs.model';
import { AuteursService } from './auteurs.service';

@Component({
  templateUrl: './auteurs-delete-dialog.component.html',
})
export class AuteursDeleteDialogComponent {
  auteurs?: IAuteurs;

  constructor(protected auteursService: AuteursService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.auteursService.delete(id).subscribe(() => {
      this.eventManager.broadcast('auteursListModification');
      this.activeModal.close();
    });
  }
}
