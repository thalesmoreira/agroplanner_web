import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPropriedadeFoto } from 'app/shared/model/propriedade-foto.model';
import { PropriedadeFotoService } from './propriedade-foto.service';

@Component({
  selector: 'jhi-propriedade-foto-delete-dialog',
  templateUrl: './propriedade-foto-delete-dialog.component.html'
})
export class PropriedadeFotoDeleteDialogComponent {
  propriedadeFoto: IPropriedadeFoto;

  constructor(
    protected propriedadeFotoService: PropriedadeFotoService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.propriedadeFotoService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'propriedadeFotoListModification',
        content: 'Deleted an propriedadeFoto'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-propriedade-foto-delete-popup',
  template: ''
})
export class PropriedadeFotoDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ propriedadeFoto }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(PropriedadeFotoDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.propriedadeFoto = propriedadeFoto;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/propriedade-foto', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/propriedade-foto', { outlets: { popup: null } }]);
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
