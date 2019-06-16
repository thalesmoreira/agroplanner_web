import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IPropriedadeContratada } from 'app/shared/model/propriedade-contratada.model';

type EntityResponseType = HttpResponse<IPropriedadeContratada>;
type EntityArrayResponseType = HttpResponse<IPropriedadeContratada[]>;

@Injectable({ providedIn: 'root' })
export class PropriedadeContratadaService {
  public resourceUrl = SERVER_API_URL + 'api/propriedade-contratadas';

  constructor(protected http: HttpClient) {}

  create(propriedadeContratada: IPropriedadeContratada): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(propriedadeContratada);
    return this.http
      .post<IPropriedadeContratada>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(propriedadeContratada: IPropriedadeContratada): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(propriedadeContratada);
    return this.http
      .put<IPropriedadeContratada>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IPropriedadeContratada>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IPropriedadeContratada[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(propriedadeContratada: IPropriedadeContratada): IPropriedadeContratada {
    const copy: IPropriedadeContratada = Object.assign({}, propriedadeContratada, {
      dataInicial:
        propriedadeContratada.dataInicial != null && propriedadeContratada.dataInicial.isValid()
          ? propriedadeContratada.dataInicial.toJSON()
          : null,
      dataFinal:
        propriedadeContratada.dataFinal != null && propriedadeContratada.dataFinal.isValid()
          ? propriedadeContratada.dataFinal.toJSON()
          : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dataInicial = res.body.dataInicial != null ? moment(res.body.dataInicial) : null;
      res.body.dataFinal = res.body.dataFinal != null ? moment(res.body.dataFinal) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((propriedadeContratada: IPropriedadeContratada) => {
        propriedadeContratada.dataInicial = propriedadeContratada.dataInicial != null ? moment(propriedadeContratada.dataInicial) : null;
        propriedadeContratada.dataFinal = propriedadeContratada.dataFinal != null ? moment(propriedadeContratada.dataFinal) : null;
      });
    }
    return res;
  }
}
