import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { IPropriedadeFoto, PropriedadeFoto } from 'app/shared/model/propriedade-foto.model';
import { PropriedadeFotoService } from './propriedade-foto.service';
import { IPropriedade } from 'app/shared/model/propriedade.model';
import { PropriedadeService } from 'app/entities/propriedade';

@Component({
  selector: 'jhi-propriedade-foto-update',
  templateUrl: './propriedade-foto-update.component.html'
})
export class PropriedadeFotoUpdateComponent implements OnInit {
  isSaving: boolean;

  propriedades: IPropriedade[];

  editForm = this.fb.group({
    id: [],
    foto: [null, [Validators.required]],
    fotoContentType: [],
    propriedadeId: [null, Validators.required]
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected jhiAlertService: JhiAlertService,
    protected propriedadeFotoService: PropriedadeFotoService,
    protected propriedadeService: PropriedadeService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ propriedadeFoto }) => {
      this.updateForm(propriedadeFoto);
    });
    this.propriedadeService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IPropriedade[]>) => mayBeOk.ok),
        map((response: HttpResponse<IPropriedade[]>) => response.body)
      )
      .subscribe((res: IPropriedade[]) => (this.propriedades = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(propriedadeFoto: IPropriedadeFoto) {
    this.editForm.patchValue({
      id: propriedadeFoto.id,
      foto: propriedadeFoto.foto,
      fotoContentType: propriedadeFoto.fotoContentType,
      propriedadeId: propriedadeFoto.propriedadeId
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

  clearInputImage(field: string, fieldContentType: string, idInput: string) {
    this.editForm.patchValue({
      [field]: null,
      [fieldContentType]: null
    });
    if (this.elementRef && idInput && this.elementRef.nativeElement.querySelector('#' + idInput)) {
      this.elementRef.nativeElement.querySelector('#' + idInput).value = null;
    }
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const propriedadeFoto = this.createFromForm();
    if (propriedadeFoto.id !== undefined) {
      this.subscribeToSaveResponse(this.propriedadeFotoService.update(propriedadeFoto));
    } else {
      this.subscribeToSaveResponse(this.propriedadeFotoService.create(propriedadeFoto));
    }
  }

  private createFromForm(): IPropriedadeFoto {
    const entity = {
      ...new PropriedadeFoto(),
      id: this.editForm.get(['id']).value,
      fotoContentType: this.editForm.get(['fotoContentType']).value,
      foto: this.editForm.get(['foto']).value,
      propriedadeId: this.editForm.get(['propriedadeId']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPropriedadeFoto>>) {
    result.subscribe((res: HttpResponse<IPropriedadeFoto>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
}
