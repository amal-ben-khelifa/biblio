import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ILivres } from 'app/shared/model/livres.model';

type EntityResponseType = HttpResponse<ILivres>;
type EntityArrayResponseType = HttpResponse<ILivres[]>;

@Injectable({ providedIn: 'root' })
export class LivresService {
  public resourceUrl = SERVER_API_URL + 'api/livres';

  constructor(protected http: HttpClient) {}

  create(livres: ILivres): Observable<EntityResponseType> {
    return this.http.post<ILivres>(this.resourceUrl, livres, { observe: 'response' });
  }

  update(livres: ILivres): Observable<EntityResponseType> {
    return this.http.put<ILivres>(this.resourceUrl, livres, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ILivres>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ILivres[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
