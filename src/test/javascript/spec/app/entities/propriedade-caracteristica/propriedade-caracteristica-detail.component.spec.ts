/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AgroplannerTestModule } from '../../../test.module';
import { PropriedadeCaracteristicaDetailComponent } from 'app/entities/propriedade-caracteristica/propriedade-caracteristica-detail.component';
import { PropriedadeCaracteristica } from 'app/shared/model/propriedade-caracteristica.model';

describe('Component Tests', () => {
  describe('PropriedadeCaracteristica Management Detail Component', () => {
    let comp: PropriedadeCaracteristicaDetailComponent;
    let fixture: ComponentFixture<PropriedadeCaracteristicaDetailComponent>;
    const route = ({ data: of({ propriedade_caracteristica: new PropriedadeCaracteristica(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AgroplannerTestModule],
        declarations: [PropriedadeCaracteristicaDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(PropriedadeCaracteristicaDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PropriedadeCaracteristicaDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.propriedade_caracteristica).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
