import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IAuteurs } from 'app/shared/model/auteurs.model';

type EntityResponseType = HttpResponse<IAuteurs>;
type EntityArrayResponseType = HttpResponse<IAuteurs[]>;

@Injectable({ providedIn: 'root' })
export class AuteursService {
  public resourceUrl = SERVER_API_URL + 'api/auteurs';

  constructor(protected http: HttpClient) {}

  create(auteurs: IAuteurs): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(auteurs);
    return this.http
      .post<IAuteurs>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(auteurs: IAuteurs): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(auteurs);
    return this.http
      .put<IAuteurs>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IAuteurs>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IAuteurs[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(auteurs: IAuteurs): IAuteurs {
    const copy: IAuteurs = Object.assign({}, auteurs, {
      datedenaissance: auteurs.datedenaissance && auteurs.datedenaissance.isValid() ? auteurs.datedenaissance.toJSON() : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.datedenaissance = res.body.datedenaissance ? moment(res.body.datedenaissance) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((auteurs: IAuteurs) => {
        auteurs.datedenaissance = auteurs.datedenaissance ? moment(auteurs.datedenaissance) : undefined;
      });
    }
    return res;
  }
}
