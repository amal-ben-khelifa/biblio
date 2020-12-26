import { IAuteurs } from 'app/shared/model/auteurs.model';
import { IBibliotheque } from 'app/shared/model/bibliotheque.model';

export interface ILivres {
  id?: number;
  nomLivre?: string;
  genre?: string;
  nombredepages?: string;
  langues?: string;
  prix?: string;
  auteurs?: IAuteurs;
  nomBiblios?: IBibliotheque[];
}

export class Livres implements ILivres {
  constructor(
    public id?: number,
    public nomLivre?: string,
    public genre?: string,
    public nombredepages?: string,
    public langues?: string,
    public prix?: string,
    public auteurs?: IAuteurs,
    public nomBiblios?: IBibliotheque[]
  ) {}
}
