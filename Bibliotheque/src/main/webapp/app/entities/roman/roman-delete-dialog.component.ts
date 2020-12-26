import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IRoman } from 'app/shared/model/roman.model';
import { RomanService } from './roman.service';

@Component({
  templateUrl: './roman-delete-dialog.component.html',
})
export class RomanDeleteDialogComponent {
  roman?: IRoman;

  constructor(protected romanService: RomanService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.romanService.delete(id).subscribe(() => {
      this.eventManager.broadcast('romanListModification');
      this.activeModal.close();
    });
  }
}
