import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IPropriedade } from 'app/shared/model/propriedade.model';

@Component({
  selector: 'jhi-propriedade-detail',
  templateUrl: './propriedade-detail.component.html'
})
export class PropriedadeDetailComponent implements OnInit {
  propriedade: IPropriedade;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ propriedade }) => {
      this.propriedade = propriedade;
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
