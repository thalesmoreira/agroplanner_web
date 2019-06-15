/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AgroplannerTestModule } from '../../../test.module';
import { PropriedadeFotoDetailComponent } from 'app/entities/propriedade-foto/propriedade-foto-detail.component';
import { PropriedadeFoto } from 'app/shared/model/propriedade-foto.model';

describe('Component Tests', () => {
  describe('PropriedadeFoto Management Detail Component', () => {
    let comp: PropriedadeFotoDetailComponent;
    let fixture: ComponentFixture<PropriedadeFotoDetailComponent>;
    const route = ({ data: of({ propriedadeFoto: new PropriedadeFoto(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AgroplannerTestModule],
        declarations: [PropriedadeFotoDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(PropriedadeFotoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PropriedadeFotoDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.propriedadeFoto).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
