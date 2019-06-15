import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IPropriedadeFoto } from 'app/shared/model/propriedade-foto.model';

type EntityResponseType = HttpResponse<IPropriedadeFoto>;
type EntityArrayResponseType = HttpResponse<IPropriedadeFoto[]>;

@Injectable({ providedIn: 'root' })
export class PropriedadeFotoService {
  public resourceUrl = SERVER_API_URL + 'api/propriedade-fotos';

  constructor(protected http: HttpClient) {}

  create(propriedadeFoto: IPropriedadeFoto): Observable<EntityResponseType> {
    return this.http.post<IPropriedadeFoto>(this.resourceUrl, propriedadeFoto, { observe: 'response' });
  }

  update(propriedadeFoto: IPropriedadeFoto): Observable<EntityResponseType> {
    return this.http.put<IPropriedadeFoto>(this.resourceUrl, propriedadeFoto, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPropriedadeFoto>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPropriedadeFoto[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
