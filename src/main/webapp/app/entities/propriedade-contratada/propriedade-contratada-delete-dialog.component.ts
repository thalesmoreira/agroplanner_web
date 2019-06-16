import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPropriedadeContratada } from 'app/shared/model/propriedade-contratada.model';
import { PropriedadeContratadaService } from './propriedade-contratada.service';

@Component({
  selector: 'jhi-propriedade-contratada-delete-dialog',
  templateUrl: './propriedade-contratada-delete-dialog.component.html'
})
export class PropriedadeContratadaDeleteDialogComponent {
  propriedadeContratada: IPropriedadeContratada;

  constructor(
    protected propriedadeContratadaService: PropriedadeContratadaService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.propriedadeContratadaService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'propriedadeContratadaListModification',
        content: 'Deleted an propriedadeContratada'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-propriedade-contratada-delete-popup',
  template: ''
})
export class PropriedadeContratadaDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ propriedadeContratada }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(PropriedadeContratadaDeleteDialogComponent as Component, {
          size: 'lg',
          backdrop: 'static'
        });
        this.ngbModalRef.componentInstance.propriedadeContratada = propriedadeContratada;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/propriedade-contratada', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/propriedade-contratada', { outlets: { popup: null } }]);
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
