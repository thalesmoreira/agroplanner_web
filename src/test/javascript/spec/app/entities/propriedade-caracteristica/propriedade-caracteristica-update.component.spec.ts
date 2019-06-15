/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { AgroplannerTestModule } from '../../../test.module';
import { Propriedade_caracteristicaUpdateComponent } from 'app/entities/propriedade-caracteristica/propriedade-caracteristica-update.component';
import { Propriedade_caracteristicaService } from 'app/entities/propriedade-caracteristica/propriedade-caracteristica.service';
import { Propriedade_caracteristica } from 'app/shared/model/propriedade-caracteristica.model';

describe('Component Tests', () => {
  describe('Propriedade_caracteristica Management Update Component', () => {
    let comp: Propriedade_caracteristicaUpdateComponent;
    let fixture: ComponentFixture<Propriedade_caracteristicaUpdateComponent>;
    let service: Propriedade_caracteristicaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AgroplannerTestModule],
        declarations: [Propriedade_caracteristicaUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(Propriedade_caracteristicaUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(Propriedade_caracteristicaUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(Propriedade_caracteristicaService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Propriedade_caracteristica(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new Propriedade_caracteristica();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
