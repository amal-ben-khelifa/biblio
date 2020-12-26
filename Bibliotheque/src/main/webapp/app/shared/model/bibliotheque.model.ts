import { ILivres } from 'app/shared/model/livres.model';
import { IJeuxEducatif } from 'app/shared/model/jeux-educatif.model';

export interface IBibliotheque {
  id?: number;
  nomBiblio?: string;
  adresse?: string;
  livre?: ILivres;
  jeuxEducatif?: IJeuxEducatif;
}

export class Bibliotheque implements IBibliotheque {
  constructor(
    public id?: number,
    public nomBiblio?: string,
    public adresse?: string,
    public livre?: ILivres,
    public jeuxEducatif?: IJeuxEducatif
  ) {}
}
