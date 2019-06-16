import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'caracteristica',
        loadChildren: './caracteristica/caracteristica.module#AgroplannerCaracteristicaModule'
      },
      {
        path: 'propriedade',
        loadChildren: './propriedade/propriedade.module#AgroplannerPropriedadeModule'
      },
      {
        path: 'propriedade-caracteristica',
        loadChildren: './propriedade-caracteristica/propriedade-caracteristica.module#AgroplannerPropriedadeCaracteristicaModule'
      },
      {
        path: 'propriedade-foto',
        loadChildren: './propriedade-foto/propriedade-foto.module#AgroplannerPropriedadeFotoModule'
      },
      {
        path: 'propriedade-contratada',
        loadChildren: './propriedade-contratada/propriedade-contratada.module#AgroplannerPropriedadeContratadaModule'
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ],
  declarations: [],
  entryComponents: [],
  providers: [],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AgroplannerEntityModule {}
