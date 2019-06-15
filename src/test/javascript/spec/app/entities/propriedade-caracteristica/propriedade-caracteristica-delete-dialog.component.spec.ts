/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { AgroplannerTestModule } from '../../../test.module';
import { PropriedadeCaracteristicaDeleteDialogComponent } from 'app/entities/propriedade-caracteristica/propriedade-caracteristica-delete-dialog.component';
import { PropriedadeCaracteristicaService } from 'app/entities/propriedade-caracteristica/propriedade-caracteristica.service';

describe('Component Tests', () => {
  describe('PropriedadeCaracteristica Management Delete Component', () => {
    let comp: PropriedadeCaracteristicaDeleteDialogComponent;
    let fixture: ComponentFixture<PropriedadeCaracteristicaDeleteDialogComponent>;
    let service: PropriedadeCaracteristicaService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AgroplannerTestModule],
        declarations: [PropriedadeCaracteristicaDeleteDialogComponent]
      })
        .overrideTemplate(PropriedadeCaracteristicaDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PropriedadeCaracteristicaDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PropriedadeCaracteristicaService);
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
