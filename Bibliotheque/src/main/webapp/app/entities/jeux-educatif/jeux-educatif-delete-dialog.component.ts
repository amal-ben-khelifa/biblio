import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IJeuxEducatif } from 'app/shared/model/jeux-educatif.model';
import { JeuxEducatifService } from './jeux-educatif.service';

@Component({
  templateUrl: './jeux-educatif-delete-dialog.component.html',
})
export class JeuxEducatifDeleteDialogComponent {
  jeuxEducatif?: IJeuxEducatif;

  constructor(
    protected jeuxEducatifService: JeuxEducatifService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.jeuxEducatifService.delete(id).subscribe(() => {
      this.eventManager.broadcast('jeuxEducatifListModification');
      this.activeModal.close();
    });
  }
}
