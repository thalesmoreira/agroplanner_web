import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { IPropriedadeContratada, PropriedadeContratada } from 'app/shared/model/propriedade-contratada.model';
import { PropriedadeContratadaService } from './propriedade-contratada.service';
import { IPropriedade } from 'app/shared/model/propriedade.model';
import { PropriedadeService } from 'app/entities/propriedade';
import { IUser, UserService } from 'app/core';

@Component({
  selector: 'jhi-propriedade-contratada-update',
  templateUrl: './propriedade-contratada-update.component.html'
})
export class PropriedadeContratadaUpdateComponent implements OnInit {
  isSaving: boolean;

  propriedades: IPropriedade[];

  users: IUser[];

  editForm = this.fb.group({
    id: [],
    dataInicial: [null, [Validators.required]],
    dataFinal: [null, [Validators.required]],
    quantidadeCabecas: [null, [Validators.required]],
    valorContratado: [null, [Validators.required]],
    formaPagamento: [null, [Validators.required]],
    parcelas: [null, [Validators.required]],
    valorParcela: [null, [Validators.required]],
    propriedadeId: [null, Validators.required],
    userId: [null, Validators.required]
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected propriedadeContratadaService: PropriedadeContratadaService,
    protected propriedadeService: PropriedadeService,
    protected userService: UserService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ propriedadeContratada }) => {
      this.updateForm(propriedadeContratada);
    });
    this.propriedadeService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IPropriedade[]>) => mayBeOk.ok),
        map((response: HttpResponse<IPropriedade[]>) => response.body)
      )
      .subscribe((res: IPropriedade[]) => (this.propriedades = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.userService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IUser[]>) => mayBeOk.ok),
        map((response: HttpResponse<IUser[]>) => response.body)
      )
      .subscribe((res: IUser[]) => (this.users = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(propriedadeContratada: IPropriedadeContratada) {
    this.editForm.patchValue({
      id: propriedadeContratada.id,
      dataInicial: propriedadeContratada.dataInicial != null ? propriedadeContratada.dataInicial.format(DATE_TIME_FORMAT) : null,
      dataFinal: propriedadeContratada.dataFinal != null ? propriedadeContratada.dataFinal.format(DATE_TIME_FORMAT) : null,
      quantidadeCabecas: propriedadeContratada.quantidadeCabecas,
      valorContratado: propriedadeContratada.valorContratado,
      formaPagamento: propriedadeContratada.formaPagamento,
      parcelas: propriedadeContratada.parcelas,
      valorParcela: propriedadeContratada.valorParcela,
      propriedadeId: propriedadeContratada.propriedadeId,
      userId: propriedadeContratada.userId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const propriedadeContratada = this.createFromForm();
    if (propriedadeContratada.id !== undefined) {
      this.subscribeToSaveResponse(this.propriedadeContratadaService.update(propriedadeContratada));
    } else {
      this.subscribeToSaveResponse(this.propriedadeContratadaService.create(propriedadeContratada));
    }
  }

  private createFromForm(): IPropriedadeContratada {
    const entity = {
      ...new PropriedadeContratada(),
      id: this.editForm.get(['id']).value,
      dataInicial:
        this.editForm.get(['dataInicial']).value != null ? moment(this.editForm.get(['dataInicial']).value, DATE_TIME_FORMAT) : undefined,
      dataFinal:
        this.editForm.get(['dataFinal']).value != null ? moment(this.editForm.get(['dataFinal']).value, DATE_TIME_FORMAT) : undefined,
      quantidadeCabecas: this.editForm.get(['quantidadeCabecas']).value,
      valorContratado: this.editForm.get(['valorContratado']).value,
      formaPagamento: this.editForm.get(['formaPagamento']).value,
      parcelas: this.editForm.get(['parcelas']).value,
      valorParcela: this.editForm.get(['valorParcela']).value,
      propriedadeId: this.editForm.get(['propriedadeId']).value,
      userId: this.editForm.get(['userId']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPropriedadeContratada>>) {
    result.subscribe((res: HttpResponse<IPropriedadeContratada>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackPropriedadeById(index: number, item: IPropriedade) {
    return item.id;
  }

  trackUserById(index: number, item: IUser) {
    return item.id;
  }
}
