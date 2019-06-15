import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPropriedade_caracteristica } from 'app/shared/model/propriedade-caracteristica.model';

@Component({
  selector: 'jhi-propriedade-caracteristica-detail',
  templateUrl: './propriedade-caracteristica-detail.component.html'
})
export class Propriedade_caracteristicaDetailComponent implements OnInit {
  propriedade_caracteristica: IPropriedade_caracteristica;

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
