import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IRoman } from 'app/shared/model/roman.model';

type EntityResponseType = HttpResponse<IRoman>;
type EntityArrayResponseType = HttpResponse<IRoman[]>;

@Injectable({ providedIn: 'root' })
export class RomanService {
  public resourceUrl = SERVER_API_URL + 'api/romen';

  constructor(protected http: HttpClient) {}

  create(roman: IRoman): Observable<EntityResponseType> {
    return this.http.post<IRoman>(this.resourceUrl, roman, { observe: 'response' });
  }

  update(roman: IRoman): Observable<EntityResponseType> {
    return this.http.put<IRoman>(this.resourceUrl, roman, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IRoman>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IRoman[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
