import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IJeuxEducatif } from 'app/shared/model/jeux-educatif.model';

type EntityResponseType = HttpResponse<IJeuxEducatif>;
type EntityArrayResponseType = HttpResponse<IJeuxEducatif[]>;

@Injectable({ providedIn: 'root' })
export class JeuxEducatifService {
  public resourceUrl = SERVER_API_URL + 'api/jeux-educatifs';

  constructor(protected http: HttpClient) {}

  create(jeuxEducatif: IJeuxEducatif): Observable<EntityResponseType> {
    return this.http.post<IJeuxEducatif>(this.resourceUrl, jeuxEducatif, { observe: 'response' });
  }

  update(jeuxEducatif: IJeuxEducatif): Observable<EntityResponseType> {
    return this.http.put<IJeuxEducatif>(this.resourceUrl, jeuxEducatif, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IJeuxEducatif>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IJeuxEducatif[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
