/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { AgroplannerTestModule } from '../../../test.module';
import { PropriedadeUpdateComponent } from 'app/entities/propriedade/propriedade-update.component';
import { PropriedadeService } from 'app/entities/propriedade/propriedade.service';
import { Propriedade } from 'app/shared/model/propriedade.model';

describe('Component Tests', () => {
  describe('Propriedade Management Update Component', () => {
    let comp: PropriedadeUpdateComponent;
    let fixture: ComponentFixture<PropriedadeUpdateComponent>;
    let service: PropriedadeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AgroplannerTestModule],
        declarations: [PropriedadeUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(PropriedadeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PropriedadeUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PropriedadeService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Propriedade(123);
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
        const entity = new Propriedade();
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
