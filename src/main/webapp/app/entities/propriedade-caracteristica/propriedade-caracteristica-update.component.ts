import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IPropriedadeCaracteristica, PropriedadeCaracteristica } from 'app/shared/model/propriedade-caracteristica.model';
import { PropriedadeCaracteristicaService } from './propriedade-caracteristica.service';
import { IPropriedade } from 'app/shared/model/propriedade.model';
import { PropriedadeService } from 'app/entities/propriedade';
import { ICaracteristica } from 'app/shared/model/caracteristica.model';
import { CaracteristicaService } from 'app/entities/caracteristica';

@Component({
  selector: 'jhi-propriedade-caracteristica-update',
  templateUrl: './propriedade-caracteristica-update.component.html'
})
export class PropriedadeCaracteristicaUpdateComponent implements OnInit {
  isSaving: boolean;

  propriedades: IPropriedade[];

  caracteristicas: ICaracteristica[];

  editForm = this.fb.group({
    id: [],
    value: [null, [Validators.maxLength(250)]],
    propriedadeId: [null, Validators.required],
    caracteristicaId: [null, Validators.required]
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected propriedadeCaracteristicaService: PropriedadeCaracteristicaService,
    protected propriedadeService: PropriedadeService,
    protected caracteristicaService: CaracteristicaService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ propriedade_caracteristica }) => {
      this.updateForm(propriedade_caracteristica);
    });
    this.propriedadeService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IPropriedade[]>) => mayBeOk.ok),
        map((response: HttpResponse<IPropriedade[]>) => response.body)
      )
      .subscribe((res: IPropriedade[]) => (this.propriedades = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.caracteristicaService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ICaracteristica[]>) => mayBeOk.ok),
        map((response: HttpResponse<ICaracteristica[]>) => response.body)
      )
      .subscribe((res: ICaracteristica[]) => (this.caracteristicas = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(propriedade_caracteristica: IPropriedadeCaracteristica) {
    this.editForm.patchValue({
      id: propriedade_caracteristica.id,
      value: propriedade_caracteristica.value,
      propriedadeId: propriedade_caracteristica.propriedadeId,
      caracteristicaId: propriedade_caracteristica.caracteristicaId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const propriedade_caracteristica = this.createFromForm();
    if (propriedade_caracteristica.id !== undefined) {
      this.subscribeToSaveResponse(this.propriedadeCaracteristicaService.update(propriedade_caracteristica));
    } else {
      this.subscribeToSaveResponse(this.propriedadeCaracteristicaService.create(propriedade_caracteristica));
    }
  }

  private createFromForm(): IPropriedadeCaracteristica {
    const entity = {
      ...new PropriedadeCaracteristica(),
      id: this.editForm.get(['id']).value,
      value: this.editForm.get(['value']).value,
      propriedadeId: this.editForm.get(['propriedadeId']).value,
      caracteristicaId: this.editForm.get(['caracteristicaId']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPropriedadeCaracteristica>>) {
    result.subscribe(
      (res: HttpResponse<IPropriedadeCaracteristica>) => this.onSaveSuccess(),
      (res: HttpErrorResponse) => this.onSaveError()
    );
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

  trackCaracteristicaById(index: number, item: ICaracteristica) {
    return item.id;
  }
}
