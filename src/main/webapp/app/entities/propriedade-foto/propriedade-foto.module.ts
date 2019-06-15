import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { AgroplannerSharedModule } from 'app/shared';
import {
  PropriedadeFotoComponent,
  PropriedadeFotoDetailComponent,
  PropriedadeFotoUpdateComponent,
  PropriedadeFotoDeletePopupComponent,
  PropriedadeFotoDeleteDialogComponent,
  propriedadeFotoRoute,
  propriedadeFotoPopupRoute
} from './';

const ENTITY_STATES = [...propriedadeFotoRoute, ...propriedadeFotoPopupRoute];

@NgModule({
  imports: [AgroplannerSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    PropriedadeFotoComponent,
    PropriedadeFotoDetailComponent,
    PropriedadeFotoUpdateComponent,
    PropriedadeFotoDeleteDialogComponent,
    PropriedadeFotoDeletePopupComponent
  ],
  entryComponents: [
    PropriedadeFotoComponent,
    PropriedadeFotoUpdateComponent,
    PropriedadeFotoDeleteDialogComponent,
    PropriedadeFotoDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AgroplannerPropriedadeFotoModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
