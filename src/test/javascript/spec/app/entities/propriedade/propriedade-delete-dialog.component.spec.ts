/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { AgroplannerTestModule } from '../../../test.module';
import { PropriedadeDeleteDialogComponent } from 'app/entities/propriedade/propriedade-delete-dialog.component';
import { PropriedadeService } from 'app/entities/propriedade/propriedade.service';

describe('Component Tests', () => {
  describe('Propriedade Management Delete Component', () => {
    let comp: PropriedadeDeleteDialogComponent;
    let fixture: ComponentFixture<PropriedadeDeleteDialogComponent>;
    let service: PropriedadeService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AgroplannerTestModule],
        declarations: [PropriedadeDeleteDialogComponent]
      })
        .overrideTemplate(PropriedadeDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PropriedadeDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PropriedadeService);
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
