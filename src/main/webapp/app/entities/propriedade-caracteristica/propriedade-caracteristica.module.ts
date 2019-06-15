import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { AgroplannerSharedModule } from 'app/shared';
import {
  PropriedadeCaracteristicaComponent,
  PropriedadeCaracteristicaDetailComponent,
  PropriedadeCaracteristicaUpdateComponent,
  PropriedadeCaracteristicaDeletePopupComponent,
  PropriedadeCaracteristicaDeleteDialogComponent,
  propriedade_caracteristicaRoute,
  propriedade_caracteristicaPopupRoute
} from './';

const ENTITY_STATES = [...propriedade_caracteristicaRoute, ...propriedade_caracteristicaPopupRoute];

@NgModule({
  imports: [AgroplannerSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    PropriedadeCaracteristicaComponent,
    PropriedadeCaracteristicaDetailComponent,
    PropriedadeCaracteristicaUpdateComponent,
    PropriedadeCaracteristicaDeleteDialogComponent,
    PropriedadeCaracteristicaDeletePopupComponent
  ],
  entryComponents: [
    PropriedadeCaracteristicaComponent,
    PropriedadeCaracteristicaUpdateComponent,
    PropriedadeCaracteristicaDeleteDialogComponent,
    PropriedadeCaracteristicaDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AgroplannerPropriedadeCaracteristicaModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
