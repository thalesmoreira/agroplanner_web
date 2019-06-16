/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { PropriedadeContratadaService } from 'app/entities/propriedade-contratada/propriedade-contratada.service';
import { IPropriedadeContratada, PropriedadeContratada, FormaDePagamento } from 'app/shared/model/propriedade-contratada.model';

describe('Service Tests', () => {
  describe('PropriedadeContratada Service', () => {
    let injector: TestBed;
    let service: PropriedadeContratadaService;
    let httpMock: HttpTestingController;
    let elemDefault: IPropriedadeContratada;
    let expectedResult;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(PropriedadeContratadaService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new PropriedadeContratada(0, currentDate, currentDate, 0, 0, FormaDePagamento.DEBITO, 0, 0);
    });

    describe('Service methods', () => {
      it('should find an element', async () => {
        const returnedFromService = Object.assign(
          {
            dataInicial: currentDate.format(DATE_TIME_FORMAT),
            dataFinal: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );
        service
          .find(123)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: elemDefault });
      });

      it('should create a PropriedadeContratada', async () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            dataInicial: currentDate.format(DATE_TIME_FORMAT),
            dataFinal: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            dataInicial: currentDate,
            dataFinal: currentDate
          },
          returnedFromService
        );
        service
          .create(new PropriedadeContratada(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a PropriedadeContratada', async () => {
        const returnedFromService = Object.assign(
          {
            dataInicial: currentDate.format(DATE_TIME_FORMAT),
            dataFinal: currentDate.format(DATE_TIME_FORMAT),
            quantidadeCabecas: 1,
            valorContratado: 1,
            formaPagamento: 'BBBBBB',
            parcelas: 1,
            valorParcela: 1
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dataInicial: currentDate,
            dataFinal: currentDate
          },
          returnedFromService
        );
        service
          .update(expected)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should return a list of PropriedadeContratada', async () => {
        const returnedFromService = Object.assign(
          {
            dataInicial: currentDate.format(DATE_TIME_FORMAT),
            dataFinal: currentDate.format(DATE_TIME_FORMAT),
            quantidadeCabecas: 1,
            valorContratado: 1,
            formaPagamento: 'BBBBBB',
            parcelas: 1,
            valorParcela: 1
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            dataInicial: currentDate,
            dataFinal: currentDate
          },
          returnedFromService
        );
        service
          .query(expected)
          .pipe(
            take(1),
            map(resp => resp.body)
          )
          .subscribe(body => (expectedResult = body));
        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a PropriedadeContratada', async () => {
        const rxPromise = service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
