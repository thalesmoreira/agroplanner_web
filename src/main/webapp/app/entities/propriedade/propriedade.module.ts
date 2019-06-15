import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { AgroplannerSharedModule } from 'app/shared';
import {
  PropriedadeComponent,
  PropriedadeDetailComponent,
  PropriedadeUpdateComponent,
  PropriedadeDeletePopupComponent,
  PropriedadeDeleteDialogComponent,
  propriedadeRoute,
  propriedadePopupRoute
} from './';

const ENTITY_STATES = [...propriedadeRoute, ...propriedadePopupRoute];

@NgModule({
  imports: [AgroplannerSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    PropriedadeComponent,
    PropriedadeDetailComponent,
    PropriedadeUpdateComponent,
    PropriedadeDeleteDialogComponent,
    PropriedadeDeletePopupComponent
  ],
  entryComponents: [PropriedadeComponent, PropriedadeUpdateComponent, PropriedadeDeleteDialogComponent, PropriedadeDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AgroplannerPropriedadeModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
