import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BibliothequeSharedModule } from 'app/shared/shared.module';
import { AuteursComponent } from './auteurs.component';
import { AuteursDetailComponent } from './auteurs-detail.component';
import { AuteursUpdateComponent } from './auteurs-update.component';
import { AuteursDeleteDialogComponent } from './auteurs-delete-dialog.component';
import { auteursRoute } from './auteurs.route';

@NgModule({
  imports: [BibliothequeSharedModule, RouterModule.forChild(auteursRoute)],
  declarations: [AuteursComponent, AuteursDetailComponent, AuteursUpdateComponent, AuteursDeleteDialogComponent],
  entryComponents: [AuteursDeleteDialogComponent],
})
export class BibliothequeAuteursModule {}
