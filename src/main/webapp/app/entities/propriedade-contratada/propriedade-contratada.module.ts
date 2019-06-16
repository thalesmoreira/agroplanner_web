import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { AgroplannerSharedModule } from 'app/shared';
import {
  PropriedadeContratadaComponent,
  PropriedadeContratadaDetailComponent,
  PropriedadeContratadaUpdateComponent,
  PropriedadeContratadaDeletePopupComponent,
  PropriedadeContratadaDeleteDialogComponent,
  propriedadeContratadaRoute,
  propriedadeContratadaPopupRoute
} from './';

const ENTITY_STATES = [...propriedadeContratadaRoute, ...propriedadeContratadaPopupRoute];

@NgModule({
  imports: [AgroplannerSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    PropriedadeContratadaComponent,
    PropriedadeContratadaDetailComponent,
    PropriedadeContratadaUpdateComponent,
    PropriedadeContratadaDeleteDialogComponent,
    PropriedadeContratadaDeletePopupComponent
  ],
  entryComponents: [
    PropriedadeContratadaComponent,
    PropriedadeContratadaUpdateComponent,
    PropriedadeContratadaDeleteDialogComponent,
    PropriedadeContratadaDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AgroplannerPropriedadeContratadaModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
