import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BibliothequeSharedModule } from 'app/shared/shared.module';
import { RomanComponent } from './roman.component';
import { RomanDetailComponent } from './roman-detail.component';
import { RomanUpdateComponent } from './roman-update.component';
import { RomanDeleteDialogComponent } from './roman-delete-dialog.component';
import { romanRoute } from './roman.route';

@NgModule({
  imports: [BibliothequeSharedModule, RouterModule.forChild(romanRoute)],
  declarations: [RomanComponent, RomanDetailComponent, RomanUpdateComponent, RomanDeleteDialogComponent],
  entryComponents: [RomanDeleteDialogComponent],
})
export class BibliothequeRomanModule {}
