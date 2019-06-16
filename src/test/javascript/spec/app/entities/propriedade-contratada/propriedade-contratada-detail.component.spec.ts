/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AgroplannerTestModule } from '../../../test.module';
import { PropriedadeContratadaDetailComponent } from 'app/entities/propriedade-contratada/propriedade-contratada-detail.component';
import { PropriedadeContratada } from 'app/shared/model/propriedade-contratada.model';

describe('Component Tests', () => {
  describe('PropriedadeContratada Management Detail Component', () => {
    let comp: PropriedadeContratadaDetailComponent;
    let fixture: ComponentFixture<PropriedadeContratadaDetailComponent>;
    const route = ({ data: of({ propriedadeContratada: new PropriedadeContratada(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AgroplannerTestModule],
        declarations: [PropriedadeContratadaDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(PropriedadeContratadaDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PropriedadeContratadaDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.propriedadeContratada).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
