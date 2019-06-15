import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { AgroplannerSharedModule } from 'app/shared';
import {
  Propriedade_caracteristicaComponent,
  Propriedade_caracteristicaDetailComponent,
  Propriedade_caracteristicaUpdateComponent,
  Propriedade_caracteristicaDeletePopupComponent,
  Propriedade_caracteristicaDeleteDialogComponent,
  propriedade_caracteristicaRoute,
  propriedade_caracteristicaPopupRoute
} from './';

const ENTITY_STATES = [...propriedade_caracteristicaRoute, ...propriedade_caracteristicaPopupRoute];

@NgModule({
  imports: [AgroplannerSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    Propriedade_caracteristicaComponent,
    Propriedade_caracteristicaDetailComponent,
    Propriedade_caracteristicaUpdateComponent,
    Propriedade_caracteristicaDeleteDialogComponent,
    Propriedade_caracteristicaDeletePopupComponent
  ],
  entryComponents: [
    Propriedade_caracteristicaComponent,
    Propriedade_caracteristicaUpdateComponent,
    Propriedade_caracteristicaDeleteDialogComponent,
    Propriedade_caracteristicaDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AgroplannerPropriedade_caracteristicaModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
