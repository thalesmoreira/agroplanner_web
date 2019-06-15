import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IPropriedadeCaracteristica } from 'app/shared/model/propriedade-caracteristica.model';

type EntityResponseType = HttpResponse<IPropriedadeCaracteristica>;
type EntityArrayResponseType = HttpResponse<IPropriedadeCaracteristica[]>;

@Injectable({ providedIn: 'root' })
export class PropriedadeCaracteristicaService {
  public resourceUrl = SERVER_API_URL + 'api/propriedade-caracteristicas';

  constructor(protected http: HttpClient) {}

  create(propriedade_caracteristica: IPropriedadeCaracteristica): Observable<EntityResponseType> {
    return this.http.post<IPropriedadeCaracteristica>(this.resourceUrl, propriedade_caracteristica, { observe: 'response' });
  }

  update(propriedade_caracteristica: IPropriedadeCaracteristica): Observable<EntityResponseType> {
    return this.http.put<IPropriedadeCaracteristica>(this.resourceUrl, propriedade_caracteristica, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPropriedadeCaracteristica>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPropriedadeCaracteristica[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
