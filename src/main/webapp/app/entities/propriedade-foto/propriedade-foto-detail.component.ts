import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IPropriedadeFoto } from 'app/shared/model/propriedade-foto.model';

@Component({
  selector: 'jhi-propriedade-foto-detail',
  templateUrl: './propriedade-foto-detail.component.html'
})
export class PropriedadeFotoDetailComponent implements OnInit {
  propriedadeFoto: IPropriedadeFoto;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ propriedadeFoto }) => {
      this.propriedadeFoto = propriedadeFoto;
    });
  }

  byteSize(field) {
    return this.dataUtils.byteSize(field);
  }

  openFile(contentType, field) {
    return this.dataUtils.openFile(contentType, field);
  }
  previousState() {
    window.history.back();
  }
}
