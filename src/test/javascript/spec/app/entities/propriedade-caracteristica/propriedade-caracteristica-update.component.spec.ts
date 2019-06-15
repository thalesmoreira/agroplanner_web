/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { AgroplannerTestModule } from '../../../test.module';
import { PropriedadeCaracteristicaUpdateComponent } from 'app/entities/propriedade-caracteristica/propriedade-caracteristica-update.component';
import { PropriedadeCaracteristicaService } from 'app/entities/propriedade-caracteristica/propriedade-caracteristica.service';
import { PropriedadeCaracteristica } from 'app/shared/model/propriedade-caracteristica.model';

describe('Component Tests', () => {
  describe('PropriedadeCaracteristica Management Update Component', () => {
    let comp: PropriedadeCaracteristicaUpdateComponent;
    let fixture: ComponentFixture<PropriedadeCaracteristicaUpdateComponent>;
    let service: PropriedadeCaracteristicaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AgroplannerTestModule],
        declarations: [PropriedadeCaracteristicaUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(PropriedadeCaracteristicaUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PropriedadeCaracteristicaUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PropriedadeCaracteristicaService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new PropriedadeCaracteristica(123);
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
        const entity = new PropriedadeCaracteristica();
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
