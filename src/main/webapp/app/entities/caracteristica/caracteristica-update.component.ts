import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { ICaracteristica, Caracteristica } from 'app/shared/model/caracteristica.model';
import { CaracteristicaService } from './caracteristica.service';

@Component({
  selector: 'jhi-caracteristica-update',
  templateUrl: './caracteristica-update.component.html'
})
export class CaracteristicaUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    nome: [null, [Validators.required, Validators.maxLength(150)]],
    descricao: [null, [Validators.maxLength(300)]]
  });

  constructor(protected caracteristicaService: CaracteristicaService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ caracteristica }) => {
      this.updateForm(caracteristica);
    });
  }

  updateForm(caracteristica: ICaracteristica) {
    this.editForm.patchValue({
      id: caracteristica.id,
      nome: caracteristica.nome,
      descricao: caracteristica.descricao
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const caracteristica = this.createFromForm();
    if (caracteristica.id !== undefined) {
      this.subscribeToSaveResponse(this.caracteristicaService.update(caracteristica));
    } else {
      this.subscribeToSaveResponse(this.caracteristicaService.create(caracteristica));
    }
  }

  private createFromForm(): ICaracteristica {
    const entity = {
      ...new Caracteristica(),
      id: this.editForm.get(['id']).value,
      nome: this.editForm.get(['nome']).value,
      descricao: this.editForm.get(['descricao']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICaracteristica>>) {
    result.subscribe((res: HttpResponse<ICaracteristica>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
