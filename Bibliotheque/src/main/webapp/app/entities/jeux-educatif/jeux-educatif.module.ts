import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BibliothequeSharedModule } from 'app/shared/shared.module';
import { JeuxEducatifComponent } from './jeux-educatif.component';
import { JeuxEducatifDetailComponent } from './jeux-educatif-detail.component';
import { JeuxEducatifUpdateComponent } from './jeux-educatif-update.component';
import { JeuxEducatifDeleteDialogComponent } from './jeux-educatif-delete-dialog.component';
import { jeuxEducatifRoute } from './jeux-educatif.route';

@NgModule({
  imports: [BibliothequeSharedModule, RouterModule.forChild(jeuxEducatifRoute)],
  declarations: [JeuxEducatifComponent, JeuxEducatifDetailComponent, JeuxEducatifUpdateComponent, JeuxEducatifDeleteDialogComponent],
  entryComponents: [JeuxEducatifDeleteDialogComponent],
})
export class BibliothequeJeuxEducatifModule {}
