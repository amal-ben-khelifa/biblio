import { Moment } from 'moment';
import { ILivres } from 'app/shared/model/livres.model';

export interface IAuteurs {
  id?: number;
  nom?: string;
  prenom?: string;
  datedenaissance?: Moment;
  livre?: ILivres;
}

export class Auteurs implements IAuteurs {
  constructor(public id?: number, public nom?: string, public prenom?: string, public datedenaissance?: Moment, public livre?: ILivres) {}
}
