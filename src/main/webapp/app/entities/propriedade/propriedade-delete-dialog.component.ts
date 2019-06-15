import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPropriedade } from 'app/shared/model/propriedade.model';
import { PropriedadeService } from './propriedade.service';

@Component({
  selector: 'jhi-propriedade-delete-dialog',
  templateUrl: './propriedade-delete-dialog.component.html'
})
export class PropriedadeDeleteDialogComponent {
  propriedade: IPropriedade;

  constructor(
    protected propriedadeService: PropriedadeService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.propriedadeService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'propriedadeListModification',
        content: 'Deleted an propriedade'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-propriedade-delete-popup',
  template: ''
})
export class PropriedadeDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ propriedade }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(PropriedadeDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.propriedade = propriedade;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/propriedade', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/propriedade', { outlets: { popup: null } }]);
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
