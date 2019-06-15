import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPropriedade_caracteristica } from 'app/shared/model/propriedade-caracteristica.model';
import { Propriedade_caracteristicaService } from './propriedade-caracteristica.service';

@Component({
  selector: 'jhi-propriedade-caracteristica-delete-dialog',
  templateUrl: './propriedade-caracteristica-delete-dialog.component.html'
})
export class Propriedade_caracteristicaDeleteDialogComponent {
  propriedade_caracteristica: IPropriedade_caracteristica;

  constructor(
    protected propriedade_caracteristicaService: Propriedade_caracteristicaService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.propriedade_caracteristicaService.delete(id).subscribe(response => {
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
export class Propriedade_caracteristicaDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ propriedade_caracteristica }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(Propriedade_caracteristicaDeleteDialogComponent as Component, {
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
