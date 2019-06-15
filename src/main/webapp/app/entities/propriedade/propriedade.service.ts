import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IPropriedade } from 'app/shared/model/propriedade.model';

type EntityResponseType = HttpResponse<IPropriedade>;
type EntityArrayResponseType = HttpResponse<IPropriedade[]>;

@Injectable({ providedIn: 'root' })
export class PropriedadeService {
  public resourceUrl = SERVER_API_URL + 'api/propriedades';

  constructor(protected http: HttpClient) {}

  create(propriedade: IPropriedade): Observable<EntityResponseType> {
    return this.http.post<IPropriedade>(this.resourceUrl, propriedade, { observe: 'response' });
  }

  update(propriedade: IPropriedade): Observable<EntityResponseType> {
    return this.http.put<IPropriedade>(this.resourceUrl, propriedade, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPropriedade>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPropriedade[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
