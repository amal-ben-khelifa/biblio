import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'bibliotheque',
        loadChildren: () => import('./bibliotheque/bibliotheque.module').then(m => m.BibliothequeBibliothequeModule),
      },
      {
        path: 'livres',
        loadChildren: () => import('./livres/livres.module').then(m => m.BibliothequeLivresModule),
      },
      {
        path: 'roman',
        loadChildren: () => import('./roman/roman.module').then(m => m.BibliothequeRomanModule),
      },
      {
        path: 'auteurs',
        loadChildren: () => import('./auteurs/auteurs.module').then(m => m.BibliothequeAuteursModule),
      },
      {
        path: 'jeux-educatif',
        loadChildren: () => import('./jeux-educatif/jeux-educatif.module').then(m => m.BibliothequeJeuxEducatifModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class BibliothequeEntityModule {}
