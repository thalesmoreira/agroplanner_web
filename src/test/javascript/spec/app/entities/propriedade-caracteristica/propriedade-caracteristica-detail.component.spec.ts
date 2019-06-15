/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AgroplannerTestModule } from '../../../test.module';
import { Propriedade_caracteristicaDetailComponent } from 'app/entities/propriedade-caracteristica/propriedade-caracteristica-detail.component';
import { Propriedade_caracteristica } from 'app/shared/model/propriedade-caracteristica.model';

describe('Component Tests', () => {
  describe('Propriedade_caracteristica Management Detail Component', () => {
    let comp: Propriedade_caracteristicaDetailComponent;
    let fixture: ComponentFixture<Propriedade_caracteristicaDetailComponent>;
    const route = ({ data: of({ propriedade_caracteristica: new Propriedade_caracteristica(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AgroplannerTestModule],
        declarations: [Propriedade_caracteristicaDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(Propriedade_caracteristicaDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(Propriedade_caracteristicaDetailComponent);
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
