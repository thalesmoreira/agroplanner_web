/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { AgroplannerTestModule } from '../../../test.module';
import { Propriedade_caracteristicaDeleteDialogComponent } from 'app/entities/propriedade-caracteristica/propriedade-caracteristica-delete-dialog.component';
import { Propriedade_caracteristicaService } from 'app/entities/propriedade-caracteristica/propriedade-caracteristica.service';

describe('Component Tests', () => {
  describe('Propriedade_caracteristica Management Delete Component', () => {
    let comp: Propriedade_caracteristicaDeleteDialogComponent;
    let fixture: ComponentFixture<Propriedade_caracteristicaDeleteDialogComponent>;
    let service: Propriedade_caracteristicaService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AgroplannerTestModule],
        declarations: [Propriedade_caracteristicaDeleteDialogComponent]
      })
        .overrideTemplate(Propriedade_caracteristicaDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(Propriedade_caracteristicaDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(Propriedade_caracteristicaService);
      mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
      mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));
    });
  });
});
