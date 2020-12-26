import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BibliothequeSharedModule } from 'app/shared/shared.module';
import { BibliothequeComponent } from './bibliotheque.component';
import { BibliothequeDetailComponent } from './bibliotheque-detail.component';
import { BibliothequeUpdateComponent } from './bibliotheque-update.component';
import { BibliothequeDeleteDialogComponent } from './bibliotheque-delete-dialog.component';
import { bibliothequeRoute } from './bibliotheque.route';

@NgModule({
  imports: [BibliothequeSharedModule, RouterModule.forChild(bibliothequeRoute)],
  declarations: [BibliothequeComponent, BibliothequeDetailComponent, BibliothequeUpdateComponent, BibliothequeDeleteDialogComponent],
  entryComponents: [BibliothequeDeleteDialogComponent],
})
export class BibliothequeBibliothequeModule {}
