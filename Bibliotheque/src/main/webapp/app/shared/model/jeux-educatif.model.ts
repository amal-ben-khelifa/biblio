import { IBibliotheque } from 'app/shared/model/bibliotheque.model';

export interface IJeuxEducatif {
  id?: number;
  nom?: string;
  prix?: string;
  nomBiblios?: IBibliotheque[];
}

export class JeuxEducatif implements IJeuxEducatif {
  constructor(public id?: number, public nom?: string, public prix?: string, public nomBiblios?: IBibliotheque[]) {}
}
