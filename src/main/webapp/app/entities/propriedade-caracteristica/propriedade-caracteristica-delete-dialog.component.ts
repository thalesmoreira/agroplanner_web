import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPropriedadeCaracteristica } from 'app/shared/model/propriedade-caracteristica.model';
import { PropriedadeCaracteristicaService } from './propriedade-caracteristica.service';

@Component({
  selector: 'jhi-propriedade-caracteristica-delete-dialog',
  templateUrl: './propriedade-caracteristica-delete-dialog.component.html'
})
export class PropriedadeCaracteristicaDeleteDialogComponent {
  propriedade_caracteristica: IPropriedadeCaracteristica;

  constructor(
    protected propriedadeCaracteristicaService: PropriedadeCaracteristicaService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.propriedadeCaracteristicaService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'propriedade_caracteristicaListModification',
        content: 'Deleted an propriedade_caracteristica'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-propriedade-caracteristica-delete-popup',
  template: ''
})
export class PropriedadeCaracteristicaDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ propriedade_caracteristica }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(PropriedadeCaracteristicaDeleteDialogComponent as Component, {
          size: 'lg',
          backdrop: 'static'
        });
        this.ngbModalRef.componentInstance.propriedade_caracteristica = propriedade_caracteristica;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/propriedade-caracteristica', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/propriedade-caracteristica', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          }
        );
      }, 0);
    });
  }

  ngOnDestroy() {
    this.ngbModalRef = null;
  }
}
