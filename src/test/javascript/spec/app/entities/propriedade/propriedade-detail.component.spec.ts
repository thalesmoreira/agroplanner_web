/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AgroplannerTestModule } from '../../../test.module';
import { PropriedadeDetailComponent } from 'app/entities/propriedade/propriedade-detail.component';
import { Propriedade } from 'app/shared/model/propriedade.model';

describe('Component Tests', () => {
  describe('Propriedade Management Detail Component', () => {
    let comp: PropriedadeDetailComponent;
    let fixture: ComponentFixture<PropriedadeDetailComponent>;
    const route = ({ data: of({ propriedade: new Propriedade(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AgroplannerTestModule],
        declarations: [PropriedadeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(PropriedadeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PropriedadeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.propriedade).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
