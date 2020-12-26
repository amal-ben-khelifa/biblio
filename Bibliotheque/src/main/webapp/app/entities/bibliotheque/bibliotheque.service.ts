import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IBibliotheque } from 'app/shared/model/bibliotheque.model';

type EntityResponseType = HttpResponse<IBibliotheque>;
type EntityArrayResponseType = HttpResponse<IBibliotheque[]>;

@Injectable({ providedIn: 'root' })
export class BibliothequeService {
  public resourceUrl = SERVER_API_URL + 'api/bibliotheques';

  constructor(protected http: HttpClient) {}

  create(bibliotheque: IBibliotheque): Observable<EntityResponseType> {
    return this.http.post<IBibliotheque>(this.resourceUrl, bibliotheque, { observe: 'response' });
  }

  update(bibliotheque: IBibliotheque): Observable<EntityResponseType> {
    return this.http.put<IBibliotheque>(this.resourceUrl, bibliotheque, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IBibliotheque>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IBibliotheque[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
