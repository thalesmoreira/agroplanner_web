import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { IPropriedade, Propriedade } from 'app/shared/model/propriedade.model';
import { PropriedadeService } from './propriedade.service';
import { IUser, UserService } from 'app/core';

@Component({
  selector: 'jhi-propriedade-update',
  templateUrl: './propriedade-update.component.html'
})
export class PropriedadeUpdateComponent implements OnInit {
  isSaving: boolean;

  users: IUser[];

  editForm = this.fb.group({
    id: [],
    nome: [null, [Validators.required, Validators.maxLength(250)]],
    descricao: [null, [Validators.required, Validators.maxLength(1024)]],
    localidade: [null, [Validators.required, Validators.maxLength(250)]],
    georeferenciamento: [],
    userId: [null, Validators.required]
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected jhiAlertService: JhiAlertService,
    protected propriedadeService: PropriedadeService,
    protected userService: UserService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ propriedade }) => {
      this.updateForm(propriedade);
    });
    this.userService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IUser[]>) => mayBeOk.ok),
        map((response: HttpResponse<IUser[]>) => response.body)
      )
      .subscribe((res: IUser[]) => (this.users = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(propriedade: IPropriedade) {
    this.editForm.patchValue({
      id: propriedade.id,
      nome: propriedade.nome,
      descricao: propriedade.descricao,
      localidade: propriedade.localidade,
      georeferenciamento: propriedade.georeferenciamento,
      userId: propriedade.userId
    });
  }

  byteSize(field) {
    return this.dataUtils.byteSize(field);
  }

  openFile(contentType, field) {
    return this.dataUtils.openFile(contentType, field);
  }

  setFileData(event, field: string, isImage) {
    return new Promise((resolve, reject) => {
      if (event && event.target && event.target.files && event.target.files[0]) {
        const file = event.target.files[0];
        if (isImage && !/^image\//.test(file.type)) {
          reject(`File was expected to be an image but was found to be ${file.type}`);
        } else {
          const filedContentType: string = field + 'ContentType';
          this.dataUtils.toBase64(file, base64Data => {
            this.editForm.patchValue({
              [field]: base64Data,
              [filedContentType]: file.type
            });
          });
        }
      } else {
        reject(`Base64 data was not set as file could not be extracted from passed parameter: ${event}`);
      }
    }).then(
      () => console.log('blob added'), // sucess
      this.onError
    );
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const propriedade = this.createFromForm();
    if (propriedade.id !== undefined) {
      this.subscribeToSaveResponse(this.propriedadeService.update(propriedade));
    } else {
      this.subscribeToSaveResponse(this.propriedadeService.create(propriedade));
    }
  }

  private createFromForm(): IPropriedade {
    const entity = {
      ...new Propriedade(),
      id: this.editForm.get(['id']).value,
      nome: this.editForm.get(['nome']).value,
      descricao: this.editForm.get(['descricao']).value,
      localidade: this.editForm.get(['localidade']).value,
      georeferenciamento: this.editForm.get(['georeferenciamento']).value,
      userId: this.editForm.get(['userId']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPropriedade>>) {
    result.subscribe((res: HttpResponse<IPropriedade>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

  trackUserById(index: number, item: IUser) {
    return item.id;
  }
}
