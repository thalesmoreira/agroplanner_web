import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPropriedadeCaracteristica } from 'app/shared/model/propriedade-caracteristica.model';

@Component({
  selector: 'jhi-propriedade-caracteristica-detail',
  templateUrl: './propriedade-caracteristica-detail.component.html'
})
export class PropriedadeCaracteristicaDetailComponent implements OnInit {
  propriedade_caracteristica: IPropriedadeCaracteristica;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ propriedade_caracteristica }) => {
      this.propriedade_caracteristica = propriedade_caracteristica;
    });
  }

  previousState() {
    window.history.back();
  }
}
