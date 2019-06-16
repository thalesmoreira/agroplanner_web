import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPropriedadeContratada } from 'app/shared/model/propriedade-contratada.model';

@Component({
  selector: 'jhi-propriedade-contratada-detail',
  templateUrl: './propriedade-contratada-detail.component.html'
})
export class PropriedadeContratadaDetailComponent implements OnInit {
  propriedadeContratada: IPropriedadeContratada;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ propriedadeContratada }) => {
      this.propriedadeContratada = propriedadeContratada;
    });
  }

  previousState() {
    window.history.back();
  }
}
