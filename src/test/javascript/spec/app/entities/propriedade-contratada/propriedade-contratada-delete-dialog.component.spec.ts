/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { AgroplannerTestModule } from '../../../test.module';
import { PropriedadeContratadaDeleteDialogComponent } from 'app/entities/propriedade-contratada/propriedade-contratada-delete-dialog.component';
import { PropriedadeContratadaService } from 'app/entities/propriedade-contratada/propriedade-contratada.service';

describe('Component Tests', () => {
  describe('PropriedadeContratada Management Delete Component', () => {
    let comp: PropriedadeContratadaDeleteDialogComponent;
    let fixture: ComponentFixture<PropriedadeContratadaDeleteDialogComponent>;
    let service: PropriedadeContratadaService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AgroplannerTestModule],
        declarations: [PropriedadeContratadaDeleteDialogComponent]
      })
        .overrideTemplate(PropriedadeContratadaDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PropriedadeContratadaDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PropriedadeContratadaService);
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
