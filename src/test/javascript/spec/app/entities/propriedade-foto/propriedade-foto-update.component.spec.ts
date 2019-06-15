/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { AgroplannerTestModule } from '../../../test.module';
import { PropriedadeFotoUpdateComponent } from 'app/entities/propriedade-foto/propriedade-foto-update.component';
import { PropriedadeFotoService } from 'app/entities/propriedade-foto/propriedade-foto.service';
import { PropriedadeFoto } from 'app/shared/model/propriedade-foto.model';

describe('Component Tests', () => {
  describe('PropriedadeFoto Management Update Component', () => {
    let comp: PropriedadeFotoUpdateComponent;
    let fixture: ComponentFixture<PropriedadeFotoUpdateComponent>;
    let service: PropriedadeFotoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AgroplannerTestModule],
        declarations: [PropriedadeFotoUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(PropriedadeFotoUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PropriedadeFotoUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PropriedadeFotoService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new PropriedadeFoto(123);
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
        const entity = new PropriedadeFoto();
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
