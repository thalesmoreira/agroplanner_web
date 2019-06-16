/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { AgroplannerTestModule } from '../../../test.module';
import { PropriedadeContratadaUpdateComponent } from 'app/entities/propriedade-contratada/propriedade-contratada-update.component';
import { PropriedadeContratadaService } from 'app/entities/propriedade-contratada/propriedade-contratada.service';
import { PropriedadeContratada } from 'app/shared/model/propriedade-contratada.model';

describe('Component Tests', () => {
  describe('PropriedadeContratada Management Update Component', () => {
    let comp: PropriedadeContratadaUpdateComponent;
    let fixture: ComponentFixture<PropriedadeContratadaUpdateComponent>;
    let service: PropriedadeContratadaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AgroplannerTestModule],
        declarations: [PropriedadeContratadaUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(PropriedadeContratadaUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PropriedadeContratadaUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PropriedadeContratadaService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new PropriedadeContratada(123);
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
        const entity = new PropriedadeContratada();
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
