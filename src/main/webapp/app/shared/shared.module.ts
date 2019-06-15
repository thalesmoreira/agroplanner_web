import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { AgroplannerSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective } from './';

@NgModule({
  imports: [AgroplannerSharedCommonModule],
  declarations: [JhiLoginModalComponent, HasAnyAuthorityDirective],
  entryComponents: [JhiLoginModalComponent],
  exports: [AgroplannerSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AgroplannerSharedModule {
  static forRoot() {
    return {
      ngModule: AgroplannerSharedModule
    };
  }
}
