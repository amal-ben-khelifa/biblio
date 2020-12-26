import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BibliothequeSharedModule } from 'app/shared/shared.module';
import { LivresComponent } from './livres.component';
import { LivresDetailComponent } from './livres-detail.component';
import { LivresUpdateComponent } from './livres-update.component';
import { LivresDeleteDialogComponent } from './livres-delete-dialog.component';
import { livresRoute } from './livres.route';

@NgModule({
  imports: [BibliothequeSharedModule, RouterModule.forChild(livresRoute)],
  declarations: [LivresComponent, LivresDetailComponent, LivresUpdateComponent, LivresDeleteDialogComponent],
  entryComponents: [LivresDeleteDialogComponent],
})
export class BibliothequeLivresModule {}
